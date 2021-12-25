package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

import java.util.ArrayList;

public class FileNode extends Node {

    private final ArrayList<VariableDeclarationNode> variables = new ArrayList<>();
    private final ArrayList<FunctionNode> functions = new ArrayList<>();
    private FunctionNode mainFunction;

    public void addFunction(final FunctionNode node) {
        functions.add(node);
    }

    public void addVariable(final VariableDeclarationNode node) {
        variables.add(node);
    }

    public ArrayList<VariableDeclarationNode> getVariables() {
        return variables;
    }

    public ArrayList<FunctionNode> getFunctions() {
        return functions;
    }

    public void setMainFunction(FunctionNode mainFunction) {
        this.mainFunction = mainFunction;
    }

    public FunctionNode getMainFunction() {
        return mainFunction;
    }

    @Override
    public String toString() {
        return String.format("(FILE %s %s %s)", variables, functions, mainFunction);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
