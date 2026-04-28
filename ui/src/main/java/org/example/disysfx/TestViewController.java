package org.example.disysfx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class TestViewController {
  @FXML
  private Label statusLabel;

  @FXML
  private Button sendButton;

  @FXML
  private Button muteButton;

  @FXML
  private Button cameraButton;

  @FXML
  private Button disconnectButton;

  @FXML
  private Button quitButton;

  @FXML
  private ListView<String> participantsListView;

  @FXML
  private TextArea chatHistoryArea;

  @FXML
  private TextArea messageInputArea;

  private boolean connected = true;

  @FXML
  private void initialize() {
    sendButton.disableProperty().bind(Bindings.createBooleanBinding(
      () -> !connected || messageInputArea.getText() == null || messageInputArea.getText().trim().isEmpty(),
      messageInputArea.textProperty()
    ));

    sendButton.setDefaultButton(true);
    quitButton.setCancelButton(true);

    participantsListView.getItems().setAll(
      "Alice",
      "Bob",
      "Charlie"
    );

    chatHistoryArea.setText("Welcome to the session.\n");
    statusLabel.setText("Ready");
  }

  @FXML
  protected void onSendMessage() {
    String message = messageInputArea.getText().trim();
    if (message.isEmpty()) {
      return;
    }

    chatHistoryArea.appendText("Me: " + message + "\n");
    messageInputArea.clear();
    statusLabel.setText("Message sent");
  }

  @FXML
  protected void onMuteToggle() {
    if (!connected) {
      statusLabel.setText("Reconnected to toggle mute");
      return;
    }
    toggleStatus("Mute toggled");
  }

  @FXML
  protected void onCameraToggle() {
    if (!connected) {
      statusLabel.setText("Reconnected to toggle camera");
      return;
    }
    toggleStatus("Camera toggled");
  }

  @FXML
  protected void onDisconnect() {
    connected = false;
    muteButton.setDisable(true);
    cameraButton.setDisable(true);
    disconnectButton.setText("Disconnected");
    messageInputArea.setDisable(true);
    toggleStatus("Disconnected");
  }

  @FXML
  protected void onNewSession() {
    connected = true;
    muteButton.setDisable(false);
    cameraButton.setDisable(false);
    disconnectButton.setText("Disconnect");
    messageInputArea.setDisable(false);
    chatHistoryArea.clear();
    chatHistoryArea.setText("New session started.\n");
    messageInputArea.clear();
    statusLabel.setText("New session");
  }

  @FXML
  protected void onOpenSession() {
    statusLabel.setText("Open session not implemented");
  }

  @FXML
  protected void onCloseSession() {
    statusLabel.setText("Session closed");
  }

  @FXML
  protected void onSaveSession() {
    statusLabel.setText("Session saved");
  }

  @FXML
  protected void onSaveSessionAs() {
    statusLabel.setText("Save As not implemented");
  }

  @FXML
  protected void onRevertSession() {
    statusLabel.setText("Session reverted");
  }

  @FXML
  protected void onPreferences() {
    statusLabel.setText("Preferences not implemented");
  }

  @FXML
  protected void onQuit() {
    Platform.exit();
  }

  private void toggleStatus(String message) {
    statusLabel.setText(message);
  }
}



