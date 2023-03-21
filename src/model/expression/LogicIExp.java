package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicIExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    public LogicIExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue bVal1 = (BoolValue) v1;
                BoolValue bVal2 = (BoolValue) v2;
                boolean b1, b2;
                b1 = bVal1.getVal();
                b2 = bVal2.getVal();
                if (op.equals("and"))
                    return new BoolValue(b1 && b2);
                if (op.equals("or"))
                    return new BoolValue(b1 || b2);
            }
            else {
                throw new MyException("second operand is not a boolean");
            }
        }
        else {
            throw new MyException("first operand is not a boolean");
        }
        return v1;
    }

    @Override
    public IExp deepCopy() {
        return new LogicIExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new MyException("second operand is not a boolean");
            }
        }
        else {
            throw new MyException("first operand is not a boolean");
        }
    }

}
