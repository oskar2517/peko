package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class ArrayAccessNode extends Node {

    private final Node target;
    private final Node index;

    public ArrayAccessNode(final Node target, final Node index) {
        this.target = target;
        this.index = index;
    }

    public Node getTarget() {
        return target;
    }

    public Node getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("(ARRAY_ACC %s, %s)", target, index);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
