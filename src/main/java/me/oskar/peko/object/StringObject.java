package me.oskar.peko.object;

public class StringObject extends PekoObject {

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
    public PekoObject add(final PekoObject o) {
        if (o instanceof StringObject oc) {
            return new StringObject(value + oc.value);
        } else {
            return super.add(o);
        }
    }

    @Override
    public PekoObject eq(final PekoObject o) {
        if (o instanceof StringObject oc) {
            return new BooleanObject(value.equals(oc.value));
        } else {
            return new BooleanObject(false);
        }
    }
}
