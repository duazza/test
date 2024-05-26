/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty.Database;

import com.mycompany.qproperty.Model.Customer;
import com.mycompany.qproperty.Model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing customer records in the database.
 * 
 * Author: Duane Powell
 */
public class CustomerDAO {
    private Database database;

    /**
     * Constructor that initializes the database connection.
     */
    public CustomerDAO() {
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new customer to the database.
     * 
     * @param customer The customer to be added.
     * @throws SQLException If an error occurs during the database operation.
     */
    public void addCustomer(Customer customer) throws SQLException {
        int addressId = addAddress(customer.getAddress());

        String sql = "INSERT INTO customers (first_name, last_name, phone_number, fk_customer_address_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = database.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getPhoneNumber());
        statement.setInt(4, addressId);

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to create new customer record");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                customer.setCustomerId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Failed to add customer, no ID generated");
            }
        }
    }

    /**
     * Adds a new address to the database.
     * 
     * @param address The address to be added.
     * @return The ID of the newly added address.
     * @throws SQLException If an error occurs during the database operation.
     */
    private int addAddress(Address address) throws SQLException {
        String sql = "INSERT INTO address (street_number, street, city, state, postal_code) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = database.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, address.getStreetNumber());
        statement.setString(2, address.getStreet());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getState());
        statement.setInt(5, address.getPostalCode());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to create new address record");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to add address, no ID generated");
            }
        }
    }

    /**
     * Retrieves all customers from the database.
     * 
     * @return A list of all customers.
     * @throws SQLException If an error occurs during the database operation.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.phone_number, " +
                     "a.street_number, a.street, a.city, a.state, a.postal_code " +
                     "FROM customers c " +
                     "JOIN address a ON c.fk_customer_address_id = a.address_id";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            Address address = new Address(
                result.getInt("street_number"),
                result.getString("street"),
                result.getString("city"),
                result.getString("state"),
                result.getInt("postal_code")
            );
            Customer customer = new Customer(
                result.getInt("customer_id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("phone_number"),
                address
            );
            customers.add(customer);
        }

        return customers;
    }

    /**
     * Searches for customers by phone number.
     * 
     * @param phoneNumber The phone number to search for.
     * @return A list of customers with the matching phone number.
     * @throws SQLException If an error occurs during the database operation.
     */
    public List<Customer> searchCustomersByPhone(String phoneNumber) throws SQLException {
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.phone_number, a.street_number, a.street, a.city, a.state, a.postal_code " +
                     "FROM customers c " +
                     "JOIN address a ON c.fk_customer_address_id = a.address_id " +
                     "WHERE c.phone_number LIKE ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setString(1, "%" + phoneNumber + "%");

        ResultSet result = statement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            customers.add(createCustomerFromResultSet(result));
        }

        return customers;
    }

    /**
     * Searches for customers by last name.
     * 
     * @param lastName The last name to search for.
     * @return A list of customers with the matching last name.
     * @throws SQLException If an error occurs during the database operation.
     */
    public List<Customer> searchCustomersByLastName(String lastName) throws SQLException {
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.phone_number, a.street_number, a.street, a.city, a.state, a.postal_code " +
                     "FROM customers c " +
                     "JOIN address a ON c.fk_customer_address_id = a.address_id " +
                     "WHERE c.last_name LIKE ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setString(1, "%" + lastName + "%");

        ResultSet result = statement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            customers.add(createCustomerFromResultSet(result));
        }

        return customers;
    }

    /**
     * Creates a customer object from the result set.
     * 
     * @param rs The result set.
     * @return The customer object.
     * @throws SQLException If an error occurs during the database operation.
     */
    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address(
                rs.getInt("street_number"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getInt("postal_code")
        );
        Customer customer = new Customer(
                rs.getInt("customer_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                address
        );
        return customer;
    }

    /**
     * Updates an existing customer in the database.
     * 
     * @param customer The customer to be updated.
     * @throws SQLException If an error occurs during the database operation.
     */
    public void updateCustomer(Customer customer) throws SQLException {
        int addressId = addAddress(customer.getAddress());

        String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, fk_customer_address_id = ? WHERE customer_id = ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getPhoneNumber());
        statement.setInt(4, addressId);
        statement.setInt(5, customer.getCustomerId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to update customer record");
        }
    }
}
