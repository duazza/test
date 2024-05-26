package com.mycompany.qproperty;

import com.mycompany.qproperty.InputValidator.InvalidInputException;
import com.mycompany.qproperty.InputValidator.Validator;
import com.mycompany.qproperty.Model.Address;
import com.mycompany.qproperty.Model.AddressModel;
import com.mycompany.qproperty.Model.Property;
import com.mycompany.qproperty.Model.PropertyModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;

public class PropertyController {

    // Validator and model objects for backend interaction.
    Validator validator = new Validator();
    AddressModel addressModel = new AddressModel();
    PropertyModel propertyModel = new PropertyModel();

    // Property fields
    @FXML
    TextField propertyIdField;
    @FXML
    TextField customerIdField;
    @FXML
    TextField agentNameField;
    @FXML
    TextField numBedrooms;
    @FXML
    TextField numBathrooms;
    @FXML
    TextField carPark;
    @FXML
    DatePicker buildYearDatePicker;
    @FXML
    ChoiceBox<String> propertyTypeChoiceBox;
    @FXML
    TextField descriptionField;
    @FXML
    TextField searchField;

    // Address fields
    @FXML
    TextField streetNumberField;
    @FXML
    TextField streetNameField;
    @FXML
    TextField cityField;
    @FXML
    ChoiceBox<String> stateChoiceBox;
    @FXML
    TextField postalCodeField;

    // Table fields
    @FXML
    TableView<Property> propertyTable;
    @FXML
    TableColumn<Property, Integer> propertyIdCol;
    @FXML
    TableColumn<Property, Integer> streetNumCol;
    @FXML
    TableColumn<Property, String> streetCol;
    @FXML
    TableColumn<Property, String> cityCol;
    @FXML
    TableColumn<Property, String> stateCol;
    @FXML
    TableColumn<Property, Integer> postalCol;
    @FXML
    TableColumn<Property, Integer> numBedroomsCol;
    @FXML
    TableColumn<Property, Integer> numBathroomsCol;
    @FXML
    TableColumn<Property, String> agentCol;
    @FXML
    TableColumn<Property, String> typeCol;
    @FXML
    TableColumn<Property, Integer> customerIdCol;

    Property selectedProperty; // Currently selected property from the table
    @FXML
    private Button customerButton;
    @FXML
    private Button propertyButton;
    @FXML
    private Button repairButton;
    @FXML
    private Button reportingButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button exitButton;

