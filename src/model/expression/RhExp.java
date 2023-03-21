package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class RhExp implements IExp{
    IExp exp;

    public RhExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws MyException {
        Value v = exp.eval(tbl, hp);
        if (v instanceof RefValue) {
            RefValue refVal = (RefValue) v;
            int addr = refVal.getAddr();
            if (!hp.isDefined(addr)) {
                throw new MyException("Address is not defined!");
            }
            else {
                return hp.get(addr);
            }
        }
        else throw new MyException("variable is not RefValue!");
    }

    @Override
    public IExp deepCopy() {
        return new RhExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Rh(" + exp.toString() + ")";
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        }
        else  throw new MyException("the rH argument is not a Ref Type");  }
}
