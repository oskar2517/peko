package me.oskar.peko.vm.stack;

public class StackSlot<T> {

    private final T value;

    public StackSlot(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
