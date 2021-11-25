package me.oskar.compiler.symbol;

import java.util.HashMap;

public class FunctionScope {

    private BlockScope blockScope = new BlockScope(null);
    private int symbolIndex = 0;
    private HashMap<String, Symbol> parameters = new HashMap<>();

    public void enterBlockScope() {
        blockScope = new BlockScope(blockScope);
    }

    public void leaveBlockScope() {
        blockScope = blockScope.getParentScope();
    }

    public Symbol define(final String name) {
        final var symbol = new Symbol(symbolIndex, false);
        symbolIndex++;
        blockScope.define(name, symbol);

        return symbol;
    }

    public Symbol defineParameter(final String name, final int index) {
        final var symbol = new Symbol(index, false);
        parameters.put(name, symbol);
        return symbol;
    }

    public Symbol resolve(final String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        }

        var cScope = blockScope;

        while (cScope != null && !cScope.exists(name)) {
            cScope = cScope.getParentScope();
        }

        if (cScope == null) {
            return null;
        }

        return cScope.resolve(name);
    }

    public boolean existsOnCurrentScope(final String name) {
        return blockScope.exists(name);
    }

    public boolean exists(final String name) {
        return resolve(name) != null;
    }

    public int getLocalsCount() {
        return symbolIndex;
    }
 }
