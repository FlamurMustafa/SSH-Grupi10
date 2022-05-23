package com.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {
        if(Token.getToken()!=null){
            fxmlLoader = new FXMLLoader(Main.class.getResource("views/Log-in.fxml"));

        }else{
            fxmlLoader = new FXMLLoader(Main.class.getResource("views/schedules.fxml"));
        }

        Scene scene = new Scene(fxmlLoader.load(), 814, 495);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
