package com.ogzaitsev.springcalc.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Expression {

    private String inputExpression;

    private String resultExpression;

    private Deque<Token> tokenDeque;

    public boolean isValid() {
        inputExpression = inputExpression.toLowerCase();
        inputExpression = inputExpression.replaceAll("\\s",""); // удалить пробелы
        inputExpression = inputExpression.replaceAll("×", "*");
        String regex = "^(?:[0-9\\-+*/^()\\.]|(?:sin\\(|cos\\(|tan\\()?)+$"; // регулярка для лишних символов
        if (!inputExpression.matches(regex))
            return false;  // присутствуют лишние символы
        regex = ".+(\\d|\\))$";
        if (!inputExpression.matches(regex))
            return false;  // строка не оканчивается на число или )
        regex = "^(s|t|c|\\(|\\d|\\-|\\+).+";
        if (!inputExpression.matches(regex))
            return false;  // строка начинается на неправильный символ

        Deque<Character> deque = new ArrayDeque<>();
        char[] charArray = inputExpression.toCharArray();
        for (char c : charArray) {  // алгоритм для проверки скобок
            if (c == '(') {
                deque.push(c);
            } else if (c == ')') {
                if (deque.isEmpty()) {
                    return false;
                } else if (deque.peek() == '(') {
                    deque.pop();
                } else {
                    return false;
                }
            }
        }
        if (!deque.isEmpty())
            return false;
        if (inputExpression.indexOf('.') >= 0) { // не сработает при 123.45.67
            for (int i = 0; i < inputExpression.length();) {
                i = inputExpression.indexOf('.', i);
                if (i < 0) {
                    break;
                }
                if (i == 0 || i == inputExpression.length() - 1)
                    return false;
                if (!(Character.isDigit(inputExpression.charAt(i - 1)) &&
                        Character.isDigit(inputExpression.charAt(i + 1)))) {
                    return false;
                } else {
                    i++;
                }
            }
        }
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '-' || charArray[i] == '+' || charArray[i] == '/'
                    || charArray[i] == '*' || charArray[i] == '^') {
                if (!Character.isDigit(charArray[i + 1]) && charArray[i + 1] != '('
                        && !Character.isLetter(charArray[i + 1]))
                    return false;
            }
            if (charArray[i] == '(') {
                if (charArray[i + 1] == '/' || charArray[i + 1] == '*' ||
                        charArray[i + 1] == '^' || charArray[i + 1] == ')')
                    return false;
            }
            if (charArray[i] == ')') {
                if (i < charArray.length - 1 &&
                        (Character.isDigit(charArray[i + 1]) || Character.isLetter(charArray[i + 1])))
                    return false;
            }
        }
        return true;
    }

    public void tokenizeInput() {
        tokenDeque = new ArrayDeque<>();
        StringBuilder input = new StringBuilder(inputExpression.trim());
        while (input.length() > 0) {
            if (Character.isDigit(input.charAt(0))) {
                int nextToken = addNumberToken(tokenDeque, input);
                input.delete(0, nextToken);
            } else if (input.charAt(0) == '+' || input.charAt(0) == '-') {
                addZeroBeforeUnary(tokenDeque);
                tokenDeque.addLast(new OperationToken(input.charAt(0), 1));
                input.delete(0, 1);
            } else if (input.charAt(0) == '*' || input.charAt(0) == '/') {
//                char operation = input.charAt(0) == '×' ? '*' : input.charAt(0);
                tokenDeque.addLast(new OperationToken(input.charAt(0), 2));
                input.delete(0, 1);
            } else if (input.charAt(0) == '^') {
                tokenDeque.addLast(new OperationToken(input.charAt(0), 3));
                input.delete(0, 1);
            } else if (input.charAt(0) == 's' || input.charAt(0) == 'c' || input.charAt(0) == 't') {
                int nextToken = addTrigonometryToken(tokenDeque, input);
                input.delete(0, nextToken);
            } else if (input.charAt(0) == '(') {
                tokenDeque.addLast(new OperationToken('(', -1));
                input.delete(0, 1);
            } else if (input.charAt(0) == ')') {
                tokenDeque.addLast(new OperationToken(')', 6));
                input.delete(0, 1);
            } else {
                throw new RuntimeException("Unknown character");
            }
//            System.out.println("length " + input.length());
        }
        addZeroBeforeUnary(tokenDeque);
//        System.out.println(tokenDeque);
    }

    private void addZeroBeforeUnary(Deque<Token> tokenDeque) {
        if (tokenDeque.isEmpty()) {
            return;
        }
        if (tokenDeque.getLast() instanceof OperationToken)
            if (((OperationToken) tokenDeque.getLast()).getOperation() == '(')
                tokenDeque.addLast(new NumberToken(0));
        if (tokenDeque.getFirst().priority == 1)
            tokenDeque.addFirst(new NumberToken(0));
    }

    private int addTrigonometryToken(Deque<Token> tokenDeque, StringBuilder input) {
        if (input.length() < 3) {
            return input.length();
        }
        tokenDeque.addLast(new OperationToken(input.charAt(0), 4));
        return 3;
    }

    private int addNumberToken(Deque<Token> tokenDeque, StringBuilder input) {
        int indexOfNext = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
                indexOfNext++;
                continue;
            }
            break;
        }
        double value = Double.parseDouble(input.substring(0, indexOfNext));
        tokenDeque.addLast(new NumberToken(value));
        return indexOfNext;
    }

    public Deque<Token> getTokenDeque() {
        return tokenDeque;
    }

    public String getInputExpression() {
        return inputExpression;
    }

    public void setInputExpression(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    public String getResultExpression() {
        return resultExpression;
    }

    public void setResultExpression(String resultExpression) {
        this.resultExpression = resultExpression;
    }
}