    // Search button. Searches for a property based on street name or number
    @FXML
    public void searchPropertyButton() {
        String searchTerm = searchField.getText();
        try {
            // Assign properties to the table
            validator.validateSearch(searchTerm);
            List<Property> properties = propertyModel.searchProperties(searchTerm);
            propertyTable.setItems(FXCollections.observableList(properties));

            setColumns(); // set table columns to match searched properties

            // TODO change this to get customer object attribute then customer id after customer class is made
            customerIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());
        } catch (SQLException | InvalidInputException e) {
            // Shows any errors to the user
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    // Adds a new property and its associated address
    @FXML
    public void addPropertyButton() {
        try {
            validatePropertyInput();

            // Create address and property objects from input fields
            Address address = createAddress();
            Property property = createProperty();

            if (selectedProperty != null) { // Button updates the property if there is one selected from the table

                int addressID = selectedProperty.getAddress().getAddressID();
                int propertyId = selectedProperty.getPropertyId();

                address.setAddressID(addressID);
                property.setPropertyId(propertyId);
                property.setAddress(address);

                addressModel.updateAddress(address);
                propertyModel.updateProperty(property);

                refreshTable();
                JOptionPane.showMessageDialog(null, "Successfully updated property");
            } else { // If there's no selection, add a new property and its associated address

                int addressId = addressModel.addAddress(address); // Insert the address object first, then get its generated ID key
                address.setAddressID(addressId); // Assign the newly made address key to the address object
                property.setAddress(address);   // Assign the address object to the property
                propertyModel.addProperty(property); // Add the property to the DB

                refreshTable();
                JOptionPane.showMessageDialog(null, "Successfully added property");
            }
        } catch (InvalidInputException | SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    // Creates an address from input, to be used for DB operations
    private Address createAddress () {
        Address address = new Address();

        // Assign address attributes from input fields
        address.setCity(cityField.getText());
        address.setStreetNumber(Integer.parseInt(streetNumberField.getText()));
        address.setStreet(streetNameField.getText());
        address.setState(stateChoiceBox.getValue());
        address.setStreet(streetNameField.getText());
        address.setPostalCode(Integer.parseInt(postalCodeField.getText()));

        return address;
    }

    // Creates a property from input, to be used for DB operations
    private Property createProperty () {
        Property property = new Property();

        // Assign property attributes from input fields
        property.setDescription(descriptionField.getText());
        property.setBedrooms(Integer.parseInt(numBedrooms.getText()));
        property.setBathrooms(Integer.parseInt(numBathrooms.getText()));
        property.setCarPark(Integer.parseInt(carPark.getText()));
        property.setYearBuilt(Date.valueOf(buildYearDatePicker.getValue()));
        property.setPropertyType(propertyTypeChoiceBox.getValue());
        property.setAgentName(agentNameField.getText());
        property.setCustomerId(Integer.parseInt(customerIdField.getText()));

        return property;
    }

    /**
     * Validates all input fields for the add/update property button.
     * Validates fields from top to bottom as shown in the UI.
     *
     * @throws InvalidInputException
     */
    public void validatePropertyInput() throws InvalidInputException {
        validator.validateStreetNumber(streetNumberField.getText());
        validator.validateStreet(streetNameField.getText());
        validator.validateCity(cityField.getText());
        validator.validateState(stateChoiceBox.getValue());
        validator.validatePostalCode(postalCodeField.getText());
        validator.validateName(agentNameField.getText());
        validator.validateRooms(numBedrooms.getText());
        validator.validateRooms(numBathrooms.getText());
        validator.validateCarPark(carPark.getText());
        validator.validateDescription(descriptionField.getText());
        validator.validatePropertyType(propertyTypeChoiceBox.getValue());
    }

    // Gets all properties from the DB, then applies them to the table.
    private void refreshTable () throws SQLException {
        List<Property>  properties = propertyModel.getAllProperties();
        propertyTable.setItems(FXCollections.observableList(properties));
        setColumns();
    }

    // Assigns data to table columns.
    private void setColumns () {

        propertyIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPropertyId()).asObject());
        streetNumCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAddress().getStreetNumber()).asObject());
        streetCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getStreet()));
        cityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
        stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getState()));
        postalCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAddress().getPostalCode()).asObject());
        numBedroomsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBedrooms()).asObject());
        numBathroomsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBathrooms()).asObject());
        agentCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAgentName()));
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPropertyType()));
    }

    // Clears all fields
    @FXML
    public void clearButton() {
        propertyIdField.clear();
        customerIdField.clear();
        agentNameField.clear();
        numBedrooms.clear();
        numBathrooms.clear();
        carPark.clear();
        buildYearDatePicker.getEditor().clear();
        descriptionField.clear();
        searchField.clear();
        streetNumberField.clear();
        streetNameField.clear();
        cityField.clear();
        postalCodeField.clear();
        propertyTable.setItems(null);

        if (selectedProperty != null) {
            selectedProperty = null;
        }
    }

    // Initialise & update fields
    public void initialize() {
        // Set choice box values
        propertyTypeChoiceBox.getItems().addAll("house", "apartment");
        propertyTypeChoiceBox.setValue("house");
        stateChoiceBox.getItems().addAll("NSW", "QLD", "SA", "TAS", "VIC", "WA");
        stateChoiceBox.setValue("NSW");
        buildYearDatePicker.setEditable(false);
        propertyIdField.setEditable(false);

        // Set table columns
        propertyTable.getColumns().setAll(
                propertyIdCol,
                streetNumCol,
                streetCol,
                cityCol,
                stateCol,
                postalCol,
                numBedroomsCol,
                numBathroomsCol,
                agentCol,
                typeCol,
                customerIdCol
        );

        // Populate the table with all existing property records
        try {
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Set the selection mode for the table to single selections only
        TableView.TableViewSelectionModel<Property> selectionModel = propertyTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        // Add a listener for the selection. When an item is selected, it updates all corresponding property input fields
        selectionModel.selectedItemProperty().addListener(
                (selection) -> {
                    selectedProperty = selectionModel.getSelectedItem();
                    propertyIdField.setText(String.valueOf(selectedProperty.getPropertyId()));
                    streetNumberField.setText(String.valueOf(selectedProperty.getAddress().getStreetNumber()));
                    streetNameField.setText(selectedProperty.getAddress().getStreet());
                    cityField.setText(selectedProperty.getAddress().getCity());
                    stateChoiceBox.setValue(selectedProperty.getAddress().getState());
                    postalCodeField.setText(String.valueOf(selectedProperty.getAddress().getPostalCode()));
                    agentNameField.setText(selectedProperty.getAgentName());
                    numBedrooms.setText(String.valueOf(selectedProperty.getBedrooms()));
                    numBathrooms.setText(String.valueOf(selectedProperty.getBathrooms()));
                    carPark.setText(String.valueOf(selectedProperty.getCarPark()));
                    buildYearDatePicker.setValue(selectedProperty.getYearBuilt().toLocalDate());
                    descriptionField.setText(selectedProperty.getDescription());
                    customerIdField.setText(String.valueOf(selectedProperty.getCustomerId()));

                }
        );

    }

    @FXML
    public void exitButton () {
        Platform.exit();
    }

    @FXML
    public void aboutButton () throws IOException {
        App.setRoot("about - property");
    }

    @FXML
    private void handleCustomerAction(ActionEvent event) {
    }

    @FXML
    private void handlePropertyAction(ActionEvent event) {
    }

    @FXML
    private void handleRepairAction(ActionEvent event) {
    }

    @FXML
    private void handleReportingAction(ActionEvent event) {
    }

    @FXML
    private void handleAboutAction(ActionEvent event) {
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
    }

}
