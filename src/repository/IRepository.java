package repository;

import exception.MyException;
import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState p) throws MyException, IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);
}
