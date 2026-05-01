package at.uastw.disys26bwi.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                java.util.Objects.requireNonNull(
                        UIApplication.class.getResource("/at.uastw.disys26bwi.ui/EnergyGUI.fxml"),
                        "EnergyGUI.fxml not found on classpath"
                )
        );

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("DISY26 - Energy Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
