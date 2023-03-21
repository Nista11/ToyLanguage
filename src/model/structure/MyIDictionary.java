package model.structure;

import exception.MyException;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary <K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws MyException;
    void update(K key, V value) throws MyException;
    void remove(K key) throws MyException;
    void put(K key, V value);
    Map<K, V> getContent();
    Collection<V> values();
    MyIDictionary<K, V> deepCopy();
}
