/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty.Model;

import com.mycompany.qproperty.Database.CustomerDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Model class representing Customer operations.
 * Provides methods to interact with the CustomerDAO for CRUD operations.
 * 
 * Author: Duane Powell
 */
public class CustomerModel {
    private final CustomerDAO customerDAO;

    /**
     * Constructor initializes the CustomerDAO.
     */
    public CustomerModel() {
        customerDAO = new CustomerDAO();
    }

    /**
     * Adds a new customer to the database.
     * 
     * @param customer The customer to be added.
     * @throws SQLException If a database access error occurs.
     */
    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    /**
     * Searches for customers by phone number.
     * 
     * @param phoneNumber The phone number to search for.
     * @return A list of customers matching the phone number.
     * @throws SQLException If a database access error occurs.
     */
    public List<Customer> searchCustomersByPhone(String phoneNumber) throws SQLException {
        return customerDAO.searchCustomersByPhone(phoneNumber);
    }

    /**
     * Searches for customers by last name.
     * 
     * @param lastName The last name to search for.
     * @return A list of customers matching the last name.
     * @throws SQLException If a database access error occurs.
     */
    public List<Customer> searchCustomersByLastName(String lastName) throws SQLException {
        return customerDAO.searchCustomersByLastName(lastName);
    }

    /**
     * Retrieves all customers from the database.
     * 
     * @return A list of all customers.
     * @throws SQLException If a database access error occurs.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    /**
     * Updates an existing customer in the database.
     * 
     * @param customer The customer to be updated.
     * @throws SQLException If a database access error occurs.
     */
    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }
}
