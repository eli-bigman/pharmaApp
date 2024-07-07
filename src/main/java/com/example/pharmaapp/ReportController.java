package com.example.pharmaapp;

import com.example.pharmaapp.database.sql.dbConnection;
import com.example.pharmaapp.entities.Customer;
import com.example.pharmaapp.entities.Purchase;
import com.example.pharmaapp.entities.Supplier;
import com.example.pharmaapp.utils.ReportGenerator;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportController implements Initializable {


    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableColumn<Purchase, Integer> purchaseIdColumn;
    @FXML
    private TableColumn<Purchase, Integer> drugIdColumn;
    @FXML
    private TableColumn<Purchase, Double> priceSoldColumn;

    @FXML
    private TableColumn<Purchase, Double> purchaseCustomerIdColumn;
    @FXML
    private TableColumn<Purchase, String> dateTimeColumn;


    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, Integer> supplierIDColumn;

    @FXML
    private TableColumn<Supplier, String> supplierNameColumn;

    @FXML
    private TableColumn<Supplier, String> supplierLocationColumn;

    @FXML
    private TableColumn<Supplier, String> supplierContactInfoColumn;


    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerContactInfoColumn;

    @FXML
    private TextField customrerNameField;
    @FXML
    private TextField contactInfoField;


    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

    private ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();;

    /**
     // Initializes Interface
     *

     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        purchaseIdColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseID"));
        drugIdColumn.setCellValueFactory(new PropertyValueFactory<>("drugID"));
        priceSoldColumn.setCellValueFactory(new PropertyValueFactory<>("priceSold"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        purchaseCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        purchaseTable.setItems(purchaseList);
        loadPurchases();


        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        supplierContactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        supplierTable.setItems(supplierList);
        loadSuppliers();

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerContactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        customerTable.setItems(customerList);
        loadCustomers();
    }

//    public void setSupplierList(ObservableList<Supplier> supplierList) {
//        this.supplierList = supplierList;
//        supplierTable.setItems(supplierList);
//    }

    /**
     // Load Suppliers from database
     *
     */  private void loadSuppliers() {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Suppliers")) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("supplierID"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("location"),
                        resultSet.getString("contactInfo")
                );

                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     // Load purchases from database
     *
     */
    private void loadPurchases() {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Purchase")) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = new Purchase(
                        resultSet.getInt("purchase_id"),
                        resultSet.getInt("drugID"),
                        resultSet.getDouble("price_sold"),
                        resultSet.getInt("customerID")
                );

                purchaseList.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     // Load customers from database
     *
     */
    private void loadCustomers() {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Customers")) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("customerName"),
                        resultSet.getString("phoneNumber")
                );

                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void handleGenerateReport(){
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport();
    }


    @FXML
    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inventory-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reports-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToSales(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sales-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}


