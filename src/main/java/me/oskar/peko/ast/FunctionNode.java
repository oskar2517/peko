package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

import java.util.List;

public class FunctionNode extends Node {

    private final String name;
    private final List<ParameterDeclarationNode> parameters;
    private final BlockNode body;

    public FunctionNode(final String name, final List<ParameterDeclarationNode> parameters, final BlockNode body) {
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

    public List<ParameterDeclarationNode> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return String.format("(FUNC %s %s %s)", name, parameters, body);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
