package model.statement;

import exception.MyException;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyDictionary;
import model.structure.MyIDictionary;
import model.structure.MyIStack;
import model.structure.MyStack;
import model.type.Type;
import model.value.Value;

import java.util.Map;

public class ForkStmt implements IStmt {
    IStmt stm;

    public ForkStmt(IStmt stm) {
        this.stm = stm;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = new MyStack<>();
        stk.push(stm);
        MyIDictionary<String, Value> symt = new MyDictionary<>();
        for (Map.Entry<String, Value> e : state.getSymTable().getContent().entrySet()) {
            symt.put(e.getKey(), e.getValue());
        }

        return new PrgState(stk, symt, state.getOut(), state.getFileTable(), state.getHeap(), state.getBarrierTable());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stm.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stm.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stm.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
