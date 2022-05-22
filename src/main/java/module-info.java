module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires okhttp3;
    requires annotations;
    requires java.net.http;
    requires org.json;
    requires com.google.gson;


    opens com.example.ui to javafx.fxml;
    opens com.example.ui.controllers to javafx.fxml;
    exports com.example.ui;
}