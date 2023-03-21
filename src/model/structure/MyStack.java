package model.structure;

import exception.MyException;

import java.lang.reflect.Array;
import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;//a field whose type  CollectionType is an appropriate
                            // generic java library  collection

    public MyStack() {
        stack = new Stack<T>();
    }

    public T pop() throws MyException {
        if (isEmpty())
            throw new MyException("Empty stack");
        return stack.pop();
    }

    public void push(T v) {
        stack.push(v);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        List<T> lst = Arrays.asList((T[]) stack.toArray());
        String s = "";
        ListIterator<T> it = lst.listIterator(lst.size());

        while (it.hasPrevious()) {
            s += it.previous().toString() + "\n";
        }

        return s;
    }
}
