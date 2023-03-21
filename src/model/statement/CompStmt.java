package model.statement;

import exception.MyException;
import model.PrgState;
import model.structure.MyIDictionary;
import model.structure.MyIStack;
import model.type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt f, IStmt s) {
        first = f;
        snd = s;
    }

    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }

  public PrgState execute(PrgState state) {
      MyIStack<IStmt> stk=state.getStk();
      stk.push(snd);
      stk.push(first);
      state.setStk(stk);
      return null;
  }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        // MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        // MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        // return typEnv2;
        return snd.typecheck(first.typecheck(typeEnv));
    }
}