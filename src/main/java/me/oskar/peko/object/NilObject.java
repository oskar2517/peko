package me.oskar.peko.object;

public class NilObject extends PekoObject {

    @Override
    public String toString() {
        return "nil";
    }

    @Override
    public PekoObject eq(final PekoObject o) {
        return new BooleanObject(o instanceof NilObject);
    }
}
