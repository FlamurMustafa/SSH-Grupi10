package com.example.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Iterator;

public class Signup implements Iterable{
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
        String nameTf = this.nameTf.getText();
        String mnumberTf = this.mnumberTf.getText();
        String emailTf = this.emailTf.getText();
        String passwordTf = this.passwordTf.getText();

        if (nameTf.isEmpty() || passwordTf.isEmpty() || mnumberTf.isEmpty() || mnumberTf.isEmpty()) {
            badSignup.setText("Pleas fill the text field");
        } else {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n "
                    + "\"nameTf\":\"" + nameTf + "\","
                    + "\n\"password\":\"" + passwordTf +
                    "\n\"email\":\"" + emailTf+
                    "\n\"mnumberTf\":\"" + mnumberTf+"\"\n}");

            Request request = new Request.Builder()
                    .url("http://localhost:3000/user/signup")
                    .method("POST", body)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() == 201) {
                goToLogin();
            } else if (response.code() == 409) {
                badSignup.setText("The user alredy exists");
            }
        }
    }
    private void goToLogin() throws IOException{
        Stage stage=(Stage) link.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ui/views/schedules.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void onLinkClicked() throws IOException{
        goToLogin();
    }
}
