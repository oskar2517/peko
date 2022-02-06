package me.oskar.peko.object;

import me.oskar.peko.error.Error;

import java.util.ArrayList;
import java.util.Objects;

public class ArrayObject extends PekoObject {

    private final ArrayList<PekoObject> value;

    public ArrayObject(final ArrayList<PekoObject> value) {
        this.value = value;
    }

    public ArrayList<PekoObject> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public PekoObject eq(final PekoObject o) {
        return new BooleanObject(this == o);
    }

    @Override
    public PekoObject getIndex(final PekoObject index) {
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
    public void setIndex(final PekoObject index, final PekoObject newValue) {
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
