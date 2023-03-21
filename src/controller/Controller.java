package controller;

import exception.MyException;
import model.PrgState;
import model.statement.IStmt;
import model.structure.MyIStack;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = true;
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap, List<Integer> heapTableAddr){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || heapTableAddr.contains(e.getKey())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(
                    symTableAddresses, p.getHeap().getContent(),
                    getAddrFromHeapTable(p.getHeap().getContent().values())));
        });
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues){
        return heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
                display(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
                List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                        .map(future -> { try { return future.get();}
                        catch(InterruptedException | ExecutionException e) {
        //here you can treat the possible
        // exceptions thrown by statements
        // execution, namely the green part // from previous allStep method
                            }
                            return null;
                        })
                        .filter(p -> p!=null)
                        .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //------------------------------------------------------------------------------
        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            //HERE you can call conservativeGarbageCollector
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
        //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }

    public void oneStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        oneStepForAllPrg(prgList);
        conservativeGarbageCollector(prgList);
        executor.shutdownNow();
    }



    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void display(PrgState state) {
        if (displayFlag) {
            System.out.println(state.toString());
        }
    }

    public List<PrgState> getProgramStates() {
        return this.repo.getPrgList();
    }

    public void setProgramStates(List<PrgState> l) {
        this.repo.setPrgList(l);
    }
}

