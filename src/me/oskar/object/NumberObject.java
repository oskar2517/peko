package me.oskar.object;

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
            return super.add(o);
        }
    }

    @Override
    public LObject mul(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new NumberObject(value * oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject div(final LObject o) {
        if (o instanceof NumberObject oc) {
            if (oc.getValue() == 0) {
                throw new IllegalStateException("left expression cannot be zero in division");
            }

            return new NumberObject(value / oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject mod(final LObject o) {
        if (o instanceof NumberObject oc) {
            if (oc.getValue() == 0) {
                throw new IllegalStateException("left expression cannot be zero in modulo");
            }

            return new NumberObject(value % oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject eq(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value == oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject ne(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value != oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject lt(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value < oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject le(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value <= oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject gt(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value > oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject ge(final LObject o) {
        if (o instanceof NumberObject oc) {
            return new BooleanObject(value >= oc.getValue());
        } else {
            return super.add(o);
        }
    }

    @Override
    public LObject neg() {
        return new NumberObject(-value);
    }
}
