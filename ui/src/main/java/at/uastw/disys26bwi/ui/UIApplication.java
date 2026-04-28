package at.uastw.disys26bwi.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UIApplication extends Application {
  private final EnergyService energyService = new EnergyService();

  @Override
  public void start(Stage stage) {
    BorderPane root = new BorderPane();
    root.setCenter(new Label("DISY26 UI"));
    
    Scene scene = new Scene(root, 800, 600);
    stage.setTitle("DISY26 - Energy Management");
    stage.setScene(scene);
    stage.show();
  }

  static void main() {
    launch();
  }
}
