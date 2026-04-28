module org.example.disysfx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires java.logging;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.annotation;


  opens org.example.disysfx to javafx.fxml;
  exports org.example.disysfx;
}