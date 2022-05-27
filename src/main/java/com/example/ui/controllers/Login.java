package com.example.ui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Login {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label popUp;

    @FXML
    private Button loginBtn;

    @FXML
    private Label badRequest;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField passwordTf;

    @FXML
    public void onLinkClicked(ActionEvent action) throws IOException, InterruptedException{

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/ui/views/sign-up.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginClicked(ActionEvent action) throws IOException, InterruptedException {

        if (emailTf.getText().isEmpty() || passwordTf.getText().isEmpty()) {
            badRequest.setText("Please fill the field");
        } else {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", emailTf.getText())
                        .add("password", passwordTf.getText())
                        .build();

                Request req = new Request.Builder()
                        .url("http://localhost:3000/user/login")
                        .post(formBody)
                        .build();
                Call call = client.newCall(req);
                Response res = call.execute();
                if (res.isSuccessful()) {
//
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/ui/views/schedules.fxml"));
                    Parent root = loader.load();
                    stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if (res.code() == 400 || res.code() == 404) {
                    badRequest.setText("There was a mistake in your credentials");
                }
            } catch (Exception e) {
                emailTf.clear();
                passwordTf.clear();
                popUp.setText("An error occurred");
                e.printStackTrace();
            }

        }
    }
}
