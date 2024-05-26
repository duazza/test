/*
Purpose: Custom exception for invalid user input
Author: Tarique Turnbull
 */
package com.mycompany.qproperty.InputValidator;

public class InvalidInputException extends Exception {
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }
}
