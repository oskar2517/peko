package me.oskar.object;

import me.oskar.error.Error;

public class NumberObject extends LObject {

    private final double value;

    public NumberObject(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public LObject add(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value + oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject sub(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value - oc.getValue());
        } else {
            return super.sub(o);
        }
    }

    @Override
    public LObject mul(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value * oc.getValue());
        } else {
            return super.mul(o);
        }
    }

    @Override
    public LObject div(final LObject o) {
        if (o instanceof NumberObject oc) {
            if (oc.getValue() == 0) {
                Error.error("Division by zero.");
            }

            return new NumberObject(value / oc.getValue());
        } else {
            return super.div(o);
        }
    }

    @Override
    public LObject mod(final LObject o) {
        if (o instanceof NumberObject oc) {
            if (oc.getValue() == 0) {
                Error.error("Division by zero.");
            }

            return new NumberObject(value % oc.getValue());
        } else {
            return super.mod(o);
        }
    }

    @Override
    public LObject eq(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value == oc.getValue());
        } else {
            return new BooleanObject(false);
        }
    }

    @Override
    public LObject ne(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value != oc.getValue());
        } else {
            return super.ne(o);
        }
    }

    @Override
    public LObject lt(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value < oc.getValue());
        } else {
            return super.lt(o);
        }
    }

    @Override
    public LObject le(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value <= oc.getValue());
        } else {
            return super.le(o);
        }
    }

    @Override
    public LObject gt(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value > oc.getValue());
        } else {
            return super.gt(o);
        }
    }

    @Override
    public LObject ge(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value >= oc.getValue());
        } else {
            return super.ge(o);
        }
    }

    @Override
    public LObject neg() {
        return new NumberObject(-value);
    }
}
