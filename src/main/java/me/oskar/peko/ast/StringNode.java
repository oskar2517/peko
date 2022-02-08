package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

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
        return String.format("(STRING %s)", value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
