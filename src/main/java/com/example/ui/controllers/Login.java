package com.example.ui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.*;

public class Login {
    private Stage stage;
    private OkHttpClient client;
    @FXML
    private Label popUp;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField passwordTf;

    @FXML
    private void onLoginClicked(ActionEvent action){
       client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", emailTf.getText())
                .add("password", passwordTf.getText())
                .build();

       Request request = new Request.Builder()
               .url("http://localhost:3000/user/login")
               .post(formBody)
               .build();
       Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Error");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    System.out.println(response);
                }
            }
        });
    }

}
