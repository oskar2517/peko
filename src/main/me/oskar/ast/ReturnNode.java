package main.me.oskar.ast;

public class ReturnNode extends Node {

    private final Node value;

    public ReturnNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(Return " + value + ")";
    }
}
