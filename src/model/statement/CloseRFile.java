package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIDictionary;
import model.type.StringType;
import model.type.Type;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private IExp exp;

    public CloseRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        if (val.getType().equals(new StringType())) {
            MyIDictionary<String, BufferedReader> ft = state.getFileTable();
            if (ft.isDefined(val.toString())) {
                try {
                    BufferedReader br = ft.lookup(val.toString());
                    br.close();
                    ft.remove(val.toString());
                    state.setFileTable(ft);
                } catch (IOException e) {
                    throw new MyException("File cannot be closed");
                }
            }
            else {
                throw new MyException("File not in file table");
            }
        }
        else {
            throw new MyException("The expression is not a string");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseRFile " + exp.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new MyException("CloseRFile: The expression is not a string");
        }
    }
}
