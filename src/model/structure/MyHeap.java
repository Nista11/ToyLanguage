package model.structure;

import exception.MyException;
import model.value.Value;

import java.util.HashMap;

public class MyHeap implements MyIHeap {
    HashMap<Integer, Value> heap;
    Integer newFreeLocation;

    public MyHeap() {
        heap = new HashMap<>();
        newFreeLocation = 1;
    }

    @Override
    public Integer nextFreeLocation() {
        do {
            newFreeLocation++;
        } while (isDefined(newFreeLocation));
        return newFreeLocation;
    }

    @Override
    public Integer getFreeLocation() {
        return newFreeLocation;
    }

    @Override
    public boolean isDefined(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public Integer add(Value val) {
        heap.put(newFreeLocation, val);
        Integer foundLocation = newFreeLocation;
        nextFreeLocation();
        return foundLocation;
    }

    @Override
    public Value get(Integer key) throws MyException {
        if (!isDefined(key))
            throw new MyException("key is not defined in heap!");
        return heap.get(key);
    }

    @Override
    public void remove(Integer key) throws MyException {
        if (!isDefined(key))
            throw new MyException("key is not defined in heap!");
        heap.remove(key);
        if (key < newFreeLocation)
            newFreeLocation = key;
    }

    @Override
    public Value update(Integer key, Value val) throws MyException {
        if (!isDefined(key))
            throw new MyException("key is not defined in heap!");
        Value oldVal = heap.get(key);
        heap.replace(key, val);
        return oldVal;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public void setContent(HashMap<Integer, Value> newHp) {
        heap = newHp;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
