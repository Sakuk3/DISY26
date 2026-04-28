package org.example.disysfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HelloController {

  private final BookService bookService = new BookService();
  @FXML
  private Label lbResult;

  @FXML
  private TextField tfNum1;

  @FXML
  private TextField tfNum2;

  @FXML
  private Button btPlus;

  @FXML
  private Button btMinus;

  @FXML
  private Button btMulti;

  @FXML
  private Button btDiv;

  @FXML
  protected void onPlusButtonClick() {
    String title = tfNum1.getText();
    String order = tfNum2.getText();
    lbResult.setText(this.bookService.getBooks(title, order).stream().map(BookDto::title).reduce((t1, t2) -> t1 + ", " + t2).orElse("No books found"));
  }

  @FXML
  protected void onMinusButtonClick() {
    lbResult.setText("");
//    lbResult.setText(
//      Double.toString(this.safeParseDouble(tfNum1) - this.safeParseDouble(tfNum2))
//    );
  }

  @FXML
  protected void onMultiButtonClick() {
    lbResult.setText(
      Double.toString(this.safeParseDouble(tfNum1) * this.safeParseDouble(tfNum2))
    );
  }

  @FXML
  protected void onDivButtonClick() {
    Double num1 = this.safeParseDouble(tfNum1);
    Double num2 = this.safeParseDouble(tfNum2);
    if (num2 == 0) {
      lbResult.setText("Success: Singularity created");
    } else {
      lbResult.setText(
        Double.toString(num1 / num2)
      );
    }
  }

  protected Double safeParseDouble(TextField tf) {
    try {
      return Double.parseDouble(tf.getText());
    } catch (NumberFormatException error) {
      lbResult.setText("Error: Not a number in " + tf.getId());
      return Double.NaN;
    }
  }
}
