package com.example.pharmaapp.entities;


public class Drug {
    private final int drugID;
    private String drugName;
    private double unitPrice;
    private int numOfUnits;
    private String description;
    private String supplier;


    public Drug(int drugID, String drugName, double unitPrice, int numOfUnits, String description, String supplier) {
        this.drugID =  drugID;
        this.drugName = drugName;
        this.unitPrice = unitPrice;
        this.numOfUnits = numOfUnits;
        this.description = description;
        this.supplier = supplier;
    }




    public int getDrugID() {
        return drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String drugName) {
        this.description = description;
    }

    public void setSupplier(String supplier) {this.supplier = supplier;}

    public String getSupplier() { return supplier; }
}
