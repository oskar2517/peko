package main.me.oskar.compiler.constant.object;

public record NumberConstant(double value) implements Constant {

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(final Constant o) {
        if (o instanceof NumberConstant co) {
            return co.value == value;
        } else {
            return false;
        }
    }
}
