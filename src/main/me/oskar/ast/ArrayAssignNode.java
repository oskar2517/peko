package main.me.oskar.ast;

public class ArrayAssignNode extends Node {

    private final Node index;
    private final Node value;

    public ArrayAssignNode(final Node target, final Node value) {
        this.index = target;
        this.value = value;
    }

    public Node getIndex() {
        return index;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(ARRAY_ASGN %s %s)", index, value);
    }
}
