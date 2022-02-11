package edu.wpi.teame.view.controllers.serviceRequests;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.fxml.Initializable;

public abstract class ServiceRequestController implements Initializable {
  static final ArrayList<String> createTime() {
    ArrayList<String> list = new ArrayList<String>();
    for (int i = 0; i < 96; i++) {
      DecimalFormat decimalFormat = new DecimalFormat("00");
      String time = String.valueOf(decimalFormat.format(i / 4));
      time += ":" + String.valueOf(decimalFormat.format(15 * (i % 4)));
      list.add(time);
    }
    return list;
  }
}
