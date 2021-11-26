package main.me.oskar.object;

public class BooleanObject extends LObject {

    private final boolean value;

    public BooleanObject(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public LObject eq(final LObject o) {
        if (o instanceof BooleanObject oc) {
            return new BooleanObject(value == oc.getValue());
        } else {
            return new BooleanObject(false);
        }
    }

    @Override
    public LObject not() {
        return new BooleanObject(!value);
    }

    @Override
    public boolean isTruthy() {
        return value;
    }
}

