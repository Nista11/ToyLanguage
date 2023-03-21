package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.Type;
import model.value.Value;

public class VarIExp implements IExp {
    String id;

    public VarIExp(String id) {
        this.id = id;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public IExp deepCopy() {
        return new VarIExp(id);
    }

    @Override
    public String toString() {
        return id;
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }
}
