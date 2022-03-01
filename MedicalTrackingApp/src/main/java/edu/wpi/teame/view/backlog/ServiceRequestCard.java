package edu.wpi.teame.view.backlog;

import static edu.wpi.teame.model.enums.DataBaseObjectType.*;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teame.model.Location;
import edu.wpi.teame.model.enums.FloorType;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import edu.wpi.teame.model.enums.ServiceRequestStatus;
import edu.wpi.teame.model.serviceRequests.*;
import java.sql.SQLException;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ServiceRequestCard {

  // Styling
  private final int BORDERRADIUS = 10;

  // Details
  private final ServiceRequestBacklog backlog;
  private String patientName;
  private Location location;
  private final ServiceRequest sr;
  private final Color color;
  private Background nonHoverBG;
  private Background hoverBG;
  private boolean isDead = false;

  public ServiceRequestCard(ServiceRequest serviceRequest, ServiceRequestBacklog b) {
    System.out.println(serviceRequest);
    sr = serviceRequest;
    backlog = b;
    color = getServiceRequestColor();
    location = sr.getLocation();
  }

  public ServiceRequestCard(ServiceRequest serviceRequest, ServiceRequestBacklog b, boolean dead) {
    System.out.println(serviceRequest);
    sr = serviceRequest;
    backlog = b;
    color = getServiceRequestColor();
    location = sr.getLocation();
    isDead = true;
  }

  public HBox getCard(double width, double height) {
    // Setup grid
    HBox card = new HBox();
    if (!isDead) {
      card.setEffect(new DropShadow(5, color));
    }
    Stop[] stops = new Stop[] {new Stop(0, Color.WHITE), new Stop(1, color)};
    Stop[] stops2 = new Stop[] {new Stop(0, Color.LIGHTGRAY), new Stop(1, color)};
    LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    LinearGradient lg2 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops2);
    nonHoverBG = new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY));
    hoverBG = new Background(new BackgroundFill(lg2, CornerRadii.EMPTY, Insets.EMPTY));
    if (!isDead) {
      card.setBackground(nonHoverBG);
    } else {
      card.setBackground(
          new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    createBookend(card, 40);
    card.getChildren().add(getDoneCheckbox());
    createBookend(card, 80);

    GridPane titleAndDescription = new GridPane();
    titleAndDescription.setAlignment(Pos.CENTER);
    titleAndDescription.add(getTitleText(), 0, 0);
    titleAndDescription.add(getSeparatorH(), 0, 1);
    titleAndDescription.add(getDescriptionText(), 0, 2);
    titleAndDescription.setAlignment(Pos.CENTER_LEFT);
    card.getChildren().add(titleAndDescription);
    createSpacer(card);

    GridPane detailsGrid = new GridPane();
    detailsGrid.setAlignment(Pos.CENTER);
    detailsGrid.setHgap(20);
    detailsGrid.add(generateDetailText("Assignee: "), 2, 0);
    detailsGrid.add(getSeparatorH(), 2, 1);
    detailsGrid.add(generateDetailText("Location: "), 2, 2);
    detailsGrid.add(getSeparatorH(), 2, 3);
    detailsGrid.add(generateDetailText("Priority: "), 2, 4);
    detailsGrid.add(getSeparatorH(), 2, 5);
    detailsGrid.add(generateDetailText("Status: "), 2, 6);
    detailsGrid.add(getSeparatorH(), 2, 7);

    detailsGrid.add(generateDetailText(sr.getAssignee().getName()), 3, 0);
    detailsGrid.add(getSeparatorH(), 3, 1);
    detailsGrid.add(generateDetailText(location.getLongName()), 3, 2);
    detailsGrid.add(getSeparatorH(), 3, 3);
    detailsGrid.add(generatePriorityText(sr.getPriority().name()), 3, 4);
    detailsGrid.add(getSeparatorH(), 3, 5);
    detailsGrid.add(generateDetailText(sr.getStatus().name()), 3, 6);
    detailsGrid.add(getSeparatorH(), 3, 7);
    card.getChildren().add(detailsGrid);
    createBookend(card, 40);

    card.setAlignment(Pos.CENTER_RIGHT);
    card.setPrefSize(width, height);
    card.setFillHeight(false);
    if (!isDead) {
      setHoverStyling(card);
    }
    return card;
  }

  private HBox generatePriorityText(String text) {
    HBox pBox = new HBox();
    Text priorityText = new Text(text);
    priorityText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    if (sr.getPriority() == ServiceRequestPriority.Low
        || sr.getPriority() == ServiceRequestPriority.Normal) {
      priorityText.setFill(Color.BLACK);
    } else {
      priorityText.setFill(Color.WHITE);
    }
    priorityText.setTextAlignment(TextAlignment.RIGHT);
    pBox.getChildren().add(priorityText);
    pBox.setBackground(
        new Background(
            new BackgroundFill(sr.getPriority().getColor(), new CornerRadii(10), Insets.EMPTY)));
    pBox.setAlignment(Pos.CENTER);
    pBox.setMaxWidth(70);
    return pBox;
  }

  private Text generateDetailText(String text) {
    Text detailText = new Text(text);
    detailText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    detailText.setFill(Color.BLACK);
    detailText.setTextAlignment(TextAlignment.RIGHT);
    return detailText;
  }

  private HBox getTitleText() {
    HBox textBox = new HBox();
    Text titleText = new Text(sr.getDBType().shortName());
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    textBox.getChildren().add(titleText);
    Text srText = new Text(" Service Request");
    srText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    if (!isDead) {
      srText.setFill(color);
    }
    textBox.getChildren().add(srText);
    textBox.setAlignment(Pos.CENTER_LEFT);
    return textBox;
  }

  private JFXCheckBox getDoneCheckbox() {
    JFXCheckBox doneBox = new JFXCheckBox();
    doneBox.setCheckedColor(Color.LIGHTBLUE);
    doneBox.setCheckedColor(Color.LIGHTGRAY);
    doneBox.setPadding(new Insets(0, -40, 0, 0));
    doneBox.setScaleX(2);
    doneBox.setScaleY(2);
    doneBox.setOnMouseClicked(
        (event -> {
          try {
            deleteRequest(backlog);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }));
    Tooltip t = new Tooltip("Click to mark as done.");
    Tooltip.install(doneBox, t);
    ;
    if (isDead) {
      t.setText("Click to delete.");
      doneBox.setSelected(true);
    }
    Tooltip.install(doneBox, t);
    ;
    return doneBox;
  }

  private Text getDescriptionText() {
    String d = "";
    if (sr.getDBType() == FoodDeliverySR) {
      FoodDeliveryServiceRequest s = (FoodDeliveryServiceRequest) sr;
      d =
          "Deliver "
              + s.getFood()
              + " to patient \""
              + s.getPatient().getName()
              + "\" at the requested location.";
    } else if (sr.getDBType() == GiftAndFloralSR) {
      GiftAndFloralServiceRequest s = (GiftAndFloralServiceRequest) sr;
      d =
          "Fulfil a Gift/Floral Service Request to patient \""
              + s.getPatient().getName()
              + "\" at the requested location.";
    } else if (sr.getDBType() == LanguageInterpreterSR) {
      LanguageInterpreterServiceRequest s = (LanguageInterpreterServiceRequest) sr;
      d =
          "Get a Language Interpreter that speaks "
              + s.getLanguage().name()
              + " to patient \""
              + s.getPatient().getName()
              + "\" at the requested location.";
    } else if (sr.getDBType() == MedicalEquipmentSR) {
      MedicalEquipmentServiceRequest s = (MedicalEquipmentServiceRequest) sr;
      d = "Deliver equipment \"" + s.getEquipment().getName() + "\" to the requested location.";
    } else if (sr.getDBType() == MedicineDeliverySR) {
      MedicineDeliveryServiceRequest s = (MedicineDeliveryServiceRequest) sr;
      d =
          "Deliver medicine \""
              + s.getMedicineName()
              + "\" in quantity \""
              + s.getMedicineQuantity()
              + "\" to patient \""
              + s.getPatient().getName()
              + "\" at the requested location.";
    } else if (sr.getDBType() == InternalPatientTransferSR) {
      PatientTransportationServiceRequest s = (PatientTransportationServiceRequest) sr;
      d =
          "Transport patient \""
              + s.getPatient().getName()
              + "\" internally from location \""
              + s.getLocation().getShortName()
              + "\" to location \""
              + s.getDestination().getShortName()
              + "\".";
      if (s.getEquipment() != null) {
        d += "\nBring equipment: " + s.getEquipment().getName();
      }
    } else if (sr.getDBType() == ExternalPatientSR) {
      PatientTransportationServiceRequest s = (PatientTransportationServiceRequest) sr;
      d =
          "Transport patient \""
              + s.getPatient().getName()
              + "\" externally from location \""
              + s.getLocation().getShortName()
              + "\" to location \""
              + s.getDestination().getShortName()
              + "\".";
      if (s.getEquipment() != null) {
        d += "\nBring equipment: " + s.getEquipment().getName();
      }
    } else if (sr.getDBType() == ReligiousSR) {
      ReligiousServiceRequest s = (ReligiousServiceRequest) sr;
      d =
          "Get a religious official of religion \""
              + s.getReligion()
              + "\" to patient \""
              + s.getPatient().getName()
              + "\" at the requested location.";
    }

    if (!sr.getAdditionalInfo().equals("")) {
      d = sr.getAdditionalInfo();
    }
    if (d.equals("")) {
      d = sr.getDBType().getDescription();
    }

    Text descriptionText = new Text(d);
    descriptionText.setFont(Font.font("Arial", 16));
    descriptionText.setFill(Color.BLACK);
    descriptionText.setTextAlignment(TextAlignment.CENTER);
    return descriptionText;
  }

  private Separator getSeparatorH() {
    Separator titleSeparator = new Separator();
    titleSeparator.setScaleX(1);
    titleSeparator.setScaleY(1);
    titleSeparator.setHalignment(HPos.CENTER);
    return titleSeparator;
  }

  private void deleteRequest(ServiceRequestBacklog b) throws SQLException {
    if (!isDead) {
      b.killServiceRequest(this.sr);
    } else {
      b.removeServiceRequest(this.sr);
    }
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  } // TODO change this to SR patient field

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setFloor(FloorType floor) {
    this.location.setFloor(floor);
  }

  public void setStatus(ServiceRequestStatus status) {
    this.sr.setStatus(status);
  }

  private void setHoverStyling(HBox c) {
    c.setOnMouseEntered(e -> c.setBackground(hoverBG));

    c.setOnMouseExited(e -> c.setBackground(nonHoverBG));
  }

  public ServiceRequest getServiceRequest() {
    return this.sr;
  }

  private void createSpacer(HBox c) {
    final Region spacer = new Region();
    // Make it always grow or shrink according to the available space
    HBox.setHgrow(spacer, Priority.ALWAYS);
    c.getChildren().add(spacer);
  }

  private void createBookend(HBox c, double w) {
    final Region spacer = new Region();
    // Make it always grow or shrink according to the available space
    spacer.setPrefWidth(w);
    spacer.setMaxWidth(w);
    spacer.setMinWidth(w);
    HBox.setHgrow(spacer, Priority.SOMETIMES);
    c.getChildren().add(spacer);
  }

  private Color getServiceRequestColor() {
    return this.sr.getDBType().getColor();
  }
}
