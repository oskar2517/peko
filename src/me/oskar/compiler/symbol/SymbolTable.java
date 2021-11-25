package me.oskar.compiler.symbol;

public class SymbolTable {

    final GlobalScope globalScope = new GlobalScope();

    public Symbol define(final String name) {
        return globalScope.define(name);
    }

    public Symbol resolve(final String name) {
        return globalScope.resolve(name);
    }

    public Symbol defineParameter(final String name, final int index) {
        return globalScope.defineParameter(name, index);
    }

    public void enterFunctionScope() {
        globalScope.enterFunctionScope();
    }

    public void leaveFunctionScope() {
        globalScope.leaveFunctionScope();
    }

    public void enterBlockScope() {
        globalScope.enterBlockScope();
    }

    public void leaveBlockScope() {
        globalScope.leaveBlockScope();
    }

    public FunctionScope getFunctionScope() {
        return globalScope.getFunctionScope();
    }
}
