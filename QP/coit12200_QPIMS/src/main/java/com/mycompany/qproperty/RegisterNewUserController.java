/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.qproperty;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.Database.UserDAO;
import com.mycompany.qproperty.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author duane
 */
public class RegisterNewUserController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button exitButton;
    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userDAO = new UserDAO();
    }

    @FXML
    private void registerAction(ActionEvent event) {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        User user = new User(firstName, lastName, email, username, password);

        try {
            userDAO.addUser(user);
            showAlert("Success", "Registration successful!");
            App.setRoot("loginScreen");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred.");
        }
    }

    @FXML
    private void aboutAction(ActionEvent event) {
        // Show about information
    }

    @FXML
    private void exitAction(ActionEvent event) {
        try {
            App.setRoot("loginScreen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}