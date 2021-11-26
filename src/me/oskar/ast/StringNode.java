package me.oskar.ast;

public class StringNode extends Node {

    private final String value;

    public StringNode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("STRING %s", value);
    }
}
