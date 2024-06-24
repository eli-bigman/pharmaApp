package com.example.pharmaapp.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// Purchase.java
public class Purchase {
   private int purchaseID;
    private int drugID;
    private double priceSold;
    private final String dateTime;

    public Purchase(int drugID, double priceSold) {
        this.purchaseID = purchaseID;
        this.drugID = drugID;
        this.priceSold = priceSold;
        this.dateTime = Timestamp.valueOf(LocalDateTime.now()).toString();;
    }




    // getters and setters
    public int getPurchaseID() {
        return purchaseID;
    }


    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

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