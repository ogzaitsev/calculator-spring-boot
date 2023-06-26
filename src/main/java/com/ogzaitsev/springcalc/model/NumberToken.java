package com.ogzaitsev.springcalc.model;

public class NumberToken extends Token {

    double value;

    public NumberToken() {}

    public NumberToken(double value) {
        this.value = value;
        this.priority = 0;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
