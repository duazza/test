package com.mycompany.qproperty.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    String url;
    String user;
    String password;
    Connection connection;

    /**
     * Constructor for database object. To be used for creating connections to the DB
     * @throws SQLException DB errors
     */
    public Database() throws SQLException {
        url = "jdbc:mysql://localhost:3306/propertyservicedb";
        user = "root";
        password = "password24";
        connection = DriverManager.getConnection(url,user,password);
    }


}
