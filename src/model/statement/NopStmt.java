package model.statement;

import exception.MyException;
import model.PrgState;
import model.structure.MyIDictionary;
import model.type.Type;

public class NopStmt implements IStmt {
    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
