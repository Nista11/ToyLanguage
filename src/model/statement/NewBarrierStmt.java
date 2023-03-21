package model.statement;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.expression.IExp;
import model.structure.MyIBarrierTable;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt{
    String var;
    IExp exp;
    Lock lock;

    public NewBarrierStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
        lock = new ReentrantLock();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIBarrierTable bt = state.getBarrierTable();
        MyIDictionary<String, Value> symt = state.getSymTable();
        MyIHeap hp = state.getHeap();
        IntValue val = (IntValue) exp.eval(symt, hp);
        int Nr = val.getVal();
        int freeAddr = bt.getFreeLocation();
        bt.add(freeAddr, new Pair<>(Nr, new ArrayList<>()));
        if (symt.isDefined(var)) {
            symt.update(var, new IntValue(freeAddr));
        }

        else {
            throw new MyException("var is not defined in heap!");
        }
        state.setBarrierTable(bt);
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("var is not of int type!");
        if (!exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("exp is not of int type!");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NewBarrier(" + var + ", " + exp + ")";
    }
}
