package model.statement;

import exception.MyException;
import model.PrgState;
import model.structure.MyIDictionary;
import model.type.Type;
import model.value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String n, Type t) {
        name = n;
        typ = t;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> sysTbl = state.getSymTable();
        if (!sysTbl.isDefined(name)) {
            sysTbl.put(name, typ.defaultValue());
        }
        else {
            throw new MyException("variable already defined");
        }
        state.setSymTable(sysTbl);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ);
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name,typ);
        return typeEnv;
    }
}
