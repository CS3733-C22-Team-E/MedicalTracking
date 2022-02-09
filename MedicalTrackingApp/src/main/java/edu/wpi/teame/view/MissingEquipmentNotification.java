package edu.wpi.teame.view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MissingEquipmentNotification {
  GridPane errorPane = new GridPane();

  public MissingEquipmentNotification(String equipmentName) {
    Text titleText = new Text("Warning: Missing Equipment");
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    errorPane.add(titleText, 0, 0);

    String errorTextString = "Equipment missing: " + equipmentName;
    Text errorText = new Text(errorTextString);
    errorPane.add(errorText, 0, 2);

    JFXButton okButton = new JFXButton("Okay.");
    okButton.setButtonType(JFXButton.ButtonType.RAISED);
    okButton.setTextAlignment(TextAlignment.CENTER);
    errorPane.add(okButton, 0, 2);
  }

  public Parent display() {
    return errorPane;
  }
}
