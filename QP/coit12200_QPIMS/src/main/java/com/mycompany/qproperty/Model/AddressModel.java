package com.mycompany.qproperty.Model;

import com.mycompany.qproperty.Database.AddressDAO;
import com.mycompany.qproperty.InputValidator.InvalidInputException;

import java.sql.SQLException;

public class AddressModel {

    private final AddressDAO addressDAO;

    public AddressModel () {
        addressDAO = new AddressDAO();
    }

    public int addAddress (Address address) throws SQLException, InvalidInputException {
        return addressDAO.addAddress(address);
    }

    public void updateAddress(Address address) throws SQLException {
        addressDAO.updateAddress(address);
    }
}
