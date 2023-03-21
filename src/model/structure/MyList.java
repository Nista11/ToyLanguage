package model.structure;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList () {
        list = new ArrayList<>();
    }

    public void add(T v) {
        list.add(v);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        String s = "";
        for (T t : list) {
            s += t.toString() + " ";
        }
        return s;
    }
}
