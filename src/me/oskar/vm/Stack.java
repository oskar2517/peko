package me.oskar.vm;

import me.oskar.error.Error;

public class Stack<E> {

    private int sp = 0;
    private int fp = 0;
    private final E[] stack= (E[])new Object[1024];

    public void push(final E o) {
        if (sp >= stack.length) {
            Error.error("Stack overflow.");
        }

        stack[sp] = o;
        sp++;
    }

    public E pop() {
        sp--;
        if (sp < 0) {
            Error.error("Stack underflow.");
        }

        final var v = stack[sp];
        stack[sp] = null;

        return v;
    }

    public void set(final int index, final E o) {
        if (index >= stack.length || index < 0) {
            Error.error("Stack size exceeded.");
        }

        stack[index] = o;
    }

    public E get(final int index) {
        if (index >= stack.length || index < 0) {
            Error.error("Stack size exceeded.");
        }

        return stack[index];
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
