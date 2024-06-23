package com.example.pharmaapp.database.dataStructures;

import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String address;
    private String contactInfo;
    private List<String> productsSupplied;

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    // Setter for contactInfo
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Getter for productsSupplied
    public List<String> getProductsSupplied() {
        return productsSupplied;
    }

    // Setter for productsSupplied
    public void setProductsSupplied(List<String> productsSupplied) {
        this.productsSupplied = productsSupplied;
    }
}

