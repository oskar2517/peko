package me.oskar.ast;

public class BinaryOperatorNode extends Node {

    private final Node left;
    private final Node right;
    private final OperatorType type;

    public BinaryOperatorNode(final OperatorType type, final Node left, final Node right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public OperatorType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", type, left, right);
    }
}
