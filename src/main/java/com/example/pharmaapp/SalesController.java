package com.example.pharmaapp;


/**
 * Controller class for the Sales view.
 *
 * This class handles the logic for the Sales view, including populating the drug table, the sales history table and handling button clicks.
 *
 *
 */


import com.example.pharmaapp.entities.Drug;
import com.example.pharmaapp.database.sql.dbConnection;
import com.example.pharmaapp.entities.Sale;
import com.example.pharmaapp.utils.ReportGenerator;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;



public class SalesController implements Initializable {

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
    private TableView<Sale> purchaseHistoryTable;

    @FXML
    private TableColumn<Sale, Integer> purchaseIDColumn;
    @FXML
    private TableColumn<Sale, String> customerNameColumn;
    @FXML
    private TableColumn<Sale, String> phoneNumberColumn;
    @FXML
    private TableColumn<Sale, String> drugSoldNameColumn;
    @FXML
    private TableColumn<Sale, Double> priceColumn;
    @FXML
    private TableColumn<Sale, Integer> quantityColumn;
    @FXML
    private TableColumn<Sale, String> drugDescriptionColumn;
    @FXML
    private TableColumn<Sale, Double> totalAmountColumn;
    @FXML
    private TableColumn<Sale, String> purchaseDateColumn;

    private ObservableList<Sale> purchaseHistory = FXCollections.observableArrayList();


    @FXML
    private TextField searchField;

    @FXML
    private Button deleteDrugButton;

    @FXML
    private Button showPurchaseHistoryButton;

    private ObservableList<Drug> drugList;

    /**
     // Initializes Interface
     *

     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize drug table columns
        drugIDColumn.setCellValueFactory(new PropertyValueFactory<>("drugID"));
        drugNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        numOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfUnits"));
        drugSuppIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data from database
        loadDrugData();

        //initialize sales table column
        purchaseIDColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        drugSoldNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        drugDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("drugDescription"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        // Clear purchase history
        purchaseHistory.clear();
        //update table
        purchaseHistoryTable.setItems(purchaseHistory);

    }
    /**
     // Load drugs from database
     *
     */
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

        }
    }




    //Store the drug Id of recently deleted drug
    private int recentDeletedDrugID;
    /**
     // Deletes drugs from database and system
     *
     */ /**
     // Load Suppliers from database
     *
     */
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();

        if (selectedDrug != null) {
            // Delete the drug from the database
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM drugs " +
                         "WHERE drugID = ?;")) {
                recentDeletedDrugID = selectedDrug.getDrugID();
                stmt.setInt(1, selectedDrug.getDrugID());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Remove the drug from the table
            drugList.remove(selectedDrug);
            drugsTable.getItems().remove(selectedDrug);
            InventoryController.refreshDrugTable();

        }
    }



    @FXML
    private void handleUndoDeleteButton(ActionEvent event) {
//        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
//        if (selectedDrug == null) {
//            // Delete the drug from the database
//            try (Connection conn = dbConnection.getConnection();
//                 PreparedStatement stmt = conn.prepareStatement("UPDATE drugs " +
//                         "SET available = 'yes', deleted = false " +
//                         "WHERE drugID = ?;")) {
//                stmt.setInt(1, recentDeletedDrugID);
//                stmt.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            // add the drug from the table
//            drugList.add(selectedDrug);
//            drugsTable.getItems().add(selectedDrug);
//        }
    }




    /**
     // Load Suppliers from database
     *
     * @param event Button click (purchase history)
     */
    @FXML
    private void handlePurchaseHistoryButton(ActionEvent event) {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            // Clear the purchase history table
            purchaseHistory.clear();

            // Get the purchase history for the selected drug
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT p.Purchase_id, c.customerName, c.phoneNumber, d.drugName, p.price_sold, p.quantity, d.description, p.date_time, (p.price_sold * p.quantity) AS totalAmount " +
                                 "FROM purchase p " +
                                 "JOIN customers c ON p.CustomerID = c.id " +
                                 "JOIN drugs d ON p.DrugID = d.drugID " +
                                 "WHERE d.drugID = ?"))
            {
                stmt.setInt(1, selectedDrug.getDrugID());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int purchaseID = rs.getInt("purchase_id");
                    String customerName = rs.getString("customerName");
                    String phoneNumber = rs.getString("phoneNumber");
                    String drugName = rs.getString("drugName");
                    double priceSold = rs.getDouble("price_sold");
                    int quantity = rs.getInt("quantity");
                    String drugDescription = rs.getString("description");
                    String purchaseDate = rs.getString("date_time");
                    double totalAmount = rs.getDouble("totalAmount");

                    purchaseHistory.add(new Sale(purchaseID, customerName, phoneNumber, drugName, priceSold, quantity, drugDescription, totalAmount, purchaseDate));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //Comparator<Sale> x=new compareByTotalAmount();
          //  FXCollections.sort(purchaseHistory,x);
            purchaseHistoryTable.setItems(purchaseHistory);
        }
    }

class compareByTotalAmount implements Comparator<Sale> {


    @Override
    public int compare(Sale o1, Sale o2) {
        return  Double.compare(o1.getTotalAmount(),o2.getTotalAmount());
    }
}

    class compareByTime implements Comparator<Sale> {


        @Override
        public int compare(Sale o1, Sale o2) {
            return o1.getPurchaseDate().compareTo(o2.getPurchaseDate());

        }
    }


    /**
     // Generates reports
     *
     */

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



}

