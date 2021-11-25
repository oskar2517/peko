package me.oskar.object;

public class LObject {

    public LObject add(final LObject o) {
        throw new IllegalStateException("+ operation not supported with these objects");
    }

    public LObject sub(final LObject o) {
        throw new IllegalStateException("- operation not supported with these objects");
    }

    public LObject mul(final LObject o) {
        throw new IllegalStateException("* operation not supported with these objects");
    }

    public LObject div(final LObject o) {
        throw new IllegalStateException("/ operation not supported with these objects");
    }

    public LObject mod(final LObject o) {
        throw new IllegalStateException("# operation not supported with these objects");
    }

    public LObject eq(final LObject o) {
        throw new IllegalStateException("== operation not supported with these objects");
    }

    public LObject ne(final LObject o) {
        throw new IllegalStateException("!= operation not supported with these objects");
    }

    public LObject lt(final LObject o) {
        throw new IllegalStateException("< operation not supported with these objects");
    }

    public LObject le(final LObject o) {
        throw new IllegalStateException("<= operation not supported with these objects");
    }

    public LObject gt(final LObject o) {
        throw new IllegalStateException("> operation not supported with these objects");
    }

    public LObject ge(final LObject o) {
        throw new IllegalStateException(">= operation not supported with these objects");
    }

    public LObject not() {
        throw new IllegalStateException("! operation not supported with these objects");
    }

    public LObject neg() {
        throw new IllegalStateException("- operation not supported with these objects");
    }

    public boolean isTruthy() {
        throw new IllegalStateException("object does not represent a truth value");
    }
}
