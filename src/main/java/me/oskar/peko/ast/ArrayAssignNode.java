package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class ArrayAssignNode extends Node {

    private final Node target;
    private final Node value;

    public ArrayAssignNode(final Node target, final Node value) {
        this.target = target;
        this.value = value;
    }

    public Node getTarget() {
        return target;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(ARRAY_ASGN %s %s)", target, value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
