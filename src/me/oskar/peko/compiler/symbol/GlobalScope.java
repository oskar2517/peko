package me.oskar.peko.compiler.symbol;

import java.util.HashMap;

public class GlobalScope {

    private int symbolIndex = 0;
    private FunctionScope functionScope;
    private final HashMap<String, Symbol> symbols = new HashMap<>();

    public Symbol define(final String name) {
        if (onGlobalScope()) {
            final var symbol = new Symbol(symbolIndex, true);
            symbolIndex++;
            symbols.put(name, symbol);
            return symbol;
        } else {
            return functionScope.define(name);
        }
    }

    public Symbol defineParameter(final String name, final int index) {
        return functionScope.defineParameter(name, index);
    }

    public Symbol resolve(final String name) {
        if (!onGlobalScope() && functionScope.exists(name)) {
            return functionScope.resolve(name);
        }
        return symbols.get(name);
    }

    public boolean existsOnCurrentScope(final String name) {
        if (onGlobalScope()) {
            return symbols.containsKey(name);
        } else {
            return functionScope.existsOnCurrentScope(name);
        }
    }

    public void enterFunctionScope() {
        functionScope = new FunctionScope();
    }

    public void leaveFunctionScope() {
        functionScope = null;
    }

    public void enterBlockScope() {
        functionScope.enterBlockScope();
    }

    public void leaveBlockScope() {
        functionScope.leaveBlockScope();
    }

    public FunctionScope getFunctionScope() {
        return functionScope;
    }

    public boolean onGlobalScope() {
        return functionScope == null;
    }
}
