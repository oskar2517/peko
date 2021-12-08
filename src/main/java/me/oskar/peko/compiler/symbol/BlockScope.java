package me.oskar.peko.compiler.symbol;

import java.util.HashMap;

public class BlockScope {

    private final BlockScope parentScope;
    private final HashMap<String, Symbol> symbols = new HashMap<>();

    public BlockScope(final BlockScope parent) {
        this.parentScope = parent;
    }

    public void define(final String name, final Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol resolve(final String name) {
        return symbols.get(name);
    }

    public boolean exists(final String name) {
        return symbols.containsKey(name);
    }

    public BlockScope getParentScope() {
        return parentScope;
    }
}
