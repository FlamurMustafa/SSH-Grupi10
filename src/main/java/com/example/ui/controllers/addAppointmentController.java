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

import static javax.swing.JOptionPane.showMessageDialog;

public class addAppointmentController {
    //DatePicker
    @FXML
    private DatePicker datePicker;

    //Date in hour and minutes
    @FXML
    private TextField minutesTf,hourTf;

    //Date in hour and minutes Integer
    int hours,minutes;
    @FXML
    private Label popUp;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label badRequest;


    public void handlebuttonAdd(ActionEvent actionEvent) throws IOException, InterruptedException {
        /*try{
            String hour= hourTf.getText();
            String minute = minutesTf.getText();
            if(!hour.isBlank() || !minute.isBlank()){
                if (!hour.matches("(0[89]|1[0-9]|20)")) {
                    showMessageDialog(null,"The hour should be between 08 am to 08 pm(08-20)!");
                }
                else if (!minute.matches("(0[0-9]|[1-5][0-9])")) {
                    showMessageDialog(null,"Minutes should be betwen 00 and 59!");
                }
                else {
                    hours = Integer.parseInt(hour);
                    minutes = Integer.parseInt(minute);
                    System.out.println("The course appointment has been scheduled at "+hours+":"+minutes+".");
                    showMessageDialog(null, "Add: Successful.");
                }
            }
            else {
                showMessageDialog(null, "All the fields must be filled!");
            }
        }
        catch(Exception e)
        {
            popUp.setText("An error occurred");
            e.printStackTrace();
        }*/
        try{
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
            }
            else if (res.code()==400 ||res.code()==404) {
                badRequest.setText("There was a mistake adding your course credentials.");
                 }
            }
        catch (Exception e){

                popUp.setText("An error occurred");
                e.printStackTrace();
            }
        }

    public void handlebuttonCancel(ActionEvent actionEvent) {
        try{
            datePicker.getEditor().clear();
            hourTf.clear();
            minutesTf.clear();

            System.out.println("All fields have been deleted.");
        }
        catch(Exception e)
        {
            popUp.setText("An error occurred");
            e.printStackTrace();
        }
    }
}
