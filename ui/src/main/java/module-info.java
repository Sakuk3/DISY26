module at.uastw.disys26bwi.ui {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires java.logging;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.annotation;
  requires at.uastw.disys26bwi.common;


  opens at.uastw.disys26bwi.ui to javafx.fxml;
  exports at.uastw.disys26bwi.ui;
}