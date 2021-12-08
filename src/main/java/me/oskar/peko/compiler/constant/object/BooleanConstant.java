package me.oskar.peko.compiler.constant.object;

public record BooleanConstant(boolean value) implements Constant {

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(final Constant o) {
        if (o instanceof BooleanConstant co) {
            return co.value == value;
        } else {
            return false;
        }
    }
}
