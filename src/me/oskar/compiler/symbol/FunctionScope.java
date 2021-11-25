package me.oskar.compiler.symbol;

public class FunctionScope {

    private BlockScope blockScope = new BlockScope(null);
    private int symbolIndex = 0;

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

    public Symbol resolve(final String name) {
        var cScope = blockScope;

        while (cScope != null && !cScope.exists(name)) {
            cScope = cScope.getParentScope();
        }

        if (cScope == null) {
            return null;
        }

        return cScope.resolve(name);
    }

    public boolean exists(final String name) {
        return resolve(name) != null;
    }

    public int getLocalsCount() {
        return symbolIndex;
    }
 }
