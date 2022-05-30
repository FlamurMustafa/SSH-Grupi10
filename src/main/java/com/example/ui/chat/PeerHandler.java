package com.example.ui.chat;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerHandler extends Thread {
    private BufferedReader bufferedReader;
    private VBox vBox;
    private VBox senderVbox;
    private String sender;
    public PeerHandler(Socket socket, VBox vbox, VBox senderVbox, String sender) throws IOException {
        this.bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        this.vBox = vbox;
        this.senderVbox = senderVbox;
        this.sender = sender;
    }

    public PeerHandler(Socket socket) {
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {

            try {
                String content = bufferedReader.readLine();
                Platform.runLater(()->{
                    vBox.getChildren().add(new Label(this.sender+": "+content));
                    senderVbox.getChildren().add(new Label());
                });
            }catch(Exception e) {
                flag = false;
                super.interrupt();
            }
        }
    }
}