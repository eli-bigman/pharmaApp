package com.example.pharmaapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Switch {


    private static Stage stage;
    private static Scene scene;

    public static void switchToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Switch.class.getResource("/com/example/pharmaapp/dashboard-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     // Switches to the Inventory screen
     *
     * @param event A click event
     * @throws IOException Exception thrown if file does not exist
     *
     *
     */
    @FXML
    public static void switchToInventory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Switch.class.getResource("/com/example/pharmaapp/inventory-view.fxml")));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     // Switches to the reports screen
     *
     * @param event A click event
     * @throws IOException Exception thrown if file does not exist
     *
     *
     */
    public static void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Switch.class.getResource("/com/example/pharmaapp/reports-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     // Switches to the sales screen
     *
     * @param event A click event
     * @throws IOException Exception thrown if file does not exist
     *
     *
     */
    public static void switchToSales(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Switch.class.getResource("/com/example/pharmaapp/sales-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
