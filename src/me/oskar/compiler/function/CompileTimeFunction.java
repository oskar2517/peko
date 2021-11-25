package me.oskar.compiler.function;

import me.oskar.compiler.symbol.FunctionScope;

public class CompileTimeFunction {

    private final String name;
    private final int position;
    private final FunctionScope scope;

    public CompileTimeFunction(final String name, final int position, final FunctionScope scope) {
        this.name = name;
        this.position = position;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public FunctionScope getScope() {
        return scope;
    }
}
