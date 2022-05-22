package com.example.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import static javax.swing.JOptionPane.showMessageDialog;

public class Controller {
    //DatePicker
    @FXML
    private DatePicker datePicker;

    //Date in hour and minutes
    @FXML
    private TextField minutesTf,hourTf;

    //Buttons
    @FXML
    private Button cancelButton,addButton;

    //Hour
    int hours,minutes;

    @FXML private Label label;
    @FXML private Button button;

    @FXML
    private void onAddButtonClick(ActionEvent event){

    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {

    }

}
