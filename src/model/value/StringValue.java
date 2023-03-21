package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private String val;

    public boolean equals(Object another) {
        return another instanceof StringValue;
    }

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {return val;}

    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }
}
