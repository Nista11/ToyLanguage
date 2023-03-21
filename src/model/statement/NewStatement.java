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

public class NewStatement implements IStmt {
    String varName;
    IExp exp;

    public NewStatement(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        if (symtbl.isDefined(varName)) {
            Value val = symtbl.lookup(varName);
            if (val.getType() instanceof RefType) {
                Value expVal = exp.eval(symtbl, state.getHeap());
                Type locationType = ((RefValue)val).getLocationType();
                if (expVal.getType().equals(locationType)) {
                    MyIHeap heap = state.getHeap();
                    Integer position = heap.add(expVal);
                    symtbl.update(varName, new RefValue(position, locationType));
                    state.setHeap(heap);
                    state.setSymTable(symtbl);
                }
                else throw new MyException("types are not equal!");
            }
            else throw new MyException("value not of ref type!");
        }
        else throw new MyException("variable not defined!");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStatement(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "New(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }
}
