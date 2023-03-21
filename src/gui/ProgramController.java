package gui;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.PrgState;
import model.statement.IStmt;
import model.structure.*;
import model.value.Value;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

class MyPair<A, B> {
    A first;
    B second;

    public MyPair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramController {
    public ListView<Integer> programStates;
    public TextField programStatesNumber;
    public ListView<String> output;
    public ListView<String> fileTable;
    public TableView<MyPair<String, Value>> symbolTable;
    public TableColumn<MyPair<String, Value>, String> variableNameColumn;
    public TableColumn<MyPair<String, Value>, String> symbolValueColumn;
    public TableView<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>> barrierTableView;

    public TableColumn<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>, Integer> indexBarrierTableColumn;

    public TableColumn<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>, Integer> valueBarrierTableColumn;

    public TableColumn<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>, String> listBarrierTableColumn;
    public ListView<String> executionStack;
    private Controller controller;

    @FXML
    public TableView<MyPair<Integer, Value>> heapTable;
    @FXML
    public TableColumn<MyPair<Integer, Value>, Integer> heapAddressColumn;
    @FXML
    public TableColumn<MyPair<Integer, Value>, String> heapValueColumn;

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        symbolValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        indexBarrierTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueBarrierTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getKey()).asObject());
        listBarrierTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue().toString()));
        programStates.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, oldIndex, newIndex) -> {updateSymbolTable(); updateExecutionStack();}
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
        updateAll();
    }

    public void onRunOneStepButtonClick(MouseEvent mouseEvent) throws InterruptedException {
        if (controller != null) {
            List<PrgState> programStates = controller.getProgramStates();
            if (programStates.size() > 0) {
                controller.oneStep();
                updateAll();
                programStates = controller.removeCompletedPrg(controller.getProgramStates());
                controller.setProgramStates(programStates);
                updateProgramStates();
            }
        }
    }

    private PrgState getCurrentProgramState() {
        if (controller.getProgramStates().size() == 0)
            return null;
        else {
            int currentId = programStates.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getProgramStates().get(0);
            else
                return controller.getProgramStates().get(currentId);
        }
    }

    void updateAll() {
        updateOutput();
        updateHeapTable();
        updateFileTable();
        updateProgramStates();
        updateSymbolTable();
        updateExecutionStack();
        updateBarrierTableView();
    }

    void updateBarrierTableView() {
        PrgState prgState = getCurrentProgramState();
        if (prgState != null) {
            MyIBarrierTable barrierTable = prgState.getBarrierTable();
            List<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>> barrierList = new ArrayList<>();
            for (Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>> entry : barrierTable.getContent().entrySet()) {
                barrierList.add(entry);
            }
            barrierTableView.setItems(FXCollections.observableArrayList(barrierList));
        }
    }

    void updateProgramStatesNumber() {
        programStatesNumber.setText(Integer.toString(controller.getProgramStates().size()));
    }

    void updateFileTable () {
        PrgState prgState = controller.getProgramStates().get(0);
        MyIDictionary<String, BufferedReader> fileTableDict = prgState.getFileTable();
        List<String> stringList = new ArrayList<>(fileTableDict.getContent().keySet());
        fileTable.setItems(FXCollections.observableList(stringList));
    }

    void updateOutput() {
        PrgState programState = controller.getProgramStates().get(0);
        MyIList<Value> outputList = programState.getOut();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < outputList.size(); i++) {
            stringList.add(outputList.get(i).toString());
        }
        output.setItems(FXCollections.observableList(stringList));
    }
    void updateHeapTable() {
        PrgState programState = controller.getProgramStates().get(0);
        MyIHeap heap = programState.getHeap();
        ArrayList<MyPair<Integer, Value>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, Value> entry: heap.getContent().entrySet()) {
            heapEntries.add(new MyPair<>(entry.getKey(), entry.getValue()));
        }
        heapTable.setItems(FXCollections.observableArrayList(heapEntries));
    }

    void updateProgramStates() {
        List<PrgState> programStatesList = controller.getProgramStates();
        List<Integer> programStatesID = programStatesList.stream().map(PrgState::getID).collect(Collectors.toList());
        programStates.setItems(FXCollections.observableList(programStatesID));
        updateProgramStatesNumber();
    }

    void updateSymbolTable() {
        PrgState prgState = getCurrentProgramState();
        if (prgState != null) {
            MyIDictionary<String, Value> symtb = prgState.getSymTable();
            List<MyPair<String, Value>> entries = new ArrayList<>();
            for (Map.Entry<String, Value> entry : symtb.getContent().entrySet()) {
                entries.add(new MyPair<>(entry.getKey(), entry.getValue()));
            }
            symbolTable.setItems(FXCollections.observableList(entries));
        }
    }

    void updateExecutionStack() {
        PrgState prgState = getCurrentProgramState();
        if (prgState != null) {
            MyIStack<IStmt> stk = prgState.getStk();
            List<String> stringList = new ArrayList<>();
            for (IStmt stm : stk.getStack()) {
                stringList.add(stm.toString());
            }
            Collections.reverse(stringList);
            executionStack.setItems(FXCollections.observableList(stringList));
        }
    }
}
