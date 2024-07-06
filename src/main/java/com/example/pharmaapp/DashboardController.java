package com.example.pharmaapp;

// PharmacyController.java
import com.example.pharmaapp.database.sql.dbConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
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
    private Text revenueLabel;
    @FXML
    private Text totalDrugsInStockLabel;
    @FXML
    private Label totalMedicineSoldLabel;

    @FXML
    private Label totalNumberOfDrugs1;
    @FXML
    private Label totalNumberOfDrugs2;

    private dbConnection dbConnection;

    public DashboardController() {
        dbConnection = new dbConnection();
    }

    @FXML
    public void initialize() {
        updateDashboard();
    }

    public void updateDashboard() {
        try (Connection connection = dbConnection.getConnection()) {
            double revenue = getTotalRevenue(connection);
            int totalDrugsInStock = getTotalDrugsInStock(connection);
            int totalMedicineSold = getTotalMedicineSold(connection);

            revenueLabel.setText("GHC " + String.format("%.2f", revenue));
            totalDrugsInStockLabel.setText(String.valueOf(totalDrugsInStock));
            totalMedicineSoldLabel.setText(String.valueOf(totalMedicineSold));

            totalNumberOfDrugs1.setText(String.valueOf(totalDrugsInStock));
            totalNumberOfDrugs2.setText(String.valueOf(totalDrugsInStock));

        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    private double getTotalRevenue(Connection connection) throws SQLException {
        String query = "SELECT SUM(price_sold) AS total_revenue FROM purchase";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getDouble("total_revenue");
            }
        }
    }

    private int getTotalDrugsInStock(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) AS total_medicine_in_stock FROM drugs WHERE available = 'yes'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("total_medicine_in_stock");
            }
        }
    }

    private int getTotalMedicineSold(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) AS total_medicine_sold FROM purchase";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("total_medicine_sold");
            }
        }
    }


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


    public void switchToSales(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pharmaapp/sales-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
