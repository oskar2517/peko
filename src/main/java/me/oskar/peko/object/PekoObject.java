package me.oskar.peko.object;

import me.oskar.peko.error.Error;

public class PekoObject {

    public PekoObject add(final PekoObject o) {
        Error.error("Cannot perform operation `+` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject sub(final PekoObject o) {
        Error.error("Cannot perform operation `-` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject mul(final PekoObject o) {
        Error.error("Cannot perform operation `*` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject div(final PekoObject o) {
        Error.error("Cannot perform operation `/` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject mod(final PekoObject o) {
        Error.error("Cannot perform operation `%s` on %s and %s.", "%", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject eq(final PekoObject o) {
        Error.error("Cannot perform operation `==` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject ne(final PekoObject o) {
        Error.error("Cannot perform operation `!=` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject lt(final PekoObject o) {
        Error.error("Cannot perform operation `<` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject le(final PekoObject o) {
        Error.error("Cannot perform operation `<=` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject gt(final PekoObject o) {
        Error.error("Cannot perform operation `>` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject ge(final PekoObject o) {
        Error.error("Cannot perform operation `>=` on %s and %s.", getClass().getSimpleName(), o.getClass().getSimpleName());
        return null;
    }

    public PekoObject getIndex(final PekoObject index) {
        Error.error("Array access operator not supported on %s", getClass().getSimpleName());
        return null;
    }

    public void setIndex(final PekoObject index, final PekoObject newValue) {
        Error.error("Array access operator not supported on %s", getClass().getSimpleName());
    }

    public PekoObject not() {
        Error.error("Cannot perform operation `!` on %s.", getClass().getSimpleName());
        return null;
    }

    public PekoObject neg() {
        Error.error("Cannot perform operation `-` on %s.", getClass().getSimpleName());
        return null;
    }

    public boolean isTruthy() {
        Error.error("Cannot cast %s to boolean.", getClass().getSimpleName());
        return false;
    }
}
