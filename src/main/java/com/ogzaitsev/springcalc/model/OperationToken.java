package com.ogzaitsev.springcalc.model;


public class OperationToken extends Token {
    private char operation;

    public OperationToken() {}

    public OperationToken(char operation, int priority) {
        this.operation = operation;
        this.priority = priority;
    }

    public char getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return Character.toString(operation);
    }
}
