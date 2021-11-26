package me.oskar.compiler.constant.object;

public record StringConstant(String value) implements Constant {

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Constant o) {
        if (o instanceof StringConstant co) {
            return co.value.equals(value);
        } else {
            return false;
        }
    }
}
