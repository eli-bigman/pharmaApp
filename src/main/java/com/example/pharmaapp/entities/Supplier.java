package com.example.pharmaapp.entities;

// Supplier.java
public class Supplier {
    private int supplierID;
    private String supplierName;
    private String location;
    private String contactInfo;

    public Supplier(int supplierID, String supplierName, String location, String contactInfo) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.location = location;
        this.contactInfo = contactInfo;
    }

    // getters and setters
    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}