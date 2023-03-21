package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalIExp implements IExp {
    IExp e1, e2;
    String op;
    public RelationalIExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue iVal1 = (IntValue) v1;
                IntValue iVal2 = (IntValue) v2;
                int i1, i2;
                i1 = iVal1.getVal();
                i2 = iVal2.getVal();
                if (op.equals("<"))
                    return new BoolValue(i1 < i2);
                if (op.equals("<="))
                    return new BoolValue(i1 <= i2);
                if (op.equals("=="))
                    return new BoolValue(i1 == i2);
                if (op.equals("!="))
                    return new BoolValue(i1 != i2);
                if (op.equals(">"))
                    return new BoolValue(i1 > i2);
                if (op.equals(">="))
                    return new BoolValue(i1 >= i2);
            }
            else {
                throw new MyException("second operand is not an integer");
            }
        }
        else {
            throw new MyException("first operand is not an integer");
        }
        return null;
    }

    @Override
    public IExp deepCopy() {
        return new RelationalIExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }
            else throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
    }
}
