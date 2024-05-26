package com.mycompany.qproperty.Model;

import java.sql.Date;

public class Property {
    private int propertyId;
    private String description;
    private int bedrooms;
    private int bathrooms;
    private int carPark;
    private Date yearBuilt;
    private String propertyType;
    private String agentName;
    private Address address;
    private int customerId;


    public Property() {
        
    }
    public Property(int propertyId, String description, int bedrooms, int bathrooms, int carPark, Date yearBuilt, String propertyType, String agentName, Address address, int customerId) {
        this.propertyId = propertyId;
        this.description = description;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.carPark = carPark;
        this.yearBuilt = yearBuilt;
        this.propertyType = propertyType;
        this.agentName = agentName;
        this.address = address;
        this.customerId = customerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getCarPark() {
        return carPark;
    }

    public void setCarPark(int carPark) {
        this.carPark = carPark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Date getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Date yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
