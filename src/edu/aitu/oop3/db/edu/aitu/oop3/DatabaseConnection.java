package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Connection details
    private static final String URL = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.trjrofabmvkrkxmvmhic";

    // MAKE SURE TO PUT YOUR ACTUAL PASSWORD HERE
    private static final String PASSWORD = "ThisIsTheBigBlack";

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}