package com.ogzaitsev.springcalc.model;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class ReversePolishNotation {

    private Expression expression;

    private LinkedList<Token> outputDeque;

    public LinkedList<Token> getOutputDeque() {
        return outputDeque;
    }

    public ReversePolishNotation(Expression expression) {
        this.expression = expression;
    }

    public Deque<Token> toPolishNotation() {
        Deque<Token> inputDeque = expression.getTokenDeque();
        outputDeque = new LinkedList<>();
        Deque<Token> supportDeque = new ArrayDeque<>();
        while (!inputDeque.isEmpty()) {
            Token token = inputDeque.removeFirst();
            if (token.priority == 0) {
                outputDeque.addLast(token);
            }
            if (token.priority > 0 && token.priority < 5) {
                if (supportDeque.isEmpty()) {
                    supportDeque.addLast(token);
                } else {
                    if (supportDeque.getLast().priority == 3) {
                        outputDeque.addLast(supportDeque.removeLast());
                    }
                    while (!supportDeque.isEmpty() && supportDeque.getLast().priority >= token.priority) {
                        outputDeque.addLast(supportDeque.removeLast());
                    }
                    supportDeque.addLast(token);
                }
            } else if (token.priority == -1) {
                supportDeque.addLast(token);
            } else if (token.priority == 6) {
                while (supportDeque.getLast().priority != -1) {
                    outputDeque.addLast(supportDeque.removeLast());
                }
                supportDeque.removeLast();
            }
        }
        while (!supportDeque.isEmpty()) {
            outputDeque.addLast(supportDeque.removeLast());
        }
        return outputDeque;
    }

    public void calculate() {
        int i = 0;
        while (outputDeque.size() > 1) {
            Token token = outputDeque.get(i);
            if (token instanceof OperationToken) {
                if (((OperationToken) token).priority < 4) {
                    double operationResult = performOperation(
                            (NumberToken) outputDeque.get(i - 2),
                            (OperationToken) token,
                            (NumberToken) outputDeque.get(i - 1));
                    NumberToken resultToken = new NumberToken(operationResult);
                    for (int j = 0; j < 3; j++)
                        outputDeque.remove(i - 2);
                    outputDeque.add(i - 2, resultToken);
                    i = 0;
                } else if (((OperationToken) token).priority == 4) {
                    double operationResult = performTrigonometryOperation(
                            (OperationToken) token,
                            (NumberToken) outputDeque.get(i - 1));
                    NumberToken resultToken = new NumberToken(operationResult);
                    outputDeque.remove(i - 1);
                    outputDeque.set(i - 1, resultToken);
                    i = 0;
                }
            }
            i++;
        }
        DecimalFormat format = new DecimalFormat("0.0000000");
        expression.setResultExpression(format.format(((NumberToken) outputDeque.getFirst()).value));
    }

    private double performOperation(NumberToken operand1, OperationToken operation, NumberToken operand2) {
        switch (operation.getOperation()) {
            case '+' -> {
                return operand1.value + operand2.value;
            }
            case '-' -> {
                return operand1.value - operand2.value;
            }
            case '/' -> {
                return operand1.value / operand2.value;
            }
            case '*' -> {
                return operand1.value * operand2.value;
            }
            case '^' -> {
                return Math.pow(operand1.value, operand2.value);
            }
            default -> {
                throw new RuntimeException("Unknown operation: " + operation.getOperation());
            }
        }
    }

    private double performTrigonometryOperation(OperationToken operation, NumberToken number) {
        switch (operation.getOperation()) {
            case 's' -> {
                return Math.sin(number.value);
            }
            case 'c' -> {
                return Math.cos(number.value);
            }
            case 't' -> {
                return Math.tan(number.value);
            }
            default -> {
                throw new RuntimeException("Unknown operation: " + operation.getOperation());
            }
        }
    }


}
