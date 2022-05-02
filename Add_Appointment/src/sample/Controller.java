package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.awt.event.ActionEvent;
import static javax.swing.JOptionPane.showMessageDialog;

public class Controller {
   @FXML
    private Label cAppointment;
    @FXML
    private Label sCAppointment;
    @FXML
    private Label sDate;
    @FXML
    private Label sTime;
    @FXML
    private Label hr;
    @FXML
    private Label min;

    //Buttons
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;

    //TextFields
    @FXML
    private TextField hourField;
    @FXML
    private TextField timeField;


    @FXML
    private void onAddButtonClick(ActionEvent event){
        try{
            if (!hourField.getText().isBlank() && !timeField.getText().isBlank()) {
                showMessageDialog(null, "Add: Successful.");
            }
            else {
                showMessageDialog(null, "All the fields must be filled!");
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        try {
            //Go to the main/dashboard page
            //Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            //primaryStage.setTitle("Dashboard");
            //primaryStage.setScene(new Scene(root, 720, 570));
            //primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
