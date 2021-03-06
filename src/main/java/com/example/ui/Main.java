package com.example.ui;

import com.example.ui.statics.Token;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {
        if(Token.getToken()!=null){
            fxmlLoader = new FXMLLoader(Main.class.getResource("views/schedules.fxml"));
        }else{
            fxmlLoader = new FXMLLoader(Main.class.getResource("views/Log-in.fxml"));
        }

        Scene scene = new Scene(fxmlLoader.load(), 814, 495);
        stage.setTitle("Schedule Management");
        stage.setScene(scene);
        stage.show();
    }
}
