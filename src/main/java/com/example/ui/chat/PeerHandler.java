package com.example.ui.chat;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerHandler extends Thread {
    private BufferedReader bufferedReader;
    private VBox vBox;
    public PeerHandler(Socket socket, VBox vbox) throws IOException {
        this.bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        this.vBox = vbox;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {

            try {
                String content = bufferedReader.readLine();
                Platform.runLater(()->{
                    vBox.getChildren().add(new Label(content));
                });
                //Label label = new Label(content);
                //vBox.getChildren().add(label);
            }catch(Exception e) {
                flag = false;
                super.interrupt();
            }
        }
    }
}