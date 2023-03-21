package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    private int val;

    public boolean equals(Object another) {
        return another instanceof IntValue;
    }

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return Integer.toString(val);
    }

    public Type getType() {
        return new IntType();
    }
}
