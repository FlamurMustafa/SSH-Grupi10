package com.example.ui.controllers;

import com.example.ui.Schedule;
import com.example.ui.Token;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ScheduleController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField usernameTf;

    @FXML
    private TableView<Schedule> tableView;

    @FXML
    private TableColumn<Schedule, Integer> scheduleField;

    @FXML
    private TableColumn<Schedule, Integer> roomField;

    @FXML
    private TableColumn<Schedule, String> startTimeField;

    @FXML
    private TableColumn<Schedule, String> endTimeField;

    @FXML
    private TableColumn<Schedule, String> classField;

    private String token;

    private OkHttpClient client;

    public void onCreateClicked(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/ui/views/add-appointment.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = new OkHttpClient();
        try {
            token = Token.getToken();

            getUserCall(client, token);

            getSchedules(client, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSchedules(OkHttpClient client, String token) throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:3000/class")
                .header("Authorization", token)
                .get()
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String scheduleString = response.body().string();

        JSONArray scheduleArray = new JSONArray(scheduleString);
        ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();

        for (int i = 0; i < scheduleArray.length(); i++) {
            JSONObject scheduleObject = scheduleArray.getJSONObject(i);
            scheduleList.add(new Schedule(scheduleObject.getInt("scheduleid"),
                    scheduleObject.getInt("room_id"),
                    scheduleObject.getString("start_time"),
                    scheduleObject.getString("end_time"),
                    scheduleObject.getString("class_name")));
        }

        scheduleField.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("scheduleId"));
        roomField.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("roomId"));
        startTimeField.setCellValueFactory(new PropertyValueFactory<Schedule, String>("startTime"));
        endTimeField.setCellValueFactory(new PropertyValueFactory<Schedule, String>("endTime"));
        classField.setCellValueFactory(new PropertyValueFactory<Schedule, String>("classId"));

        tableView.setItems(scheduleList);
    }

    private void getUserCall(OkHttpClient client, String token) throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:3000/user")
                .header("Authorization", token)
                .get()
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String userString = response.body().string();

        JSONObject user = new JSONObject(userString);

        usernameTf.setText(user.getString("username"));
        emailTf.setText(user.getString("email"));
        nameTf.setText(user.getString("name"));
    }

    public void onDeleteClicked(ActionEvent action) {
        Schedule schedule = tableView.getSelectionModel().getSelectedItems().get(0);
        try {
            Request request = new Request.Builder()
                    .url("http://localhost:3000/class?scheduleid=" + schedule.getScheduleId())
                    .header("Authorization", token)
                    .delete()
                    .build();

            Call call = client.newCall(request);
            call.execute();

            getSchedules(client, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
