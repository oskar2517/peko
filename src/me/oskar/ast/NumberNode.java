package me.oskar.ast;

public class NumberNode extends Node {

    private final double value;

    public NumberNode(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(NUMBER %s)", value);
    }
}
