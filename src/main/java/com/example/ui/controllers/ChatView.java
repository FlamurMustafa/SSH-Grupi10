package com.example.ui.controllers;

import com.example.ui.ChatUser;
import com.example.ui.Schedule;
import com.example.ui.Token;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import javafx.scene.control.Label;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatView implements Initializable {
    @FXML
    VBox usersVbox;

    @FXML
    GridPane gridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OkHttpClient client = new OkHttpClient();
        String token = Token.getToken();
        Request req = new Request.Builder()
                .url("http://localhost:3000/user/others")
                .header("Authorization", token)
                .get()
                .build();

        Call call = client.newCall(req);
        Response res = null;
        try {
            res = call.execute();
            String resString = res.body().string();

            JSONArray scheduleArray = new JSONArray(resString);
            ObservableList<ChatUser>  users= FXCollections.observableArrayList();
            for(int i=0; i<scheduleArray.length();i++){
                JSONObject jsonObject = scheduleArray.getJSONObject(i);
                users.add(new ChatUser( jsonObject.getInt("userid"),
                        jsonObject.getString("username")));
                HBox hBox = new HBox();
                Label label = new Label(users.get(i).getName());
                Label label1 = new Label(String.valueOf(users.get(i).getId()));
                hBox.getChildren().addAll(label, label1);
                gridPane.add(hBox, 0,i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void getUsers() {

    }

    public void GridClicked(MouseEvent mouseEvent) {
        ObservableList<Node> childrens = gridPane.getChildren();
        Node source = (Node)mouseEvent.getSource() ;


        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        HBox i =(HBox) childrens.get(1);
        ObservableList<Node> hboxChildren = i.getChildren();

        String text = ((Label) hboxChildren.get(0)).getText();
        System.out.println(text);




    }
}
