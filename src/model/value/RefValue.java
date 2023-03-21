package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {return address;}
    public Type getLocationType() {return locationType;}

    public Type getType() {return new RefType(locationType);}

    @Override
    public String toString() {
        return "(" + Integer.toString(address) + ", " + locationType.toString() + ")";
    }
}
