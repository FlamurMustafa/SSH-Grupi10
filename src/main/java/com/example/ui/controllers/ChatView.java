package com.example.ui.controllers;

import com.example.ui.models.ChatUser;
import com.example.ui.statics.Token;
import com.example.ui.statics.UserRequests;
import com.example.ui.chat.PeerHandler;
import com.example.ui.chat.ServerThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import javafx.scene.control.Label;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatView implements Initializable {
    @FXML
    VBox vbox;

    @FXML
    VBox receiverVbox;

    @FXML
    VBox senderVbox;

    @FXML
    TextField m;

    @FXML
    TextField groupMemberTf;

    ServerThread serverThread;
    BufferedReader bufferedReader;
    int port;


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
            String usr = UserRequests.getUser();
            int p = Character.getNumericValue(usr.charAt(usr.length() - 1));
            p = p + 3050;
            System.out.println(p);
            serverThread = new ServerThread(String.valueOf(p));
            serverThread.start();

            res = call.execute();

            String resString = res.body().string();

            JSONArray scheduleArray = new JSONArray(resString);
            ObservableList<ChatUser> users = FXCollections.observableArrayList();
            for (int i = 0; i < scheduleArray.length(); i++) {
                JSONObject jsonObject = scheduleArray.getJSONObject(i);
                users.add(new ChatUser(jsonObject.getInt("userid"),
                        jsonObject.getString("username")));
                String s = users.get(i).getName() + users.get(i).getId();
                Label label = new Label(s);
                vbox.getChildren().add(label);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vboxClicked(MouseEvent mouseEvent) throws Exception {
        senderVbox.getChildren().clear();
        receiverVbox.getChildren().clear();

        String s = mouseEvent.getTarget().toString();
        String subS = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
        this.port = 3050 + Character.getNumericValue(subS.charAt(subS.length() - 1));
        String user = subS.substring(0, subS.length() - 1);
        updateListenToPeers(user);
    }

    public void updateListenToPeers(String username) throws Exception {

        Socket socket = null;
        String currentUser = UserRequests.getUser();
        currentUser = currentUser.substring(0,currentUser.length()-1);
        if(username.equalsIgnoreCase(currentUser))  {
            receiverVbox.getChildren().add(new Label("Nuk mund te selektoni veten"));
                    return;
        }
        try {
            socket = new Socket("localhost", this.port);
            String m = "You are now talking to "+username;
            receiverVbox.getChildren().add(new Label(m));
            new PeerHandler(socket, receiverVbox, senderVbox, username).start();
        } catch (Exception e) {
            if (socket == null) {
                receiverVbox.getChildren().add(new Label("Perdoruesi eshte offline"));
                System.out.println("Gabim!");
            }
            socket.close();
        }
    }

    public void sendButtonClicked(MouseEvent mouseEvent) {
        Label label = new Label(m.getText());
        receiverVbox.getChildren().add(new Label());
        senderVbox.getChildren().add(label);
        this.serverThread.send(m.getText());
        m.clear();
    }


}
