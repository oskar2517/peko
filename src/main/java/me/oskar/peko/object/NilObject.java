package me.oskar.peko.object;

public class NilObject extends LObject {

    @Override
    public String toString() {
        return "nil";
    }

    @Override
    public LObject eq(final LObject o) {
        return new BooleanObject(o instanceof NilObject);
    }
}
