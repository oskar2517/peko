package me.oskar.peko.vm;

import me.oskar.peko.error.Error;

public class Stack<E> {

    private int sp = 0;
    private int fp = 0;
    private final E[] stack= (E[])new Object[1024];

    public void push(final E o) {
        try {
            stack[sp] = o;
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack overflow.");
        }
        sp++;
    }

    public E pop() {
        sp--;

        try {
            return stack[sp];
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack underflow.");
        }

        // Cannot be reached
        return null;
    }

    public void set(final int index, final E o) {
        try {
            stack[index] = o;
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack size exceeded.");
        }
    }

    public E get(final int index) {
        try {
            return stack[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            Error.error("Stack size exceeded.");
        }

        // Cannot be reached
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
