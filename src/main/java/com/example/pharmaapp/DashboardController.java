package com.example.pharmaapp;

// PharmacyController.java
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent parent;

    //Adding drug using the add drug button
    @FXML
    private TextField drugNameField;

    @FXML
    private TextField unitPriceField;

    @FXML
    private TextField numOfUnitsField;

    @FXML
    private JFXButton addButton;

    @FXML
    private TextField descriptionField;


    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pharmaapp/dashboard-view.fxml")));

        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pharmaapp/inventory-view.fxml")));
        

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pharmaapp/reports-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToNotification(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pharmaapp/notification-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
