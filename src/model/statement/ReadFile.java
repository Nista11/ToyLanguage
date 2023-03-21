package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIDictionary;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ReadFile implements IStmt {
    private IExp exp;
    private String varName;

    public ReadFile(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        MyIDictionary<String, Value> sym = state.getSymTable();
        if (sym.isDefined(varName) && sym.lookup(varName).getType().equals(new IntType())) {
            if (val.getType().equals(new StringType())) {
                MyIDictionary<String, BufferedReader> ft = state.getFileTable();
                if (!ft.isDefined(((StringValue)val).getVal())) {
                    throw new MyException("value not found in file table");
                }
                else {
                    try {
                        BufferedReader br = ft.lookup(((StringValue) val).getVal());
                        String line = br.readLine();
                        if (line == null) {
                            sym.put(varName, new IntValue(0));
                        }
                        else {
                            sym.put(varName, new IntValue(Integer.parseInt(line)));
                        }
                        state.setSymTable(sym);
                    }
                    catch (Exception e) {
                        throw new MyException("could not read from buffer");
                    }
                }
            }

            else {
                throw new MyException("variable is not of string type");
            }
        }
        else {
            throw new MyException("variable not define or not of int type");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "ReadFile " + exp.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            if (typeEnv.lookup(varName).equals(new IntType())) {
                return typeEnv;
            }
            else {
                throw new MyException("variable not of int type");
            }
        }
        else {
            throw new MyException("expression is not of string type");
        }
    }
}
