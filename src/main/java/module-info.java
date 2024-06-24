module com.example.pharmaapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires com.jfoenix;

    opens com.example.pharmaapp to javafx.fxml;
    exports com.example.pharmaapp;
//  exports com.example.pharmaapp.database;
//  opens com.example.pharmaapp.database to javafx.fxml;
//  exports com.example.pharmaapp.controllers;
//  opens com.example.pharmaapp.controllers to javafx.fxml;
    exports com.example.pharmaapp.database.sql;
    opens com.example.pharmaapp.database.sql to javafx.fxml;
//    exports com.example.pharmaapp.database.dataStructures;
//    opens com.example.pharmaapp.database.dataStructures to javafx.fxml;
    exports com.example.pharmaapp.entities;
    opens com.example.pharmaapp.entities to javafx.fxml;
}