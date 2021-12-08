package me.oskar.peko.ast;

public class UnaryOperatorNode extends Node {

    private final Node right;
    private final OperatorType type;

    public UnaryOperatorNode(final OperatorType type, final Node right) {
        this.type = type;
        this.right = right;
    }


    public Node getRight() {
        return right;
    }

    public OperatorType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type, right);
    }
}
