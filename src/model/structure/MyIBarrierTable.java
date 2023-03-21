package model.structure;

import exception.MyException;
import javafx.util.Pair;
import model.value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MyIBarrierTable {
    Integer newFreeLocation = 0;
    Integer nextFreeLocation();
    Integer getFreeLocation();
    boolean isDefined(Integer key);
    Integer add(Integer key, Pair<Integer, List<Integer>> val) throws MyException;
    Pair<Integer, List<Integer>> get(Integer key) throws MyException;
    void remove(Integer key) throws MyException;
    Pair<Integer, List<Integer>> update(Integer key, Pair<Integer, List<Integer>> val) throws MyException;
    HashMap<Integer, Pair<Integer, List<Integer>>> getContent();
    void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newHp);
}
