package com.example.javafx;

import com.example.common.dto.MessageDto;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MessageDto dto = new MessageDto("Hello from JavaFX!");

        Label label = new Label(dto.getMessage());
        label.setStyle("-fx-font-size: 24px;");

        StackPane root = new StackPane(label);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("DISY26 JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
