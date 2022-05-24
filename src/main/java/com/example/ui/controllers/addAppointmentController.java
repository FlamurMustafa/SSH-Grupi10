package com.example.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.swing.JOptionPane.showMessageDialog;

public class addAppointmentController {
    //DatePicker
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField start_time,end_time,room_name,class_name;
    //Date in hour and minutes
    //@FXML
    //private TextField minutesTf,hourTf;
    //Date in hour and minutes Integer
    //int hours,minutes;
    @FXML
    private Label popUp;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void handlebuttonAdd(ActionEvent actionEvent) throws IOException, InterruptedException {
        LocalDate myDate = datePicker.getValue();
        String starttime = start_time.getText();
        String endtime = end_time.getText();
        String roomname = room_name.getText();
        String classname = class_name.getText();

        if (myDate.toString().isBlank() || starttime.isBlank() || endtime.isBlank() || roomname.isBlank() || classname.isBlank()) {
            showMessageDialog(null, "Please fill the text field!");
        }
        else if (!starttime.matches("(0[89]|1[0-9]|20)") && !endtime.matches("(0[89]|1[0-9]|20)")) {
            showMessageDialog(null, "The start and end time should be between 08 am to 08 pm(08-20)!");
        }
        else if (!roomname.matches("[a-zA-Z0-9]+") && !classname.matches("[a-zA-Z0-9]+")) {
            showMessageDialog(null, "The room and class name should contain only letters and numbers!");
        }
        else {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("datePicker", String.valueOf(datePicker.getValue()))
                        .add("room_name", room_name.getText())
                        .add("start_time", start_time.getText())
                        .add("end_time", end_time.getText())
                        .add("class_name", class_name.getText())
                        .build();
                Request req = new Request.Builder()
                        .url("http://localhost:3000/class/post")
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
                    popUp.setText("There was a mistake adding your course credentials.");
                }
            } catch (Exception e) {

                popUp.setText("An error has occurred");
                e.printStackTrace();
            }
        }
    }

    public void handlebuttonCancel(ActionEvent actionEvent) {
        try{
            datePicker.getEditor().clear();
            room_name.clear();
            start_time.clear();
            end_time.clear();
            class_name.clear();
            System.out.println("All fields have been cleared and canceled.");
        }
        catch(Exception e)
        {
            popUp.setText("An error has occurred");
            e.printStackTrace();
        }
    }
}
