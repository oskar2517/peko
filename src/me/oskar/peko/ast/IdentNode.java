package me.oskar.peko.ast;

public class IdentNode extends Node {

    private final String value;

    public IdentNode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(IDENT %s)", value);
    }
}
