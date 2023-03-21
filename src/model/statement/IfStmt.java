package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIDictionary;
import model.structure.MyIStack;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        exp=e;
        thenS=t;
        elseS=el;
    }

    public String toString() {
        return "(IF(" + exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    public PrgState execute(PrgState state) throws MyException {
        Value v = exp.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stk = state.getStk();
        if (v.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) v;
            if (b.getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        else
            throw new MyException("type of if else statement is not boolean");
        state.setStk(stk);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool");
    }
}