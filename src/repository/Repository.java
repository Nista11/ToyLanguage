package repository;

import exception.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    int currentIndex;
    String logFilePath;

    public Repository(PrgState prg, String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(prg);
        this.currentIndex = 0;
        this.logFilePath = logFilePath;
    }


    @Override
    public void logPrgStateExec(PrgState prg) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.println(prg.toLog());
        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        prgStates = l;
    }
}
