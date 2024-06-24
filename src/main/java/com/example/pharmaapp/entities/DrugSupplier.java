package com.example.pharmaapp.entities;

// DrugSupplier.java
public class DrugSupplier {
    private int drugSuppID;
    private int supplierID;
    private int drugID;

    // getters and setters
    public int getDrugSuppID() {
        return drugSuppID;
    }

    public void setDrugSuppID(int drugSuppID) {
        this.drugSuppID = drugSuppID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }
}