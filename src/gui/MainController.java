package gui;

import controller.Controller;
import exception.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import view.Command;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.util.ArrayList;
import java.util.HashMap;


public class MainController {
    ProgramController programController;

    @FXML
    public ListView<IStmt> programList;

    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }
    @FXML
    protected void onExecuteProgramButtonClick() {
        IStmt statement = programList.getSelectionModel().getSelectedItems().get(0);
        if (statement != null) {
            try {
                statement.typecheck(new MyDictionary<>());
                PrgState program = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), statement, new MyDictionary<>(), new MyHeap(), new MyBarrierTable() {
                });
                int index = programList.getSelectionModel().getSelectedIndex();
                IRepository repository = new Repository(program, "log" + index + ".txt");
                Controller controller = new Controller(repository);
                programController.setController(controller);
            } catch (MyException e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Program error!");
                alert.setHeaderText("A program error has occurred!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void initialize() {
        ArrayList<IStmt> statementList = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(
                        new AssignStmt("v",new ValueIExp(new StringValue("a"))),
                        new PrintStmt(new VarIExp("v"))));

        statementList.add(ex1);



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

        statementList.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueIExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarIExp("a"),
                                        new AssignStmt("v",new ValueIExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueIExp(new IntValue(3)))),
                                        new PrintStmt(new VarIExp("v"))))));

        statementList.add(ex3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueIExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarIExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                        new CompStmt(new ReadFile(new VarIExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                                        new CloseRFile(new VarIExp("varf"))))))))));

        statementList.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new VarIExp("a")))))));

        statementList.add(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new PrintStmt(new RhExp(new VarIExp("v"))),
                                                new PrintStmt(new ArithIExp('+',new RhExp(new RhExp(new VarIExp("a"))), new ValueIExp(new IntValue(5)))))))));

        statementList.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new RhExp(new VarIExp("v"))),
                                new CompStmt(new WhStmt("v", new ValueIExp(new IntValue(30))),
                                        new PrintStmt(new ArithIExp('+', new RhExp(new VarIExp("v")), new ValueIExp(new IntValue(5))))))));

        statementList.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStatement("a", new VarIExp("v")),
                                        new CompStmt(new NewStatement("v", new ValueIExp(new IntValue(30))),
                                                new PrintStmt(new RhExp(new RhExp(new VarIExp("a")))))))));

        statementList.add(ex8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalIExp(new VarIExp("v"), new ValueIExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarIExp("v")), new AssignStmt("v", new ArithIExp('-', new VarIExp("v"), new ValueIExp(new IntValue(1)))))),
                                new PrintStmt(new VarIExp("v")))));

        statementList.add(ex9);

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(10))),
                                new CompStmt(new NewStatement("a", new ValueIExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WhStmt("a", new ValueIExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new RhExp(new VarIExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarIExp("v")), new PrintStmt(new RhExp(new VarIExp("a")))))))));

        statementList.add(ex10);

        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(
                        new VarDeclStmt("y", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueIExp(new IntValue(0))),
                                new CompStmt(
                                        new RepeatStmt(
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new PrintStmt(new VarIExp("v")),
                                                                        new AssignStmt("v", new ArithIExp('-', new VarIExp("v"), new ValueIExp(new IntValue(1))))
                                                                        )
                                                        ),
                                                        new AssignStmt("v", new ArithIExp('+', new VarIExp("v"), new ValueIExp(new IntValue(1))))
                                                ),
                                                new RelationalIExp(new VarIExp("v"), new ValueIExp(new IntValue(3)), "==")
                                        ),
                                        new CompStmt(
                                                new AssignStmt("x", new ValueIExp(new IntValue(1))),
                                                new CompStmt(
                                                        new NopStmt(),
                                                        new CompStmt(
                                                                new AssignStmt("y", new ValueIExp(new IntValue(3))),
                                                                new CompStmt(
                                                                        new NopStmt(),
                                                                        new PrintStmt(new ArithIExp('*', new VarIExp("v"), new ValueIExp(new IntValue(10))))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ))
                );

        statementList.add(ex11);

        IStmt ex12 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStatement("v1", new ValueIExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStatement("v2", new ValueIExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStatement("v3", new ValueIExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewBarrierStmt("cnt", new RhExp(new VarIExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new CompStmt(
                                                                                                        new WhStmt("v1", new ArithIExp('*', new RhExp(new VarIExp("v1")), new ValueIExp(new IntValue(10)))),
                                                                                                        new PrintStmt(new RhExp(new VarIExp("v1")))
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new AwaitStmt("cnt"),
                                                                                                        new CompStmt(
                                                                                                                new WhStmt("v2", new ArithIExp('*', new RhExp(new VarIExp("v2")), new ValueIExp(new IntValue(10)))),
                                                                                                                new CompStmt(
                                                                                                                        new WhStmt("v2", new ArithIExp('*', new RhExp(new VarIExp("v2")), new ValueIExp(new IntValue(10)))),
                                                                                                                        new PrintStmt(new RhExp(new VarIExp("v2")))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new PrintStmt(new RhExp(new VarIExp("v3")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        statementList.add(ex12);

        programList.setItems(FXCollections.observableArrayList(statementList));
    }
}