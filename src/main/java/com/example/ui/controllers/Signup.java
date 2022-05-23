package com.example.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Signup implements Iterable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label popUp;

    @FXML
    private Button signupBtn;

    @FXML
    private Label badSignup;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField mnumberTf;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField passwordTf;

    @FXML
    private Hyperlink link;


    @NotNull


    @Override
    public Iterator iterator() {
        return null;
    }

    public void onSignupClicked(javafx.event.ActionEvent actionEvent) throws IOException, InterruptedException {
        String name = this.nameTf.getText();
        String mnumber = this.mnumberTf.getText();
        String email = this.emailTf.getText();
        String password = this.passwordTf.getText();

        if (name.isEmpty() || password.isEmpty() || mnumber.isEmpty() || mnumber.isEmpty()) {
            badSignup.setText("Pleas fill the text field");
        } else {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("mobileNumber", mnumber)
                        .add("email", email)
                        .add("password", password)
                        .build();

                Request req = new Request.Builder()
                        .url("http://localhost:3000/user/signup")
                        .post(formBody)
                        .build();

                Call call = client.newCall(req);
                Response res = call.execute();
                if (res.isSuccessful()) {
                    String token = res.body().string();
                    File yourFile = new File("src/main/resources/files/token.txt");
                    yourFile.createNewFile();
                    FileOutputStream fl = new FileOutputStream(yourFile, true);
                    fl.write(token.getBytes(StandardCharsets.UTF_8));
                    fl.close();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/ui/views/schedules.fxml"));
                    Parent root = loader.load();
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if (res.code() == 400 || res.code() == 404) {
                    badSignup.setText("Please write correct your credentials");
                }
            } catch (Exception e) {
                badSignup.setText("An error occurred");
                e.printStackTrace();
            }
        }
    }

    public void onLinkClicked(javafx.event.ActionEvent actionEvent) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/ui/views/Log-in.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
//            OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(mediaType, "{\n "
//                    + "\"nameTf\":\"" + nameTf + "\","
//                    + "\n\"password\":\"" + passwordTf +
//                    "\n\"email\":\"" + emailTf+
//                    "\n\"mnumberTf\":\"" + mnumberTf+"\"\n}");
//
//            Request request = new Request.Builder()
//                    .url("http://localhost:3000/user/signup")
//                    .method("POST", body)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//
//            if (response.code() == 201) {
//                goToLogin();
//            } else if (response.code() == 409) {
//                badSignup.setText("The user alredy exists");
//            }
//        }
//    }
//    private void goToLogin() throws IOException{
//        Stage stage=(Stage) link.getScene().getWindow();
//        stage.close();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/example/ui/views/schedules.fxml"));
//        Parent root = loader.load();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public void onLinkClicked() throws IOException{
//        goToLogin();
//    }

