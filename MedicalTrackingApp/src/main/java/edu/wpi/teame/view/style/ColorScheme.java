package edu.wpi.teame.view.style;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class ColorScheme {
  private final Color secondaryBackground;
  private final Color background;
  private final Color foreground;
  private final Color textColor;

  public ColorScheme(
      Color background, Color secondaryBackground, Color foreground, Color textColor) {
    this.secondaryBackground = secondaryBackground;
    this.background = background;
    this.foreground = foreground;
    this.textColor = textColor;
  }

  public void setAutoCompleteTextBoxStyle(AutoCompleteTextField autoCompleteTextBox) {
    setTextFieldStyle(autoCompleteTextBox);
  }

  public void setButtonStyle(Button button) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(foreground));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 18px; ");
    button.setStyle(newStyle.toString());
  }

  public void setCheckBoxStyle(CheckBox checkBox) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 12px; ");
    checkBox.setStyle(newStyle.toString());
  }

  public void setComboBoxStyle(ComboBox comboBox) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(foreground));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    comboBox.setStyle(newStyle.toString());
  }

  public void setDatePickerStyle(DatePicker datePicker) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(foreground));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    datePicker.setStyle(newStyle.toString());
  }

  public void setTitleStyle(Label titleLabel) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 72px; ");
    titleLabel.setStyle(newStyle.toString());
  }

  public void setHeaderStyle(Label headerLabel) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 28px; ");
    newStyle.append("-fx-padding-bottom: 20px; ");
    headerLabel.setStyle(newStyle.toString());
  }

  public void setLabelStyle(Label label) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 18px; ");
    label.setStyle(newStyle.toString());
  }

  public void setLineStyle(Line line) {
    line.setStroke(foreground);
  }

  public void setTextFieldStyle(TextField textField) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondaryBackground));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 18px; ");
    textField.setStyle(newStyle.toString());
  }

  public void setTextAreaStyle(TextArea textArea) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondaryBackground));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(textColor));
    newStyle.append("-fx-font-weight: bold; -fx-font-size: 18px; ");
    textArea.setStyle(newStyle.toString());
  }

  // <editor-fold desc="JavaFX Panes">

  public void setPaneStyle(Pane pane, boolean useStandardBackground) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ");

    if (useStandardBackground) {
      newStyle.append(getColorAsStyleString(background));
    } else {
      newStyle.append(getColorAsStyleString(secondaryBackground));
    }
    pane.setStyle(newStyle.toString());
  }

  public void setListViewStyle(ListView listView) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondaryBackground));
    listView.setStyle(newStyle.toString());

    listView.setCellFactory(
        lv ->
            new ListCell<ISQLSerializable>() {
              @Override
              protected void updateItem(ISQLSerializable dbObject, boolean empty) {
                super.updateItem(dbObject, empty);
                if (empty) {
                  setStyle("-fx-background-color: " + getColorAsStyleString(secondaryBackground));
                  setText("");
                  return;
                }

                if (dbObject.getIsDeleted()) {
                  setStyle("-fx-background-color: " + getColorAsStyleString(foreground));
                }
                setText(dbObject.toString());
              }
            });
  }

  public void setScrollPaneStyle(ScrollPane scrollPane) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    scrollPane.setStyle(newStyle.toString());
  }

  public void setTabPaneStyle(TabPane tabPane) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    tabPane.setStyle(newStyle.toString());
  }

  public void setTabStyle(Tab tab) {
    StringBuilder newStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(foreground));
    tab.setStyle(newStyle.toString());

    StringBuilder contentStyle = new StringBuilder();
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    tab.getContent().setStyle(contentStyle.toString());
  }

  // </editor-fold>

  public Paint getForegroundColor() {
    return foreground;
  }

  public Paint getTextColor() {
    return textColor;
  }

  private String getColorAsStyleString(Color color) {
    return "rgb("
        + color.getRed() * 255
        + ", "
        + color.getGreen() * 255
        + ", "
        + color.getBlue() * 255
        + "); ";
  }
}
