package com.example.pharmaapp.entities;

/**
 * Represents a sale in the pharmacy system.
 *
 * A sale consists of a purchase ID, customer name, phone number, drug name, price, quantity, drug description, and total amount.
 *
 * @author Team Silicon
 */

public class Sale {
    private int purchaseID;
    private String customerName;
    private String phoneNumber;
    private String drugName;
    private double priceSold;
    private int quantity;
    private String drugDescription;
    private double totalAmount;
    private String purchaseDate;

    public Sale(int purchaseID, String customerName, String phoneNumber, String drugName,double priceSold, int quantity, String drugDescription, double totalAmount, String purchaseDate) {
        this.purchaseID = purchaseID;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.drugName = drugName;
        this.quantity = quantity;
        this.drugDescription = drugDescription;
        this.totalAmount = totalAmount;
        this.purchaseDate = purchaseDate;
        this.priceSold = priceSold;
    }

    // Getters and setters
    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPrice(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDrugDescription() {
        return drugDescription;
    }

    public void setDrugDescription(String drugDescription) {
        this.drugDescription = drugDescription;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPriceSold() {
        return priceSold;
    }

    public void setPriceSold(double priceSold) {
        this.priceSold = priceSold;
    }
}
