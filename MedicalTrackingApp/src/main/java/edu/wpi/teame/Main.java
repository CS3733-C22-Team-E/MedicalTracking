package edu.wpi.teame;

public class Main {

  public static void main(String[] args) {
    boolean panMode = false;
    if (panMode) {
      Pannable.launch(Pannable.class, args);
    } else {
      App.launch(App.class, args);
    }
  }
}
