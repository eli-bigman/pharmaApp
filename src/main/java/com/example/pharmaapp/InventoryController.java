package com.example.pharmaapp;


import com.example.pharmaapp.entities.Drug;
import com.example.pharmaapp.database.sql.dbConnection;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Iterator;
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
    public static void refreshDrugTable(){
       InventoryController x=new InventoryController();
       x.loadDrugData();}

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

    private ObservableSet<Drug> drugList;

    @FXML
    private TextField drugNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField numOfUnitsField;
    @FXML
    private TextArea descriptionField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label sellErrorLabel;


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
    private TextField quantityBoughtField;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton sellButton;


    @FXML
    private JFXButton searchButton;

    @FXML
    private TextField searchField;







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
    /**
     // Retrieves drugs from database to be displayed in drug table
     *
     *
     */
    private void loadDrugData() {
            drugList = FXCollections.observableSet(); //Set will not allow duplicates

            // Database connection and data retrieval
            try (Connection conn = dbConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM drugs ")) {

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

            drugsTable.setItems(FXCollections.observableArrayList(drugList));
            drugsTable.refresh();
    }

//    @FXML
//    private void addButtonClicked(ActionEvent event) {
//        String drugName = drugNameField.getText();
//        double unitPrice = Double.parseDouble(unitPriceField.getText());
//        int numOfUnits = Integer.parseInt(numOfUnitsField.getText());
//        String description = descriptionField.getText();
//        String supplierName = supplierNameField.getText();
//        String supplierLocation = supplierLocationField.getText();
//        String supplierContactInfo = supplierContactInfoField.getText();
//
//        addDrugToDatabase(drugName, unitPrice, numOfUnits, description, supplierName);
//        addSupplierToDatabase(supplierName,supplierContactInfo,supplierLocation);
//
//        drugNameField.clear();
//        unitPriceField.clear();
//        numOfUnitsField.clear();
//        descriptionField.clear();
//        supplierNameField.clear();
//        supplierLocationField.clear();
//        supplierContactInfoField.clear();
//        supplierNameField.clear();
//
//        // Refresh table view
//        loadDrugData();
//    }


    @FXML
    /**
     // Adds drug to system and database
     *
     * @param event A click event
     *
     *
     */
    private void addButtonClicked(ActionEvent event) {
        String drugName = drugNameField.getText();
        String unitPriceStr = unitPriceField.getText();
        String numOfUnitsStr = numOfUnitsField.getText();
        String description = descriptionField.getText();
        String supplierName = supplierNameField.getText();
        String supplierLocation = supplierLocationField.getText();
        String supplierContactInfo = supplierContactInfoField.getText(); //Retrieves the text typed in the fields of application

        if (drugName.isEmpty() || unitPriceStr.isEmpty() || numOfUnitsStr.isEmpty() || description.isEmpty() || supplierName.isEmpty() || supplierLocation.isEmpty() || supplierContactInfo.isEmpty()) {
            errorLabel.setText("Invalid Entry");
            errorLabel.setVisible(true);
            return;
        }

        double unitPrice = Double.parseDouble(unitPriceStr);
        int numOfUnits = Integer.parseInt(numOfUnitsStr);

        addDrugToDatabase(drugName, unitPrice, numOfUnits, description, supplierName);
        addSupplierToDatabase(supplierName, supplierContactInfo, supplierLocation);

        drugNameField.clear();
        unitPriceField.clear();
        numOfUnitsField.clear();
        descriptionField.clear();
        supplierNameField.clear();
        supplierLocationField.clear();
        supplierContactInfoField.clear();

        errorLabel.setText("");
        errorLabel.setVisible(false);

        // Refresh table view
        loadDrugData();
    }




    @FXML
    /**
     // Searched based on any column
     *
     * @param event A click event
     *
     *
     */
    private void searchButtonClicked(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadDrugData();
        } else {
            ObservableList<Drug> filteredList = FXCollections.observableArrayList();
            Iterator<Drug> drugIterator=drugList.iterator();
            while (drugIterator.hasNext()){
                Drug drug=drugIterator.next();
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



//    public void writeToFile() {
//        BufferedWriter writer = null;
//        try {
//            string
//            writer = new BufferedWriter(new FileWriter("C:\\Users\\Eli\\IdeaProjects\\PharmaApp\\Sales.txt",true));
//            writer.write(temp.toString());
//            writer.newLine();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }}}
//
//    }

    /**
     // Adds drug to database
     *
     *@param drugName drug name
     *@param unitPrice unit price of drug
     * @param numOfUnits num of units in stock
     * @param  description description of drug
     *
     *
     */
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

    /**
     // Adds supplier to database
     *
     *@param supplierName supplier name
     *@param supplierContactInfo contact of drug
     * @param supplierLocation location of supplier
     *
     *
     *
     */
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


    /**
     // Adds drug to database
     *
     *@param customerName customer name
     *@param customerPhoneNo customer contact info
     *
     *
     */

    private int addCustomerToDatabase(String customerName, String customerPhoneNo) {
        String sql = "INSERT INTO Customers (customerName, phoneNumber) VALUES (?, ?)";
        int customerID = 0;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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




    /**
     //Handle purchase logic
     *
     * @param event A click event
     *
     *
     */
    @FXML
    private void sellButtonClicked(ActionEvent event) {
        //add drugs to purchased and create a new customer object
        String customerName = customerNameField.getText();
        String customerPhoneNo = customerPhoneNumberField.getText();
        String quantityBoughtText = quantityBoughtField.getText();
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        // Select
        if (selectedDrug == null && customerName.isEmpty() && customerPhoneNo.isEmpty() && quantityBoughtText.isEmpty()) {

            sellErrorLabel.setText("Please select a drug and fill in all fields");
            sellErrorLabel.setVisible(true);
            return;
        }

        int quantityBought;
        try {
            quantityBought = Integer.parseInt(quantityBoughtText);
        } catch (NumberFormatException e) {
            sellErrorLabel.setText("Invalid number format");
            sellErrorLabel.setVisible(true);
            return;
        }
if(quantityBought<selectedDrug.getNumOfUnits() && phoneNumValidator(customerPhoneNo)){
            int customerID = addCustomerToDatabase(customerName, customerPhoneNo);

            addPurchaseToDatabase(selectedDrug.getDrugID(), selectedDrug.getUnitPrice()*quantityBought, customerID,quantityBought);

            processDrugSale(selectedDrug.getDrugID(), quantityBought);
            loadDrugData();
            customerPhoneNumberField.clear();
            customerNameField.clear();
            quantityBoughtField.clear();

            sellErrorLabel.setText("");
            sellErrorLabel.setVisible(false);}
else if(quantityBought>selectedDrug.getNumOfUnits()){
    sellErrorLabel.setText("Invalid quantity");
    sellErrorLabel.setVisible(true);}

else if (!phoneNumValidator(customerPhoneNo)){
            sellErrorLabel.setText("Invalid Phone number");
            sellErrorLabel.setVisible(true);
        }

    }





    /**
     // Drug sale logic
     *
     *@param drugID drugID
     * @param quantityBought num of units bought
     *
     *
     */
    private void processDrugSale(int drugID, int quantityBought) {
        String updateSql = "UPDATE Drugs SET numOfUnits = numOfUnits - ?, available = CASE WHEN numOfUnits = 0 THEN 'yes' ELSE available END WHERE drugID =?";
        String querySql = "SELECT numOfUnits FROM Drugs WHERE drugID =?";
        String insertSql = "INSERT INTO Purchase (drugID, price_sold, date_time) SELECT drugID, unitPrice,? FROM Drugs WHERE drugID =?";
//        String insertQuantity = "INSERT INTO Purchase quantity ";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateSql);
             PreparedStatement queryStmt = connection.prepareStatement(querySql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            updateStmt.setInt(1, quantityBought);
            updateStmt.setInt(2, drugID);
            updateStmt.executeUpdate();

            queryStmt.setInt(1, drugID);

            ResultSet resultSet = queryStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt("numOfUnits") == 0) {
                insertStmt.setString(1, Timestamp.valueOf(LocalDateTime.now()).toString());
                insertStmt.setInt(2, drugID);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPurchaseToDatabase(int drugID, double price_sold, int customerID,int quantityBought) {
        String sql = "INSERT INTO Purchase (drugID, price_sold, date_time, customerID,quantity) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, drugID);
            pstmt.setDouble(2, price_sold);
            pstmt.setString(3, Timestamp.valueOf(LocalDateTime.now()).toString());
            pstmt.setInt(4, customerID);
            pstmt.setInt(5, quantityBought);


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
       Switch.switchToDashboard(event);
    }

    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
     Switch.switchToInventory(event);
    }


    public void switchToReports(ActionEvent event) throws IOException {
      Switch.switchToReports(event);
    }


    public void switchToSales(ActionEvent event) throws IOException {
        Switch.switchToSales(event);
    }

    public static Boolean phoneNumValidator(String number){
        String regex = "^0\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

}