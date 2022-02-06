package me.oskar.peko.vm.stack;

import me.oskar.peko.error.Error;
import me.oskar.peko.object.PekoObject;

import java.util.Objects;

public class Stack {

    private int sp = 0;
    private int fp = 0;
    private final StackSlot<?>[] stack= new StackSlot[1000];

    public void pushObject(final PekoObject o) {
        push(new StackSlot<>(o));
    }

    public void pushNumber(final int n) {
        push(new StackSlot<>(n));
    }

    private void push(final StackSlot<?> o) {
        try {
            stack[sp] = o;
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack overflow.");
        }
        sp++;
    }

    public PekoObject popObject() {
        return (PekoObject) Objects.requireNonNull(pop()).getValue();
    }

    public int popNumber() {
        return (Integer) Objects.requireNonNull(pop()).getValue();
    }

    private StackSlot<?> pop() {
        sp--;

        try {
            return stack[sp];
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack underflow.");
        }

        // Unreachable
        return null;
    }

    public void setObject(final int index, final PekoObject o) {
        set(index, new StackSlot<>(o));
    }

    private void set(final int index, final StackSlot<?> o) {
        try {
            stack[index] = o;
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack size exceeded.");
        }
    }

    public PekoObject getObject(final int index) {
        return (PekoObject) Objects.requireNonNull(get(index)).getValue();
    }

    private StackSlot<?> get(final int index) {
        try {
            return stack[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack size exceeded.");
        }

        // Unreachable
        return null;
    }

    public void drop(final int amount) {
        for (int i = 0; i < amount; i++) {
            pop();
        }
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }
}
