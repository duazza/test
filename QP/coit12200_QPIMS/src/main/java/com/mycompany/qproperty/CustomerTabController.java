/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty;

import com.mycompany.qproperty.InputValidator.InvalidInputException;
import com.mycompany.qproperty.InputValidator.Validator;
import com.mycompany.qproperty.Model.Customer;
import com.mycompany.qproperty.Model.CustomerModel;
import com.mycompany.qproperty.Model.Address;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class for the Customer Tab in the QProperty application.
 * Handles customer addition, update, search, and navigation between scenes.
 * 
 * Author: Duane Powell
 */
public class CustomerTabController implements Initializable {

    @FXML
    private TextField FNameField;
    @FXML
    private TextField LNameField;
    @FXML
    private TextField PostCodeField;
    @FXML
    private TextField StreetNoField;
    @FXML
    private TextField StreetNameField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField phoneField;
    @FXML
    private ChoiceBox<String> StateField;
    @FXML
    private TextField IDField;
    @FXML
    private Button addButton;
    @FXML
    private Button searchPhoneButton;
    @FXML
    private TextField searchPhoneField;
    @FXML
    private Button searchLastNameButton;
    @FXML
    private TextField searchLastNameField;
    @FXML
    private Button clearButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button exitButton;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<?, ?> IDTable;
    @FXML
    private TableColumn<?, ?> FirstNameTable;
    @FXML
    private TableColumn<?, ?> LastNameTable;
    @FXML
    private TableColumn<?, ?> NOTable;
    @FXML
    private TableColumn<?, ?> StreetTable;
    @FXML
    private TableColumn<?, ?> CityTable;
    @FXML
    private TableColumn<?, ?> StateTable;
    @FXML
    private TableColumn<?, ?> PostTable;
    @FXML
    private TableColumn<?, ?> PhoneTable;
    
    private CustomerModel customerModel;
    private Validator validator;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the ChoiceBox with the list of states
        StateField.setItems(FXCollections.observableArrayList("NSW", "QLD", "SA", "TAS", "VIC", "WA", "NT", "ACT"));

        // Initialize the CustomerModel and Validator
        customerModel = new CustomerModel();
        validator = new Validator();

        // Initialize the TableView columns
        IDTable.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        FirstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        NOTable.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
        StreetTable.setCellValueFactory(new PropertyValueFactory<>("street"));
        CityTable.setCellValueFactory(new PropertyValueFactory<>("city"));
        StateTable.setCellValueFactory(new PropertyValueFactory<>("state"));
        PostTable.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        PhoneTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load all customers and display them in the table
        refreshCustomerTable();

        // Add listener for table row selection
        customerTable.setOnMouseClicked(this::handleRowSelectAction);
    }

    /**
     * Handles adding or updating a customer when the add/update button is clicked.
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        try {
            // Validate and get inputs
            String firstName = FNameField.getText().trim();
            String lastName = LNameField.getText().trim();
            String phoneNumber = phoneField.getText().trim();
            int streetNumber = Integer.parseInt(StreetNoField.getText().trim());
            String streetName = StreetNameField.getText().trim();
            String city = CityField.getText().trim();
            String state = StateField.getValue();
            int postalCode = Integer.parseInt(PostCodeField.getText().trim());

            // Use InputValidator for validation
            validator.validateName(firstName);
            validator.validateName(lastName);
            validator.validatePhoneNumber(phoneNumber);
            validator.validateStreetNumber(String.valueOf(streetNumber));
            validator.validateStreet(streetName);
            validator.validateCity(city);
            validator.validateState(state);
            validator.validatePostalCode(String.valueOf(postalCode));

            Address address = new Address(streetNumber, streetName, city, state, postalCode);
            Customer customer = new Customer(firstName, lastName, phoneNumber, address);

            if (!IDField.getText().equals("AUTO GENERATED") && !IDField.getText().isEmpty()) {
                // Update existing customer
                customer.setCustomerId(Integer.parseInt(IDField.getText().trim()));
                customerModel.updateCustomer(customer);
                showAlert(Alert.AlertType.INFORMATION, "Customer Updated", "Customer successfully updated!");
            } else {
                // Add new customer
                customerModel.addCustomer(customer);
                showAlert(Alert.AlertType.INFORMATION, "Customer Added", "Customer successfully added!");
            }

            // Refresh the table view
            refreshCustomerTable();

            // Clear input fields
            clearInputFields();
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * Refreshes the customer table view.
     */
    private void refreshCustomerTable() {
        try {
            List<Customer> customers = customerModel.getAllCustomers();
            ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
            customerTable.setItems(customerList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customers: " + e.getMessage());
        }
    }

    /**
     * Clears all input fields.
     */
    private void clearInputFields() {
        FNameField.clear();
        LNameField.clear();
        phoneField.clear();
        StreetNoField.clear();
        StreetNameField.clear();
        CityField.clear();
        StateField.setValue(null);
        PostCodeField.clear();
        IDField.setText("AUTO GENERATED");
        searchPhoneField.clear();
        searchLastNameField.clear();
    }

    /**
     * Shows an alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles search by phone number.
     */
    @FXML
    private void handleSearchPhoneAction(ActionEvent event) {
        String phoneNumber = searchPhoneField.getText().trim();
        if (phoneNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a phone number to search.");
            return;
        }

        try {
            List<Customer> customers = customerModel.searchCustomersByPhone(phoneNumber);
            ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
            customerTable.setItems(customerList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search customers: " + e.getMessage());
        }
    }

    /**
     * Handles search by last name.
     */
    @FXML
    private void handleSearchLastAction(ActionEvent event) {
        String lastName = searchLastNameField.getText().trim();
        if (lastName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a last name to search.");
            return;
        }

        try {
            List<Customer> customers = customerModel.searchCustomersByLastName(lastName);
            ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
            customerTable.setItems(customerList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search customers: " + e.getMessage());
        }
    }

    /**
     * Handles clearing input fields and refreshing the table.
     */
    @FXML
    private void handleClearAction(ActionEvent event) {
        clearInputFields();
        refreshCustomerTable();
    }

    /**
     * Handles navigation to the About Customer scene.
     */
    @FXML
    private void handleAboutAction(ActionEvent event) {
        try {
            Parent aboutCustomerRoot = FXMLLoader.load(getClass().getResource("aboutCustomer.fxml"));
            Stage stage = (Stage) aboutButton.getScene().getWindow();
            stage.setScene(new Scene(aboutCustomerRoot));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load About Customer scene: " + e.getMessage());
        }
    }

    /**
     * Handles navigation to the Login scene.
     */
    @FXML
    private void handleExitAction(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load Login scene: " + e.getMessage());
        }
    }

    /**
     * Handles selection of a row in the customer table.
     */
    @FXML
    private void handleRowSelectAction(MouseEvent event) {
        if (event.getClickCount() == 2 && customerTable.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            fillInputFields(selectedCustomer);
        }
    }

    /**
     * Fills input fields with the details of the selected customer.
     */
    private void fillInputFields(Customer customer) {
        IDField.setText(String.valueOf(customer.getCustomerId()));
        FNameField.setText(customer.getFirstName());
        LNameField.setText(customer.getLastName());
        phoneField.setText(customer.getPhoneNumber());
        StreetNoField.setText(String.valueOf(customer.getAddress().getStreetNumber()));
        StreetNameField.setText(customer.getAddress().getStreet());
        CityField.setText(customer.getAddress().getCity());
        StateField.setValue(customer.getAddress().getState());
        PostCodeField.setText(String.valueOf(customer.getAddress().getPostalCode()));
    }
}
