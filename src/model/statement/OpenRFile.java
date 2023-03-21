package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIDictionary;
import model.type.StringType;
import model.type.Type;
import model.value.Value;

import java.io.BufferedReader;

public class OpenRFile implements IStmt {
    private IExp exp;

    public OpenRFile(IExp exp) {
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        if (val.getType().equals(new StringType())) {
            MyIDictionary<String, BufferedReader> ft = state.getFileTable();
            if (ft.isDefined(val.toString())) {
                throw new MyException("File already opened");
            }
            else {
                try {
                    BufferedReader br = new BufferedReader(new java.io.FileReader(val.toString()));
                    ft.put(val.toString(), br);
                    state.setFileTable(ft);
                }
                catch (Exception e) {
                    throw new MyException("File cannot be opened");
                }
            }
        }
        else {
            throw new MyException("The expression is not a string");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile " + exp.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new MyException("The expression is not a string");
        }
    }
}
