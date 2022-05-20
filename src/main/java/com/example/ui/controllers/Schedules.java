package com.example.ui.controllers;

import com.example.ui.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Schedules implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button createBtn;

    public void onCreateClicked(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/ui/views/add-appointment.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OkHttpClient client = new OkHttpClient();
        try {
            String token = Token.getToken();

            Request req = new Request.Builder()
                    .url("http://localhost:3000/class")
                    .header("Authorization", token)
                    .get()
                    .build();
            Call call = client.newCall(req);
            Response res = call.execute();
            System.out.println(res.body().string());
        }catch (Exception e){
            e.printStackTrace();
        }
        }
}
