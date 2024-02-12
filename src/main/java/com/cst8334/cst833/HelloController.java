package com.cst8334.cst833;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/*
   K: This is the controller that will be handling user interactions
      So when an event happens, a method here will be used to handle the event

      For now a button is displayed in the GUI and when clicked, onHelloButtonClick() is invoked to set welcome text
      Feel free to delete the button when working on the UI
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Just a test to make sure GUI works");
    }


}