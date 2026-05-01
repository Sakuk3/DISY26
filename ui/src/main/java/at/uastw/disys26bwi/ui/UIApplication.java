package at.uastw.disys26bwi.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UIApplication extends Application {
    private final EnergyService energyService = new EnergyService();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UIApplication.class.getResource("EnergyGUI.fxml"));

        BorderPane root = new BorderPane();
        root.setCenter(new Label("DISY26 UI"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("DISY26 - Energy Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
