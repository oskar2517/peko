package me.oskar.compiler.symbol;

import me.oskar.compiler.function.CompileTimeFunction;

import java.util.HashMap;

public class SymbolTable {

    private final GlobalScope globalScope = new GlobalScope();
    private final HashMap<Symbol, CompileTimeFunction> functions = new HashMap<>();

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

    public void addFunction(final Symbol symbol, final CompileTimeFunction function) {
        functions.put(symbol, function);
    }

    public CompileTimeFunction getFunction(final Symbol symbol) {
        return functions.get(symbol);
    }

    public boolean existsFunction(final Symbol symbol) {
        return functions.containsKey(symbol);
    }

    public FunctionScope getFunctionScope() {
        return globalScope.getFunctionScope();
    }
}
