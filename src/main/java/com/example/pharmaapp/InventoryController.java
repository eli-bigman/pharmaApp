package com.example.pharmaapp;


import com.example.pharmaapp.database.dataStructures.Drug;
import com.example.pharmaapp.database.sql.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private TableView<Drug> drugsTable;
    @FXML
    private TableColumn<Drug, Integer> drugIDColumn;
    @FXML
    private TableColumn<Drug, String> drugNameColumn;
    @FXML
    private TableColumn<Drug, Double> unitPriceColumn;
    @FXML
    private TableColumn<Drug, Integer> numOfUnitsColumn;
    @FXML
    private TableColumn<Drug, String> drugSuppIDColumn;
    @FXML
    private TableColumn<Drug, String> descriptionColumn;

    @FXML
    private TextField drugNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField numOfUnitsField;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField supplierField;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private TextField searchField;


    private ObservableList<Drug> drugList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize columns
        drugIDColumn.setCellValueFactory(new PropertyValueFactory<>("drugID"));
        drugNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        numOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfUnits"));
        drugSuppIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data from database
        loadDrugData();
    }

    private void loadDrugData() {
        drugList = FXCollections.observableArrayList();

        // Database connection and data retrieval
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Drugs")) {

            while (rs.next()) {
               int drugID = rs.getInt("drugID");
                String drugName = rs.getString("drugName");
                double unitPrice = rs.getDouble("unitPrice");
                int numOfUnits = rs.getInt("numOfUnits");
                String description = rs.getString("description");
                String supplier = rs.getString("supplier");

                drugList.add(new Drug(drugID, drugName, unitPrice, numOfUnits, description, supplier));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        drugsTable.setItems(drugList);
    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        String drugName = drugNameField.getText();
        double unitPrice = Double.parseDouble(unitPriceField.getText());
        int numOfUnits = Integer.parseInt(numOfUnitsField.getText());
        String description = descriptionField.getText();
        String supplier = supplierField.getText();

        addDrugToDatabase(drugName, unitPrice, numOfUnits, description, supplier);

        // Refresh table view
        loadDrugData();
    }


    @FXML
    private void searchButtonClicked(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadDrugData();
        } else {
            ObservableList<Drug> filteredList = FXCollections.observableArrayList();

            for (Drug drug : drugList) {
                if (drug.getDrugName().toLowerCase().contains(searchText) ||
                        String.valueOf(drug.getUnitPrice()).contains(searchText) ||
                        String.valueOf(drug.getNumOfUnits()).contains(searchText) ||
                        drug.getDescription().toLowerCase().contains(searchText) ||
                        drug.getSupplier().toLowerCase().contains(searchText)) {
                    filteredList.add(drug);
                }
            }
            drugsTable.getItems().clear(); // Clear the table items
            drugsTable.getItems().addAll(filteredList); // Add the filtered items to the table
            //drugsTable.setItems(filteredList);
        }
    }


    private void addDrugToDatabase(String drugName, double unitPrice, int numOfUnits, String description, String supplier) {
        String sql = "INSERT INTO Drugs (drugName, unitPrice, numOfUnits, supplier, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, drugName);
            pstmt.setDouble(2, unitPrice);
            pstmt.setInt(3, numOfUnits);
            pstmt.setString(4, supplier);
            pstmt.setString(5, description);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Switch Screens
    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inventory-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reports-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToNotification(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("notification-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}