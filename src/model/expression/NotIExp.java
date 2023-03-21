package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class NotIExp implements IExp{
    private IExp exp;
    public NotIExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws MyException {
        BoolValue val = (BoolValue) exp.eval(table, heap);
        return new BoolValue(!val.getVal());
    }

    @Override
    public IExp deepCopy() {
        return new NotIExp(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }

    @Override
    public String toString() {
        return "not(" + exp + ")";
    }
}

