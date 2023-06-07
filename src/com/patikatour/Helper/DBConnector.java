package com.patikatour.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public Connection connect(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(Config.DB_URL,Config.DB_UNAME,Config.DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static Connection getConnect(){
        DBConnector dbConnector = new DBConnector();
        return dbConnector.connect();
    }
}
