package com.example.ui.controllers;

import javafx.fxml.FXML;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;

public class Schedules {

    @FXML
    private TableColumn<?, ?> classID;

    @FXML
    private TableColumn<?, ?> endTime;

    @FXML
    private TableColumn<?, ?> roomID;

    @FXML
    private TableColumn<?, ?> scheduleID;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<?, ?> startTime;

    @FXML
    private TableView<?> tableView;

}
