package model.structure;

import exception.MyException;
import javafx.util.Pair;
import model.value.Value;

import java.util.HashMap;
import java.util.List;

public class MyBarrierTable implements MyIBarrierTable{
    HashMap<Integer, Pair<Integer, List<Integer>>> barrierTable;
    Integer newFreeLocation;

    public MyBarrierTable() {
        barrierTable = new HashMap<>();
        newFreeLocation = 1;
    }
    @Override
    public Integer nextFreeLocation() {
        synchronized (this) {
            do {
                newFreeLocation++;
            } while (isDefined(newFreeLocation));
            return newFreeLocation;
        }
    }

    @Override
    public Integer getFreeLocation() {
        synchronized (this) {
            return newFreeLocation;
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        synchronized (this) {
            return barrierTable.containsKey(key);
        }
    }

    @Override
    public Integer add(Integer key, Pair<Integer, List<Integer>> val) throws MyException {
        synchronized (this) {
            if (isDefined(key))
                throw new MyException("key is already defined!");

            barrierTable.put(newFreeLocation, val);
            Integer foundLocation = newFreeLocation;
            nextFreeLocation();
            return foundLocation;
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(Integer key) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException("key is not defined in barrierTable!");
            return barrierTable.get(key);
        }
    }

    @Override
    public void remove(Integer key) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException("key is not defined in barrierTable!");
            barrierTable.remove(key);
            if (key < newFreeLocation)
                newFreeLocation = key;
        }
    }

    @Override
    public Pair<Integer, List<Integer>> update(Integer key, Pair<Integer, List<Integer>> val) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException("key is not defined in barrierTable!");
            Pair<Integer, List<Integer>> oldVal = barrierTable.get(key);
            barrierTable.replace(key, val);
            return oldVal;
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        synchronized (this) {
            return barrierTable;
        }
    }

    @Override
    public void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newHp) {
        synchronized (this) {
            barrierTable = newHp;
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            return barrierTable.toString();
        }
    }
}
