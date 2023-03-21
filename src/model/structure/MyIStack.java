package model.structure;

import exception.MyException;

import java.util.Stack;

public interface MyIStack <T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    Stack<T> getStack();
}
