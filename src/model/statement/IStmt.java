package model.statement;

import exception.MyException;
import model.PrgState;
import model.structure.MyIDictionary;
import model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
