package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.Type;
import model.value.Value;

public class ValueIExp implements IExp {
    Value e;

    public ValueIExp(Value e) {
        this.e = e;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) {
        return e;
    }

    @Override
    public IExp deepCopy() {
        return new ValueIExp(e);
    }

    @Override
    public String toString() {
        return e.toString();
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
