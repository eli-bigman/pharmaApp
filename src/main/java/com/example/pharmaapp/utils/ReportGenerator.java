package com.example.pharmaapp.utils;

import com.example.pharmaapp.database.sql.dbConnection;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;


public class ReportGenerator {
    public void generateReport() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM purchase")) {

            StringBuilder report = new StringBuilder();
            report.append("Purchase Report\n");
            report.append("===============\n");

            while (resultSet.next()) {
                int purchaseId = resultSet.getInt("purchase_id");
                int drugId = resultSet.getInt("drugID");
                double priceSold = resultSet.getDouble("price_sold");
                Date dateTime = resultSet.getDate("date_time");
                int customerId = resultSet.getInt("customerID");
                int quantity = resultSet.getInt("quantity");

                report.append("Purchase ID: ").append(purchaseId).append("\n");
                report.append("Drug ID: ").append(drugId).append("\n");
                report.append("Price Sold: ").append(priceSold).append("\n");
                report.append("Date and Time: ").append(dateTime).append("\n");
                report.append("Customer ID: ").append(customerId).append("\n");
                report.append("Quantity: ").append(quantity).append("\n");
                report.append("\n");
            }

            boolean reportGenerated = writeReportToFile(report.toString());

            if (reportGenerated) {
                showReportGeneratedPopup("Report generated successfully!");
                askToOpenReport();
            } else {
                showReportGeneratedPopup("Failed to generate report.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showReportGeneratedPopup("Failed to generate report.");
        }
    }

    private boolean writeReportToFile(String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write(report);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showReportGeneratedPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report Generation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void askToOpenReport() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Open Report");
        alert.setContentText("Do you want to open the report?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            openReport();
        }
    }

    private void openReport() {
        try {
            Desktop desktop = Desktop.getDesktop();
            File file = new File("SalesReport.txt");
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
