package com.example.coffee.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.coffee.constant.constants.*;

public class DBConnection {

    public static final Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Disconnect");
            e.printStackTrace();
        }
        return connection;
    }
}


