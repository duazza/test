package com.mycompany.qproperty.Model;

import com.mycompany.qproperty.Database.PropertyDAO;
import com.mycompany.qproperty.InputValidator.InvalidInputException;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class PropertyModel {
    private final PropertyDAO propertyDAO;

    public PropertyModel() {
        propertyDAO = new PropertyDAO();
    }

    public void addProperty(Property property) throws SQLException {
        propertyDAO.addProperty(property);
    }

    public List<Property> searchProperties (String searchTerm) throws SQLException {
        return propertyDAO.searchProperties(searchTerm);
    }


    public void updateProperty(Property property) throws SQLException {
        propertyDAO.updateProperty(property);
    }

    public List<Property> getAllProperties() throws SQLException {
        return propertyDAO.getAllProperties();
    }
}
