package com.example.pharmaapp.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// Purchase.java
public class Purchase {
    private int purchaseID;
    private int drugID;
    private double priceSold;
    private final String dateTime;
    private int customerID;

    public Purchase(int purchaseID, int drugID, double priceSold, int customerID) {
        this.purchaseID = purchaseID;
        this.drugID = drugID;
        this.priceSold = priceSold;
        this.dateTime = Timestamp.valueOf(LocalDateTime.now()).toString();
        this.customerID = customerID;
    }




    // getters and setters
    public int getPurchaseID() {
        return purchaseID;
    }

    public int getCustomerID() {
        return customerID;
    }


//    public void setPurchaseID(int purchaseID) {
//        this.purchaseID = purchaseID;
//    }

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public double getPriceSold() {
        return priceSold;
    }

    public void setPriceSold(double priceSold) {
        this.priceSold = priceSold;
    }

    public String getDateTime() {
        return dateTime;
    }

//    public void setDateTime(String dateTime) {
//        this.dateTime = dateTime;
//    }
}