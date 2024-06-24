package com.example.pharmaapp.entities;


public class Customer {
    private final int id;
    private String customerName;
    private String contactInfo;


    public Customer(int id, String customerName, String contactInfo) {
        this.id = id;
        this.customerName = customerName;
        this.contactInfo = contactInfo;

    }


    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
//    public void setId(int id) {
//        this.id = id;
//    }

    // Getter for name
    public String getName() {
        return customerName;
    }

    // Setter for name
    public void setName(String name) {
        this.customerName = name;
    }

    // Getter for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    // Setter for contactInfo
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

}

