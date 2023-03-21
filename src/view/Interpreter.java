package view;

import controller.Controller;
import exception.MyException;
import model.PrgState;
import model.expression.*;
import model.statement.*;
import model.structure.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new AssignStmt("v",new ValueIExp(new IntValue(2))),
                        new PrintStmt(new VarIExp("v"))));

        try {
            ex1.typecheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex1, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }



        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithIExp('+',new ValueIExp(new IntValue(2)),
                                                new ArithIExp('*',new ValueIExp(new IntValue(3)),
                                                        new ValueIExp(new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b",new ArithIExp('+',new VarIExp("a"),
                                                new ValueIExp(new IntValue(1)))),
                                        new PrintStmt(new VarIExp("b"))))));

        try {
            ex2.typecheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueIExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarIExp("a"),
                                        new AssignStmt("v",new ValueIExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueIExp(new IntValue(3)))),
                                        new PrintStmt(new VarIExp("v"))))));

        try {
            ex3.typecheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex3, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueIExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarIExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                                        new CloseRFile(new VarIExp("varf"))))))))));

        try {
            ex4.typecheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex4, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new VarIExp("a")))))));

        try {
            ex5.typecheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex5, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new PrintStmt(new RhExp(new VarIExp("v"))),
                                                new PrintStmt(new ArithIExp('+',new RhExp(new RhExp(new VarIExp("a"))), new ValueIExp(new IntValue(5)))))))));

        try {
            ex6.typecheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex6, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new RhExp(new VarIExp("v"))),
                                new CompStmt(new WhStmt("v", new ValueIExp(new IntValue(30))),
                                        new PrintStmt(new ArithIExp('+', new RhExp(new VarIExp("v")), new ValueIExp(new IntValue(5))))))));

        try {
            ex7.typecheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex7, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(30))),
                                                new PrintStmt(new RhExp(new RhExp(new VarIExp("a")))))))));

        try {
            ex8.typecheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex8, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalIExp(new VarIExp("v"), new ValueIExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarIExp("v")), new AssignStmt("v", new ArithIExp('-', new VarIExp("v"), new ValueIExp(new IntValue(1)))))),
                                new PrintStmt(new VarIExp("v")))));

        try {
            ex9.typecheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex9, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(10))),
                                new CompStmt(new NewStatement("a", new ValueIExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WhStmt("a", new ValueIExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new RhExp(new VarIExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new RhExp(new VarIExp("a")))))))));

        try {
            ex10.typecheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex10, new MyDictionary<>(), new MyHeap(), new MyBarrierTable());
            IRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        } catch (MyException e) {
            throw new RuntimeException(e.getMessage());
        }

        menu.show();
    }
}