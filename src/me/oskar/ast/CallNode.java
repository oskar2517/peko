package me.oskar.ast;

import java.util.List;

public class CallNode extends Node {

    private final String functionName;
    private final List<Node> arguments;

    public CallNode(final String functionName, final List<Node> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return String.format("(CALL %S %S)", functionName, arguments);
    }
}
