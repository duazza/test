/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty.Model;

/**
 * Model class representing a Customer.
 * 
 * Author: Duane Powell
 */
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;

    /**
     * Constructor for creating a new Customer without an ID.
     * 
     * @param firstName   The first name of the customer.
     * @param lastName    The last name of the customer.
     * @param phoneNumber The phone number of the customer.
     * @param address     The address of the customer.
     */
    public Customer(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Constructor for creating a new Customer with an ID (used for existing customers).
     * 
     * @param customerId  The ID of the customer.
     * @param firstName   The first name of the customer.
     * @param lastName    The last name of the customer.
     * @param phoneNumber The phone number of the customer.
     * @param address     The address of the customer.
     */
    public Customer(int customerId, String firstName, String lastName, String phoneNumber, Address address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters

    /**
     * Gets the customer ID.
     * 
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     * 
     * @param customerId The customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the first name of the customer.
     * 
     * @return The first name of the customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the customer.
     * 
     * @param firstName The first name of the customer.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the customer.
     * 
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the customer.
     * 
     * @param lastName The last name of the customer.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number of the customer.
     * 
     * @return The phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phoneNumber The phone number of the customer.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address of the customer.
     * 
     * @return The address of the customer.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * 
     * @param address The address of the customer.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    // Getters for Address properties

    /**
     * Gets the street number from the customer's address.
     * 
     * @return The street number.
     */
    public int getStreetNumber() {
        return address.getStreetNumber();
    }

    /**
     * Gets the street name from the customer's address.
     * 
     * @return The street name.
     */
    public String getStreet() {
        return address.getStreet();
    }

    /**
     * Gets the city from the customer's address.
     * 
     * @return The city.
     */
    public String getCity() {
        return address.getCity();
    }

    /**
     * Gets the state from the customer's address.
     * 
     * @return The state.
     */
    public String getState() {
        return address.getState();
    }

    /**
     * Gets the postal code from the customer's address.
     * 
     * @return The postal code.
     */
    public int getPostalCode() {
        return address.getPostalCode();
    }
}
