package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.expression.NotIExp;
import model.structure.MyIDictionary;
import model.structure.MyIStack;
import model.type.BoolType;
import model.type.Type;

public class RepeatStmt implements IStmt {
    private IStmt stmt;
    private IExp exp;

    public RepeatStmt(IStmt stmt, IExp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt newStmt = new CompStmt(stmt, new WhileStmt(new NotIExp(exp), stmt));
        MyIStack<IStmt> stk = state.getStk();
        stk.push(newStmt);
        state.setStk(stk);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatStmt(stmt.deepCopy(), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("expression is not boolean!");
    }

    @Override
    public String toString() {
        return "repeat " + stmt + " until " + exp;
    }
}
