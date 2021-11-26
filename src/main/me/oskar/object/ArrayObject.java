package main.me.oskar.object;

import main.me.oskar.error.Error;

import java.util.ArrayList;
import java.util.Objects;

public class ArrayObject extends LObject {

    private final ArrayList<LObject> value;

    public ArrayObject(final ArrayList<LObject> value) {
        this.value = value;
    }

    public ArrayList<LObject> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public LObject eq(final LObject o) {
        return new BooleanObject(this == o);
    }

    @Override
    public LObject getIndex(final LObject index) {
        if (index instanceof NumberObject n) {
            try {
                final var o = value.get((int) n.getValue());

                return Objects.requireNonNullElseGet(o, NilObject::new);
            } catch (IndexOutOfBoundsException e) {
                return new NilObject();
            }
        } else {
            Error.error("Index %s is not a number.", index);
        }

        // Unreachable
        return null;
    }

    @Override
    public void setIndex(final LObject index, final LObject newValue) {
        if (index instanceof NumberObject n) {
            while (n.getValue() >= value.size()) {
                value.add(new NilObject());
            }

            value.set((int) n.getValue(), newValue);
        } else {
            Error.error("Index %s is not a number.", index);
        }
    }
}
