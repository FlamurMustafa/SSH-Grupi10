package com.example.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField usernameField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField passwordField;

    private void loginButtonOnAction(ActionEvent event){
        if(usernameField.getText().isBlank()==false && passwordField.getText().isBlank()==false){

        }else{
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
