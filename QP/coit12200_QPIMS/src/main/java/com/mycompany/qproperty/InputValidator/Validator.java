/*
Purpose: Validate various user input
Author: Tarique Turnbull
 */
package com.mycompany.qproperty.InputValidator;

import java.util.List;

public class Validator implements IValidator {
    public Validator() {
    }

    private void validateEmpty (String input, String message) throws InvalidInputException {
        if (input.isEmpty()) {
            throw new InvalidInputException(message);
        }
    }

    private void validateSpaces(String input, String message) throws InvalidInputException {
        if (input.contains(" ")) {
            throw new InvalidInputException(message);
        }
    }

    private void validateLength(String input, int validLength, String message) throws InvalidInputException {
        if (input.length() > validLength) {
            throw new InvalidInputException(message);
        }
    }

    private void validatePositiveNum(String input, String message) throws InvalidInputException {
        if (input.length() < 0) {
            throw new InvalidInputException(message);
        }
    }

    private void validatePattern (String input, String pattern, String message) throws InvalidInputException {
        if (!input.matches(pattern)) {
            throw new InvalidInputException(message);
        }
    }

    private void validateParseInt (String input, String message) throws InvalidInputException {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(message);
        }
    }

    private void validateInList(String input, List<String> validInput, String message) throws InvalidInputException {
        if (!validInput.contains(input)) {
            throw new InvalidInputException(message);
        }
    }
    @Override
    public void validateName(String name) throws InvalidInputException {
        validateEmpty(name, "Name cannot be empty");
        validateSpaces(name,"Name cannot contain spaces");
        validatePattern(name,"[A-Za-z]+", "Name must only contain alphabetical characters");
        validateLength(name,45,"Name cannot exceed 45 characters");
    }

    @Override
    public void validateEmail(String email) throws InvalidInputException {
        String emailPattern = "[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?";
        validateLength(email,45, "Name cannot exceed 45 characters");
        validatePattern(email,emailPattern,"Email must follow the format: abc123@email.com");
    }

    @Override
    public void validateUsername(String username) throws InvalidInputException {
        validateEmpty(username, "Username cannot be empty");
        validateSpaces(username,"Username cannot contain spaces");
        validateLength(username, 45, "Username cannot exceed 45 characters" );
    }

    @Override
    public void validatePassword(String password) throws InvalidInputException {
        validateEmpty(password,"Password cannot be empty");
        validateSpaces(password, "Password cannot contain spaces");
        validateLength(password, 255,"Password cannot exceed 255 characters" );
    }

    @Override
    public void validatePhoneNumber(String phone) throws InvalidInputException{
        validateEmpty(phone, "Phone field cannot be empty");
        validateParseInt(phone, "Phone number must be digits only without spaces");
        validateLength(phone,10,"Phone number cannot exceed 10 digits" );
    }

    @Override
    public void validateStreet(String street) throws InvalidInputException {
        validateEmpty(street, "Street cannot be empty");
        validateLength(street, 45,"Street cannot exceed 45 characters" );
    }
    // TODO write test
    @Override
    public void validateCity(String city) throws InvalidInputException {
        validateEmpty(city, "City cannot be empty");
        validateLength(city,45,"City cannot exceed 45 characters");
    }

    // TODO write test
    @Override
    public void validateCarPark(String carPark) throws InvalidInputException {
        validateEmpty(carPark, "Car park number cannot be empty");
        validateParseInt(carPark, "Car park number must be digits only without spaces");
        List<String> validInput = List.of ("1","2");
        validateInList(carPark, validInput, "CarPark must be either a 1 or a 0");
    }


    // TODO write test
    @Override
    public void validatePostalCode(String code) throws InvalidInputException {
        validateEmpty(code,"Postal code cannot be empty");
        validateParseInt(code,"Postal code must be digits only without spaces");
        validatePattern(code,"\\d{4}$", "Postal code must be 4 digits");
    }

    // TODO write test
    @Override
    public void validateStreetNumber(String streetNumber) throws InvalidInputException {
        validateEmpty(streetNumber, "Street number cannot be empty");
        validateParseInt(streetNumber, "Street number must be digits only without spaces");
        validatePositiveNum(streetNumber, "Street number must be a positive number");
    }

    // TODO write test
    @Override
    public void validateRooms(String rooms) throws InvalidInputException {
        validateEmpty(rooms, "Room number fields cannot be empty");
        validateParseInt(rooms,"Room numbers must be digits only without spaces");
        validatePositiveNum(rooms, "rooms must be a positive number");
    }

    // TODO TEST
    @Override
    public void validateDescription(String desc) throws InvalidInputException {
        validateEmpty(desc, "Description cannot be empty");
        validateLength(desc, 255, "Description cannot exceed 255 characters");
    }

    // TODO TEST
    @Override
    public void validateState(String state) throws InvalidInputException {
        validateEmpty(state, "State cannot be empty");
        List<String> validStates = List.of("NSW", "QLD", "SA", "TAS", "VIC", "WA");
        validateInList(state, validStates, "State must be one of: NSW, QLD, SA, VIC, or WA");
    }

    // TODO TEST
    @Override
    public void validatePropertyType(String type) throws InvalidInputException {
        validateEmpty(type, "Property type cannot be empty");
        List<String>validTypes = List.of("house", "apartment");
        validateInList(type, validTypes, "Property type must be one of: house, apartment");
    }

    @Override
    public void validateSearch(String searchTerm) throws InvalidInputException {
        validateEmpty(searchTerm, "Please input something to search");
    }

    @Override
    public void validateCustId(String id) throws InvalidInputException {

    }

    @Override
    public void validatePropertyId(String id) throws InvalidInputException {

    }

    @Override
    public void validateCharge(String charge) throws InvalidInputException {

    }

    @Override
    public void validateJobStatus(String status) throws InvalidInputException {

    }

    @Override
    public void validateJobType(String type) throws InvalidInputException {

    }
}
