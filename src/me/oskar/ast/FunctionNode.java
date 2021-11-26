package me.oskar.ast;

import java.util.List;

public class FunctionNode extends Node {

    private final String name;
    private final List<IdentNode> parameters;
    private final BlockNode body;

    public FunctionNode(final String name, final List<IdentNode> parameters, final BlockNode body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public BlockNode getBody() {
        return body;
    }

    public List<IdentNode> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return String.format("(FUNC %s %s %s)", name, parameters, body);
    }
}
