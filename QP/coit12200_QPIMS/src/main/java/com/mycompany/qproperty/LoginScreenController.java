/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.qproperty;

import com.mycompany.qproperty.Database.UserDAO;
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

public class LoginScreenController implements Initializable {

    @FXML
    private Button signinButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button aboutButton;
    @FXML
    private TextField usernameField;
    @FXML
    private Button registerButton;

    private UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userDAO = new UserDAO();
    }

    @FXML
    private void signinAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        try {
            boolean isValid = userDAO.validateUser(username, password);
            if (isValid) {
                App.setRoot("mainScreen");
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void aboutAction(ActionEvent event) {
        try {
            App.setRoot("aboutLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerAction(ActionEvent event) {
        try {
            App.setRoot("registerNewUser");
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