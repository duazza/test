package com.mycompany.qproperty;

import com.mycompany.qproperty.Database.AddressDAO;
import com.mycompany.qproperty.InputValidator.InvalidInputException;
import com.mycompany.qproperty.Model.Address;

import java.sql.SQLException;
import java.util.List;

public class test {
    static AddressDAO dao = new AddressDAO();

    public static void main(String[] args) {
        try {
            Address address = new Address(
                    1,
                    "test street",
                    "test city",
                    "NSW",
                    1111

            );
            int key = dao.addAddress(address);
            System.out.println(key);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
