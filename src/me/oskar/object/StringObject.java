package me.oskar.object;

public class StringObject extends LObject {

    private final String value;

    public StringObject(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public LObject add(final LObject o) {
        return new StringObject(value + o.toString());
    }
}
