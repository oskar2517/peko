package main.me.oskar.compiler.constant.object;

public record NilConstant() implements Constant {

    @Override
    public boolean equals(final Constant o) {
        return o instanceof NilConstant;
    }
}
