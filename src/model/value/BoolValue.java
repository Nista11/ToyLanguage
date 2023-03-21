package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    private boolean val;

    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return Boolean.toString(val);
    }

    public Type getType() {
        return new BoolType();
    }
}
