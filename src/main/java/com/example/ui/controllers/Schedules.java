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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class Schedules implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button createBtn;

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
    private TableColumn<Schedule, Date> startTimeField;

    @FXML
    private TableColumn<Schedule, Date> endTimeField;

    @FXML
    private TableColumn<Schedule, Integer> classField;

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

            Request userReq = new Request.Builder()
                    .url("http://localhost:3000/user")
                    .header("Authorization", token)
                    .get()
                    .build();

            Call userCall = client.newCall(userReq);
            Response userRes = userCall.execute();

            String strResponse = userRes.body().string();

            JSONObject obj = new JSONObject(strResponse);

            usernameTf.setText(obj.getString("username"));
            emailTf.setText(obj.getString("email"));
            nameTf.setText(obj.getString("name"));

            Request req = new Request.Builder()
                    .url("http://localhost:3000/class")
                    .header("Authorization", token)
                    .get()
                    .build();

            Call call = client.newCall(req);
            Response res = call.execute();
            System.out.println(res);


            scheduleField.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("scheduleId"));
            roomField.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("roomId"));
            startTimeField.setCellValueFactory(new PropertyValueFactory<Schedule, Date>("startTime"));
            endTimeField.setCellValueFactory(new PropertyValueFactory<Schedule, Date>("endTime"));
            classField.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("classId"));

            tableView.setItems(getSchedules());

        }catch (Exception e){
            e.printStackTrace();
        }
        }

        public ObservableList<Schedule> getSchedules(){
        ObservableList<Schedule> schedules = FXCollections.observableArrayList();
        schedules.add(new Schedule(1, 253, new Date(2000, 11, 21), new Date(2012, 10, 21), 32));
        return schedules;
        }
}
