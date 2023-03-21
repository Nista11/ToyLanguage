package model;

import exception.MyException;
import model.statement.IStmt;
import model.structure.*;
import model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    MyIBarrierTable barrierTable;
    IStmt   originalProgram; //optional field, but good to have
     int id;
     static int globalId = 0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot,
                    IStmt prg, MyIDictionary<String, BufferedReader> ft, MyIHeap hp, MyIBarrierTable bt) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = ft;
        heap = hp;
        barrierTable = bt;
        originalProgram = prg.deepCopy();//recreate the entire original prg
        stk.push(prg);
        id = incrementId();
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot,
                    MyIDictionary<String, BufferedReader> ft, MyIHeap hp, MyIBarrierTable bt) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = ft;
        heap = hp;
        barrierTable = bt;
        id = incrementId();
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {return out;}
    public MyIHeap getHeap(){return heap;}
    public MyIBarrierTable getBarrierTable() {return barrierTable;}

    public Integer getID() {return id;}
    public void setStk(MyIStack<IStmt> stk) {
        exeStack = stk;
    }

    public void setSymTable(MyIDictionary<String, Value> symTbl) {
        symTable = symTbl;
    }
    public void setBarrierTable(MyIBarrierTable bt) {barrierTable = bt;}

    public void setOut(MyIList<Value> ot) {
        out = ot;
    }
    public void setHeap(MyIHeap hp) {heap = hp;}

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> ft) {
        fileTable = ft;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public synchronized int incrementId() {
        globalId++;
        return globalId;
    }

    @Override
    public String toString() {
        return "Program id: " + id + ", Stack: " + exeStack.toString() + ", " + "SymTable: " + symTable.toString()
                + ", " + "out: " + out.toString();
    }

    public String toLog() {
        String s = "";
        s += "Program id: " + id + "\n";
        s += "ExeStack:\n";
        s += exeStack.toString() + "\n";
        s += "SymTable:\n";
        s += symTable.toString() + "\n";
        s += "Out:\n";
        s += out.toString() + "\n";
        s += "FileTable:\n";
        s += fileTable.toString() + "\n";
        s += "HeapTable:\n";
        s += heap.toString() + "\n";
        return s;
    }
}
