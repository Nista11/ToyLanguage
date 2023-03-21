package view;

import controller.Controller;
import exception.MyException;
import model.PrgState;
import model.expression.ArithIExp;
import model.expression.ValueIExp;
import model.expression.VarIExp;
import model.statement.*;
import model.structure.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


public class View {
    boolean running;

    void runProgram(IStmt stmt) throws MyException, IOException, InterruptedException {
        MyIStack<IStmt> stack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        MyIBarrierTable barrierTable = new MyBarrierTable();
        PrgState state = new PrgState(stack, symTable, out, stmt, fileTable, heap, barrierTable);
        IRepository repo = new Repository(state, "logFile.txt");
        Controller con = new Controller(repo);
        con.allStep();
        System.out.println(state.getOut().toString());
    }

    void start() throws MyException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int option;
        running = true;
        while (running) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 0 -> running = false;
                case 1 -> example1();
                case 2 -> example2();
                case 3 -> example3();
                case 4 -> example4();
                default -> System.out.println("Invalid option!");
            }
            System.out.println();
        }
    }

    void printMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Example 1:\nint v; v=2;Print(v)");
        System.out.println("2. Example 2:\nint a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("3. Example 3:\nbool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        System.out.println("4. Example 4:\n\t\tString varf;\n" +
                "        varf=\"test.in\";\n" +
                "        OpenRFile open = new OpenRFile(varf);\n" +
                "        open.e\n" +
                "        String varc=\"\";\n" +
                "        ReadFile read = new ReadFile(varf, varc);\n" +
                "        print(varc);\n" +
                "        readFile(varf,varc);\n" +
                "        print(varc);\n" +
                "        closeRFile(varf);");
        System.out.println("Enter option: ");
    }

    void example1() throws MyException, IOException, InterruptedException {
        //int v; v=2;Print(v)  is represented as:
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new AssignStmt("v",new ValueIExp(new IntValue(2))),
                        new PrintStmt(new VarIExp("v"))));
        runProgram(ex1);
    }

    void example2() throws MyException, IOException, InterruptedException {
        //int a;int b; a=2+3*5;b=a+1;Print(b)  is represented as:
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
        runProgram(ex2);
    }

    void example3() throws MyException, IOException, InterruptedException {
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)   is represented as:
        //IStmt ex4 = new CompStmt(new PrintStmt(new ValueExp(new IntValue(3))), new PrintStmt(new ValueExp(new IntValue(2))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueIExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarIExp("a"),
                                        new AssignStmt("v",new ValueIExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueIExp(new IntValue(3)))),
                                        new PrintStmt(new VarIExp("v"))))));
        runProgram(ex3);
    }

    void example4() throws MyException, IOException, InterruptedException {

       IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueIExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarIExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                                        new CloseRFile(new VarIExp("varf"))))))))));
       runProgram(ex4);
    }
}
