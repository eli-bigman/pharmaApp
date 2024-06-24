package com.example.pharmaapp.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {
    private static final String url = "jdbc:mysql://localhost/pharmacy?user=root&password=";
    private static final String user = "elibigman";
    private static final String password = "password";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection established successfully!");
        return connection;
    }
}


/**
 *
 * load the database backup automatically
 */
//
//
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.sql.*;
//import java.util.Objects;
//
//public class dbConnection {
//    private static final String url = "jdbc:mysql://localhost/";
//    private static final String user = "elibigman";
//    private static final String password = "password";
//    private static final String databaseName = "pharmacy_test";
//    private static final String backupScriptPath = "src/main/resources/databaseBackupDump/pharmacy_database_inti.sql";
//
//    public static Connection getConnection() throws SQLException {
//        // Check if the database exists
//        boolean databaseExists = databaseExists();
//
//        if (!databaseExists) {
//            // Run the backup script to create the database
//            runBackupScript();
//        }
//
//        // Connect to the database
//        Connection connection = DriverManager.getConnection(url + databaseName, user, password);
//        System.out.println("Connection established successfully!");
//        return connection;
//    }
//
//    private static boolean databaseExists() throws SQLException {
//        Connection connection = DriverManager.getConnection(url, user, password);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
//        boolean databaseExists = false;
//        while (resultSet.next()) {
//            if (resultSet.getString(1).equals(databaseName)) {
//                databaseExists = true;
//                break;
//            }
//        }
//        connection.close();
//        return databaseExists;
//    }
//
//    private static void runBackupScript() throws SQLException {
//        try (Reader reader = new BufferedReader(new FileReader(
//                dbConnection.class.getClassLoader().getResource("databaseBackupDump/pharmacy_database_init.sql").getFile()))) {
//
//            Connection connection = DriverManager.getConnection(url, user, password);
//            Statement statement = connection.createStatement();
//            String sql;
//            while ((sql = readSqlScript(reader))!= null) {
//                statement.execute(sql);
//            }
//            connection.close();
//            System.out.println("Database created successfully!");
//        } catch (Exception e) {
//            throw new SQLException("Error running backup script: " + e.getMessage());
//        }
//    }
//
//    private static String readSqlScript(Reader reader) throws Exception {
//        StringBuilder sql = new StringBuilder();
//        int c;
//        while ((c = reader.read())!= -1) {
//            sql.append((char) c);
//            if (c == ';') {
//                return sql.toString();
//            }
//        }
//        return null;
//    }
//}