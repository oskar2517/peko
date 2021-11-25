package me.oskar.vm;

public class Stack<E> {

    private int sp = 0;
    private int fp = 0;
    private final E[] stack= (E[])new Object[1024];

    public void push(final E o) {
        if (sp >= stack.length) {
            throw new IllegalStateException("Stack overflow");
        }

        stack[sp] = o;
        sp++;
    }

    public E pop() {
        sp--;
        if (sp < 0) {
            throw new IllegalStateException("Stack underflow");
        }

        return stack[sp];
    }

    public void set(final int index, final E o) {
        if (index >= stack.length || index < 0) {
            throw new IllegalStateException("Stack exceeded");
        }

        stack[index] = o;
    }

    public E get(final int index) {
        if (index >= stack.length || index < 0) {
            throw new IllegalStateException("Stack exceeded");
        }

        return stack[index];
    }

    public void pop(final int amount) {
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
