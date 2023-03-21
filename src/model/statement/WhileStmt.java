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

public class WhileStmt implements IStmt{
    IExp exp;
    IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof BoolValue)) {
            throw new MyException("condition exp is not a boolean!");
        }
        else {
            BoolValue boolVal = (BoolValue) val;
            if (boolVal.getVal()) {
                MyIStack<IStmt> stk = state.getStk();
                stk.push(deepCopy());
                stk.push(stmt.deepCopy());
                state.setStk(stk);
            }
            return state;
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + "){" + stmt.toString() + "}";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("condition of while is not of type bool");
    }
}
