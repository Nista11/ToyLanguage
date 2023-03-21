package model.structure;

import exception.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary <K, V> implements MyIDictionary <K,V> {
    HashMap<K, V> dict;

    public MyDictionary() {
        dict = new HashMap<K, V>();
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    public V lookup(K key) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key " + key + " not found");
        }
        return dict.get(key);
    }

    public void update(K key, V value) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key " + key + " not found");
        }
        dict.put(key, value);
    }


    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return dict;
    }

    @Override
    public Collection<V> values() {
        return dict.values();
    }

    public void remove(K key) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key " + key + " not found");
        }
        dict.remove(key);
    }

    @Override
    public String toString() {
        return this.dict.toString();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> newDict = new MyDictionary<K, V>();
        for (K key : dict.keySet()) {
            newDict.put(key, dict.get(key));
        }
        return newDict;
    }
}
