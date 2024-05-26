package com.mycompany.qproperty.Database;

import com.mycompany.qproperty.Model.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {
    Database database;

    public AddressDAO() {
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an address record to the database
     * @param address : Address object representing fields to be inserted into the DB
     * @return int representing the auto-generated primary key to be used in creating other records
     * @throws SQLException : Errors thrown relating to failed DB operations
     */
    public int addAddress(Address address) throws SQLException {
        // Check if inputted address already exists
        String existingAddressSql = "SELECT address_id FROM address WHERE" +
                " street_number = ? AND street = ? AND city = ? AND state = ? AND postal_code = ?";

        PreparedStatement existingAddressStatement = database.connection.prepareStatement(existingAddressSql);
        existingAddressStatement.setInt(1, address.getStreetNumber());
        existingAddressStatement.setString(2, address.getStreet());
        existingAddressStatement.setString(3, address.getCity());
        existingAddressStatement.setString(4, address.getState());
        existingAddressStatement.setInt(5, address.getPostalCode());

        ResultSet resultSet = existingAddressStatement.executeQuery();
        if (resultSet.next()) { // If it does already exist, return its primary key
            return resultSet.getInt("address_id");
        } else { // if it doesnt exist, Insert a new address
            String sql = "INSERT INTO address (street_number,street,city,state,postal_code) VALUES(?,?,?,?,?)";
            PreparedStatement statement = database.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, address.getStreetNumber());
            statement.setString(2,address.getStreet());
            statement.setString(3,address.getCity());
            statement.setString(4,address.getState());
            statement.setInt(5, address.getPostalCode());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to create new address record");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) { // If the address was successfully added, return its primary key
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to add address, no ID generated");
            }
        }
    }

    /**
     *
     * @param address Address object that represents an entry in the DB to update.
     * @throws SQLException Failure to update DB
     */
    public void updateAddress(Address address) throws SQLException {
        PreparedStatement statement = database.connection.prepareStatement
                ("UPDATE address SET street_number = ?, street = ?, city = ?, state = ?, postal_code = ? WHERE address_id = ?");
        statement.setInt(1,address.getStreetNumber());
        statement.setString(2, address.getStreet());
        statement.setString(3,address.getCity());
        statement.setString(4,address.getState());
        statement.setInt(5,address.getPostalCode());
        statement.setInt(6, address.getAddressID());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to update address record");
        }

    }
}
