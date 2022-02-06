package me.oskar.peko.object;

import me.oskar.peko.error.Error;

public class NumberObject extends PekoObject {

    private final double value;

    public NumberObject(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value % 1 == 0) {
            return String.valueOf((int) value);
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public PekoObject add(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value + oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public PekoObject sub(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value - oc.getValue());
        } else {
            return super.sub(o);
        }
    }

    @Override
    public PekoObject mul(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value * oc.getValue());
        } else {
            return super.mul(o);
        }
    }

    @Override
    public PekoObject div(final PekoObject o) {
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
    public PekoObject mod(final PekoObject o) {
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
    public PekoObject eq(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value == oc.getValue());
        } else {
            return new BooleanObject(false);
        }
    }

    @Override
    public PekoObject ne(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value != oc.getValue());
        } else {
            return super.ne(o);
        }
    }

    @Override
    public PekoObject lt(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value < oc.getValue());
        } else {
            return super.lt(o);
        }
    }

    @Override
    public PekoObject le(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value <= oc.getValue());
        } else {
            return super.le(o);
        }
    }

    @Override
    public PekoObject gt(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value > oc.getValue());
        } else {
            return super.gt(o);
        }
    }

    @Override
    public PekoObject ge(final PekoObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value >= oc.getValue());
        } else {
            return super.ge(o);
        }
    }

    @Override
    public PekoObject neg() {
        return new NumberObject(-value);
    }
}
