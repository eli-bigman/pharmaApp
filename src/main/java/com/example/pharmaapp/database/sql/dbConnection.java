package com.example.pharmaapp.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {
    private static final String url = "jdbc:mysql://localhost/elipharma?user=root&password=";
    private static final String user = "";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection established successfully!");
        return connection;
    }
}

