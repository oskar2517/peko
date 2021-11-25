package me.oskar.object;

import me.oskar.error.Error;

public class LObject {

    public LObject add(final LObject o) {
        Error.error("Cannot perform operation `+` on %s and %s.", this, o);
        return null;
    }

    public LObject sub(final LObject o) {
        Error.error("Cannot perform operation `-` on %s and %s.", this, o);
        return null;
    }

    public LObject mul(final LObject o) {
        Error.error("Cannot perform operation `*` on %s and %s.", this, o);
        return null;
    }

    public LObject div(final LObject o) {
        Error.error("Cannot perform operation `/` on %s and %s.", this, o);
        return null;
    }

    public LObject mod(final LObject o) {
        Error.error("Cannot perform operation `%s` on %s and %s.", "%", this, o);
        return null;
    }

    public LObject eq(final LObject o) {
        Error.error("Cannot perform operation `==` on %s and %s.", this, o);
        return null;
    }

    public LObject ne(final LObject o) {
        Error.error("Cannot perform operation `!=` on %s and %s.", this, o);
        return null;
    }

    public LObject lt(final LObject o) {
        Error.error("Cannot perform operation `<` on %s and %s.", this, o);
        return null;
    }

    public LObject le(final LObject o) {
        Error.error("Cannot perform operation `<=` on %s and %s.", this, o);
        return null;
    }

    public LObject gt(final LObject o) {
        Error.error("Cannot perform operation `>` on %s and %s.", this, o);
        return null;
    }

    public LObject ge(final LObject o) {
        Error.error("Cannot perform operation `>=` on %s and %s.", this, o);
        return null;
    }

    public LObject not() {
        Error.error("Cannot perform operation `!` on %s.", this);
        return null;
    }

    public LObject neg() {
        Error.error("Cannot perform operation `-` on %s.", this);
        return null;
    }

    public boolean isTruthy() {
        Error.error("Cannot cast %s to boolean.", this);
        return false;
    }
}
