package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Program selection");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoaderProgram = new FXMLLoader(Main.class.getResource("/program-view.fxml"));
        Scene sceneProgram = new Scene(fxmlLoaderProgram.load(), 970, 750);
        Stage stageProgram = new Stage();
        stageProgram.setTitle("Current program");
        stageProgram.setScene(sceneProgram);

        MainController mainController = fxmlLoader.getController();
        ProgramController programController = fxmlLoaderProgram.getController();
        mainController.setProgramController(programController);
        stageProgram.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
