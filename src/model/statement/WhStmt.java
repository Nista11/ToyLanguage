package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class WhStmt implements IStmt{
    String varName;
    IExp exp;

    public WhStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symt = state.getSymTable();
        if (symt.isDefined(varName)) {
            Value val = symt.lookup(varName);
            if (val.getType() instanceof RefType) {
                MyIHeap hp = state.getHeap();
                RefValue refVal = (RefValue) val;
                if (!hp.isDefined(refVal.getAddr())) {
                    throw new MyException("address is not in heap!");
                }
                else {
                    Value evalVal = exp.eval(symt, hp);
                    if (!evalVal.getType().equals(refVal.getLocationType())) {
                        throw new MyException("types between values are not equal!");
                    }
                    else {
                        hp.update(refVal.getAddr(), evalVal);
                        state.setHeap(hp);
                    }
                }
            }
            else throw new MyException("val not of ref type!");
        }
        else throw new MyException("varName not defined!");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Wh(" + varName + ',' + exp.toString() + ')';
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(varName).equals(new RefType(exp.typecheck(typeEnv)))) {
            return typeEnv;
        }
        else throw new MyException("types are not equal!");
    }
}
