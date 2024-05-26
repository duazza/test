package com.mycompany.qproperty.InputValidator;

public interface IValidator {
    void validateName(String name) throws InvalidInputException;

    void validateEmail(String email) throws InvalidInputException;

    void validateUsername(String username) throws InvalidInputException;

    void validatePassword(String password) throws InvalidInputException;

    void validatePhoneNumber(String phone) throws InvalidInputException;

    void validateStreet(String street) throws InvalidInputException;

    void validateCity(String city) throws InvalidInputException;

    void validateCarPark(String carPark) throws InvalidInputException;

    void validatePostalCode(String code) throws InvalidInputException;

    void validateStreetNumber(String streetNumber) throws InvalidInputException;

    void validateRooms(String rooms) throws InvalidInputException;

    void validateDescription(String desc) throws InvalidInputException;

    void validateState(String state) throws InvalidInputException;

    void validatePropertyType(String type) throws InvalidInputException;

    void validateSearch(String searchTerm) throws InvalidInputException;
    void validateCustId (String id) throws InvalidInputException;
    void validatePropertyId (String id) throws InvalidInputException;
    void validateCharge(String charge) throws InvalidInputException;
    void validateJobStatus (String status) throws InvalidInputException;
    void validateJobType (String type) throws InvalidInputException;

}
