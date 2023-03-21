package model.structure;

import exception.MyException;
import model.value.Value;

import java.util.HashMap;

public interface MyIHeap {
    Integer newFreeLocation = 0;
    Integer nextFreeLocation();
    Integer getFreeLocation();
    boolean isDefined(Integer key);
    Integer add(Value val);
    Value get(Integer key) throws MyException;
    void remove(Integer key) throws MyException;
    Value update(Integer key, Value val) throws MyException;
    public HashMap<Integer, Value> getContent();
    public void setContent(HashMap<Integer, Value> newHp);
}
