package com.example.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;

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
    private TextField usernameTf;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField passwordTf;

    @FXML
    private Hyperlink link;

    @FXML
    private CheckBox professorCB;

    @NotNull

    @Override
    public Iterator iterator() {
        return null;
    }

    public void onSignupClicked(javafx.event.ActionEvent actionEvent) throws IOException, InterruptedException {
        String name = this.nameTf.getText();
        String username = this.usernameTf.getText();
        String email = this.emailTf.getText();
        String password = this.passwordTf.getText();
        String emailRegexp= "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        String passwordRegexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}.*$";
        String isProfessor = "0";
        if(professorCB.isSelected())
            isProfessor = "1";

        Pattern pattern = Pattern.compile(emailRegexp);
        Pattern pattern2 = Pattern.compile(passwordRegexp);

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || username.isEmpty()) {
            badSignup.setText("Please fill all the text fields");
        }
//        else if(!pattern2.matcher(password).matches()) {
//            badSignup.setText("Please write vaild password ");
//        }
        else if(!pattern.matcher(email).matches()){
           badSignup.setText("Please write vaild email ");
        }

        else {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("username", username)
                        .add("email", email)
                        .add("password", password)
                        .add("role_id", isProfessor)
                        .build();

                Request req = new Request.Builder()
                        .url("http://localhost:3000/user/signup")
                        .post(formBody)
                        .build();

                Call call = client.newCall(req);
                Response res = call.execute();
                if (res.isSuccessful()) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/ui/views/Log-in.fxml"));
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