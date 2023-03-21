package model.statement;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.structure.MyIBarrierTable;
import model.structure.MyIDictionary;
import model.structure.MyIStack;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public class AwaitStmt implements IStmt{
    String var;

    public AwaitStmt(String var) {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symt = state.getSymTable();
        if (!symt.isDefined(var))
            throw new MyException("var is not define in symbol table!");
        else {
            IntValue indexVal = (IntValue) symt.lookup(var);
            int index = indexVal.getVal();
            MyIBarrierTable bt = state.getBarrierTable();
            MyIStack<IStmt> stk = state.getStk();
            if (!bt.isDefined(index)) {
                throw new MyException("index not found in barried table!");
            }
            else {
                Pair<Integer, List<Integer>> entry = bt.get(index);
                int NL = entry.getValue().size();
                if (entry.getKey() > NL) {
                    if (entry.getValue().contains(state.getID())) {
                        stk.push(this);
                        state.setStk(stk);
                    }
                    else {
                        entry.getValue().add(state.getID());
                        bt.update(index, new Pair<>(entry.getKey(), entry.getValue()));
                        state.setBarrierTable(bt);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException("var is not of int type!");
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}
