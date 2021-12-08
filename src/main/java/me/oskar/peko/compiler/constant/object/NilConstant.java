package me.oskar.peko.compiler.constant.object;

public record NilConstant() implements Constant {

    @Override
    public boolean equals(final Constant o) {
        return o instanceof NilConstant;
    }
}
