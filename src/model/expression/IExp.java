package model.expression;

import exception.MyException;
import model.structure.MyIDictionary;
import model.structure.MyIHeap;
import model.type.Type;
import model.value.Value;

public interface IExp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap hp) throws MyException;
    IExp deepCopy();
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}