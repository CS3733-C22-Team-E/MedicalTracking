package edu.wpi.teame.view.style;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.view.controllers.AutoCompleteTextField;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class ColorScheme {
  public final int DefaultFontSize = 16;
  public final int HeaderFontSize = 28;
  public final int TitleFontSize = 72;

  private final Color background;
  private final Color headline;
  private final Color paragraph;
  private final Color buttonFill;
  private final Color buttonText;
  private final Color stroke;
  private final Color main;
  private final Color highlight;
  private final Color secondary;
  private final Color tertiary;

  public ColorScheme(
      Color background,
      Color headline,
      Color paragraph,
      Color buttonFill,
      Color buttonText,
      Color stroke,
      Color main,
      Color highlight,
      Color secondary,
      Color tertiary) {
    this.background = background;
    this.headline = headline;
    this.paragraph = paragraph;
    this.buttonFill = buttonFill;
    this.buttonText = buttonText;
    this.stroke = stroke;
    this.main = main;
    this.highlight = highlight;
    this.secondary = secondary;
    this.tertiary = tertiary;
  }

  public void setAutoCompleteTextBoxStyle(AutoCompleteTextField autoCompleteTextBox) {
    setTextFieldStyle(autoCompleteTextBox);
  }

  public void setButtonStyle(Button button) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(buttonFill));
    newStyle.append("-fx-font-size: ").append(DefaultFontSize).append("px; ");
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(buttonText));
    button.setStyle(newStyle.toString());
  }

  public void setCheckBoxStyle(CheckBox checkBox) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(paragraph));
    newStyle.append("-fx-font-size: 12px; ");
    checkBox.setStyle(newStyle.toString());
  }

  public void setComboBoxStyle(ComboBox comboBox) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondary));
    newStyle.append("-fx-background-radius: 20px; ");
    comboBox.setStyle(newStyle.toString());
  }

  public void setDatePickerStyle(DatePicker datePicker) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondary));
    datePicker.setStyle(newStyle.toString());
  }

  public void setTitleStyle(Label titleLabel) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-font-size: ").append(TitleFontSize).append("px; ");
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(headline));
    titleLabel.setStyle(newStyle.toString());
  }

  public void setHeaderStyle(Label headerLabel) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(headline));
    newStyle.append("-fx-font-size: ").append(HeaderFontSize).append("px; ");
    newStyle.append("-fx-margin-bottom: 20px; ");
    headerLabel.setStyle(newStyle.toString());
  }

  public void setLabelStyle(Label label) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-text-fill: ").append(getColorAsStyleString(paragraph));
    newStyle.append("-fx-font-size: ").append(DefaultFontSize).append("px; ");
    label.setStyle(newStyle.toString());
  }

  public void setLineStyle(Line line) {
    line.setStroke(highlight);
  }

  public void setTextFieldStyle(TextField textField) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondary));
    newStyle.append("-fx-font-size: ").append(DefaultFontSize).append("px; ");
    textField.setStyle(newStyle.toString());
  }

  public void setTextAreaStyle(TextArea textArea) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondary));
    textArea.setStyle(newStyle.toString());
  }

  // <editor-fold desc="JavaFX Panes">

  public void setPaneStyle(Pane pane, boolean useStandardBackground) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    newStyle.append("-fx-padding: 10px; ");
    pane.setStyle(newStyle.toString());
  }

  public void setListViewStyle(ListView listView) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(secondary));
    newStyle.append("-fx-background-radius: 20px; ");
    listView.setStyle(newStyle.toString());

    listView.setCellFactory(
        lv ->
            new ListCell<ISQLSerializable>() {
              @Override
              protected void updateItem(ISQLSerializable dbObject, boolean empty) {
                super.updateItem(dbObject, empty);
                StringBuilder listTabStyle = new StringBuilder();

                if (empty) {
                  listTabStyle.append("-fx-background-radius: 20px; -fx-background-color: ");
                  listTabStyle.append(getColorAsStyleString(secondary));
                  setStyle(listTabStyle.toString());
                  setText("");
                  return;
                }

                if (dbObject.getIsDeleted()) {
                  listTabStyle.append("-fx-background-radius: 20px; -fx-background-color: ");
                  listTabStyle.append(getColorAsStyleString(tertiary));
                }

                listTabStyle.append("-fx-text-fill: ");
                this.setTextFill(paragraph);
                setText(dbObject.toString());
              }
            });
  }

  public void setScrollPaneStyle(ScrollPane scrollPane) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(background));
    scrollPane.setStyle(newStyle.toString());
  }

  public void setTabPaneStyle(TabPane tabPane) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(main));
    tabPane.setStyle(newStyle.toString());
    tabPane.setBackground(
        new Background(new BackgroundFill(main, CornerRadii.EMPTY, Insets.EMPTY)));
    for (Tab tab : tabPane.getTabs()) {
      setTabStyle(tab);
    }
  }

  public void setTabStyle(Tab tab) {
    StringBuilder newStyle = new StringBuilder(getDefaultStyleParams());
    newStyle.append("-fx-background-color: ").append(getColorAsStyleString(highlight));
    newStyle.append("-fx-background-radius: 10px; ").append("-fx-font-size: 16px; ");
    tab.setStyle(newStyle.toString());
  }

  // </editor-fold>

  private String getDefaultStyleParams() {
    StringBuilder defaultStyle = new StringBuilder();
    defaultStyle.append("-fx-font-weight: bold; -fx-font-family: Roboto; ");
    return defaultStyle.toString();
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

  public Paint getHighlightColor() {
    return highlight;
  }

  public Paint getParagraphColor() {
    return paragraph;
  }

  public Paint getBackgroundColor() {
    return background;
  }

  public Paint getMainColor() {
    return main;
  }
}
