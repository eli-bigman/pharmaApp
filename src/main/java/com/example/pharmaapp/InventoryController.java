package com.example.pharmaapp;


import com.example.pharmaapp.entities.Drug;
import com.example.pharmaapp.database.sql.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private TextArea descriptionField;

    @FXML
    private TextField supplierNameField;
    @FXML
    private TextField supplierLocationField;
    @FXML
    private TextField supplierContactInfoField;



    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneNumberField;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton sellButton;


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
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Drugs WHERE purchased = 'no'")) {

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
        String supplierName = supplierNameField.getText();
        String supplierLocation = supplierLocationField.getText();
        String supplierContactInfo = supplierContactInfoField.getText();

        addDrugToDatabase(drugName, unitPrice, numOfUnits, description, supplierName);
        addSupplierToDatabase(supplierName,supplierContactInfo,supplierLocation);

        drugNameField.clear();
        unitPriceField.clear();
        numOfUnitsField.clear();
        descriptionField.clear();
        supplierNameField.clear();
        supplierLocationField.clear();
        supplierContactInfoField.clear();
        supplierNameField.clear();

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
            drugsTable.getItems().clear();
            drugsTable.getItems().addAll(filteredList);
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


    private void addSupplierToDatabase(String supplierName, String supplierContactInfo, String supplierLocation) {
        String sql = "INSERT INTO Suppliers (supplierName,location,contactInfo) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplierName);
            pstmt.setString(2, supplierLocation);
            pstmt.setString(3, supplierContactInfo);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int addCustomerToDatabase(String customerName, String customerPhoneNo) {
        String sql = "INSERT INTO Customers (customerName, contactInfo) VALUES (?, ?)";
        int customerID = 0;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerName);
            pstmt.setString(2, customerPhoneNo);
            pstmt.executeUpdate();


            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerID = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customerID;
    }




    //Handle purchase logic

    @FXML
    private void sellButtonClicked(ActionEvent event) {
        //add drugs to purchased and create a new customer object
        String customerName = customerNameField.getText();
        String customerPhoneNo = customerPhoneNumberField.getText();
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null && customerName != null && customerPhoneNo != null) {

            int customerID = addCustomerToDatabase(customerName, customerPhoneNo);

            addPurchaseToDatabase(selectedDrug.getDrugID(), selectedDrug.getUnitPrice(), customerID);

            markDrugAsSold(selectedDrug.getDrugID());
            loadDrugData();
            customerPhoneNumberField.clear();
            customerNameField.clear();
        }
    }



    private void markDrugAsSold(int drugID) {
        String updateSql = "UPDATE Drugs SET purchased = 'yes' WHERE drugID =?";
        String insertSql = "INSERT INTO Purchase (drugID, price_sold, date_time) SELECT drugID, unitPrice,? FROM Drugs WHERE drugID =?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            updateStmt.setInt(1, drugID);
            updateStmt.executeUpdate();

            insertStmt.setString(1, Timestamp.valueOf(LocalDateTime.now()).toString());
            insertStmt.setInt(2, drugID);

            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPurchaseToDatabase(int drugID, double price_sold, int customerID) {
        String sql = "INSERT INTO Purchase (drugID, price_sold, date_time, customerID) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, drugID);
            pstmt.setDouble(2, price_sold);
            pstmt.setString(3, Timestamp.valueOf(LocalDateTime.now()).toString());
            pstmt.setInt(4, customerID);

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