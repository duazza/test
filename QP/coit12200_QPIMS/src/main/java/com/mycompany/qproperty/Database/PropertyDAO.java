package com.mycompany.qproperty.Database;

import com.mycompany.qproperty.Model.Address;
import com.mycompany.qproperty.Model.Property;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PropertyDAO {
    Database database;

    public PropertyDAO() {
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a customer with a specific customer ID exists in the database
     * @param customerId customer ID to check in the DB
     * @return boolean, true if customer exists, false otherwise
     * @throws SQLException DB operation errors
     */
    public boolean customerExists (int customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers WHERE customer_id = ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setInt(1,customerId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() && resultSet.getInt(1) > 0;
    }

    /**
     * Adds a property record to the database
     * @param property Property object representing fields to be inserted
     * @throws SQLException DB operation errors, failure to create record errors.
     */
    public void addProperty(Property property) throws SQLException {
        if (!customerExists(property.getCustomerId())) {
            throw new SQLException("Failed to add property: Referenced customer ID does not exist");
        }

        String sql = "INSERT INTO properties (property_id, description, bedrooms, bathrooms,"
                + "has_carpark, year_built, property_type, agent_name, fk_property_customer_id, fk_property_address_id)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = database.connection.prepareStatement(sql);

        statement.setInt(1,property.getPropertyId());
        statement.setString(2,property.getDescription());
        statement.setInt(3,property.getBedrooms());
        statement.setInt(4,property.getBathrooms());
        statement.setInt(5,property.getCarPark());
        statement.setDate(6,property.getYearBuilt());
        statement.setString(7,property.getPropertyType());
        statement.setString(8,property.getAgentName());
        statement.setInt(9,property.getCustomerId());
        statement.setInt(10,property.getAddress().getAddressID());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0){
            throw new SQLException("Failed to create new property record");
        }
    }

    /**
     * Returns for a list of properties that match a search term
     * @param searchTerm street name or street number
     * @return List of properties
     * @throws SQLException No records found + other SQL errors
     */
    public List<Property> searchProperties (String searchTerm) throws SQLException {
        String searchTerm2 = "%" + searchTerm + "%";
        String sql = "SELECT * FROM properties JOIN address ON properties.fk_property_address_id = address.address_id"
                        + " WHERE address.street_number LIKE ?"
                        + " OR address.street LIKE ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setString(1,searchTerm2);
        statement.setString(2,searchTerm2);

        ResultSet result = statement.executeQuery();

        List<Property> properties = new ArrayList<>();
        while (result.next()) {
            Property property = new Property();
            Address address = new Address();

            property.setPropertyId(result.getInt("property_id"));
            property.setDescription(result.getString("description"));
            property.setBedrooms(result.getInt("bedrooms"));
            property.setBathrooms(result.getInt("bathrooms"));
            property.setCarPark(result.getInt("has_carpark"));
            property.setYearBuilt(result.getDate("year_built"));
            property.setPropertyType(result.getString("property_type"));
            property.setAgentName(result.getString("agent_name"));
            property.setCustomerId(result.getInt("fk_property_customer_id"));

            address.setAddressID(result.getInt("address_id"));
            address.setStreetNumber(result.getInt("street_number"));
            address.setStreet(result.getString("street"));
            address.setCity(result.getString("city"));
            address.setState(result.getString("state"));
            address.setPostalCode(result.getInt("postal_code"));

            property.setAddress(address);
            properties.add(property);

        }

        if (properties.isEmpty()) {
            throw new SQLException("No records found with provided search term");
        }

        return properties;
    }

    /**
     * Extracts all property records from the DB.
     * @return List<Property> Represents all property records extracted from the DB.
     * @throws SQLException No records found + other SQL errors
     */
    public List<Property> getAllProperties () throws SQLException {
        String sql = "SELECT * FROM properties JOIN address ON properties.fk_property_address_id = address.address_id";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        List<Property> properties = new ArrayList<>();

        while (result.next()) {
            Property property = new Property();
            Address address = new Address();

            property.setPropertyId(result.getInt("property_id"));
            property.setDescription(result.getString("description"));
            property.setBedrooms(result.getInt("bedrooms"));
            property.setBathrooms(result.getInt("bathrooms"));
            property.setCarPark(result.getInt("has_carpark"));
            property.setYearBuilt(result.getDate("year_built"));
            property.setPropertyType(result.getString("property_type"));
            property.setAgentName(result.getString("agent_name"));
            property.setCustomerId(result.getInt("fk_property_customer_id"));

            address.setAddressID(result.getInt("address_id"));
            address.setStreetNumber(result.getInt("street_number"));
            address.setStreet(result.getString("street"));
            address.setCity(result.getString("city"));
            address.setState(result.getString("state"));
            address.setPostalCode(result.getInt("postal_code"));

            property.setAddress(address);
            properties.add(property);

        }

        if (properties.isEmpty()) {
            throw new SQLException("No records found with provided search term");
        }

        return properties;
    }


    /**
     * Updates property record attributes in the DB.
     * @param property Represents the property record in the DB to update
     * @throws SQLException Failure to update property + other SQL errors
     */
    public void updateProperty (Property property) throws SQLException {
        String sql = "UPDATE properties SET description = ?, bedrooms = ?, bathrooms = ?, has_carpark = ?, year_built = ?, property_type = ?, agent_name = ? WHERE property_id = ?";
        PreparedStatement statement = database.connection.prepareStatement(sql);
        statement.setString(1,property.getDescription());
        statement.setInt(2,property.getBedrooms());
        statement.setInt(3, property.getBathrooms());
        statement.setInt(4,property.getCarPark());
        statement.setDate(5,property.getYearBuilt());
        statement.setString(6,property.getPropertyType());
        statement.setString(7,property.getAgentName());
        statement.setInt(8,property.getPropertyId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Failed to update property");
        }

    }
}
