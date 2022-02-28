package edu.wpi.teame.view.controllers.serviceRequests;

import edu.wpi.teame.view.style.IStyleable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.fxml.Initializable;

public abstract class ServiceRequestController implements Initializable, IStyleable {
  static ArrayList<String> createTime() {
    ArrayList<String> list = new ArrayList<String>();
    for (int i = 0; i < 96; i++) {
      DecimalFormat decimalFormat = new DecimalFormat("00");
      String time = decimalFormat.format(i / 4);
      time += ":" + decimalFormat.format(15 * (i % 4));
      list.add(time);
    }
    return list;
  }
}
