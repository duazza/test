package com.mycompany.qproperty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainScreenController implements Initializable {

    @FXML
    private TabPane tabPane; 

    @FXML
    private Tab MainTab;
    @FXML
    private Tab customerTab;
    @FXML
    private Tab PropertyTab;
    @FXML
    private Tab RepairTab;
    @FXML
    private Tab ReportingTab;

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
    @FXML
    private TextField propertyIdField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField agentNameField;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField numBathrooms;
    @FXML
    private Button addPropertyButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<?> propertyTable;
    @FXML
    private TableColumn<?, ?> propertyIdCol;
    @FXML
    private TableColumn<?, ?> streetNumCol;
    @FXML
    private TableColumn<?, ?> streetCol;
    @FXML
    private TableColumn<?, ?> cityCol;
    @FXML
    private TableColumn<?, ?> stateCol;
    @FXML
    private TableColumn<?, ?> postalCol;
    @FXML
    private TableColumn<?, ?> numBedroomsCol;
    @FXML
    private TableColumn<?, ?> numBathroomsCol;
    @FXML
    private TableColumn<?, ?> agentCol;
    @FXML
    private TableColumn<?, ?> typeCol;
    @FXML
    private TableColumn<?, ?> customerIdCol;
    @FXML
    private DatePicker buildYearDatePicker;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField numBedrooms;
    @FXML
    private TextField carPark;
    @FXML
    private ChoiceBox<?> propertyTypeChoiceBox;
    @FXML
    private ChoiceBox<?> stateChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Any necessary initialization can go here
    }

    @FXML
    private void handleCustomerAction(ActionEvent event) {
        tabPane.getSelectionModel().select(customerTab);
    }

    @FXML
    private void handlePropertyAction(ActionEvent event) {
        tabPane.getSelectionModel().select(PropertyTab);
    }

    @FXML
    private void handleRepairAction(ActionEvent event) {
        tabPane.getSelectionModel().select(RepairTab);
    }

    @FXML
    private void handleReportingAction(ActionEvent event) {
        tabPane.getSelectionModel().select(ReportingTab);
    }
    @FXML
    private void handleAboutAction(ActionEvent event) {
        try {
            Parent aboutMainRoot = FXMLLoader.load(getClass().getResource("aboutMain.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(aboutMainRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleAddPropertyAction(ActionEvent event) {
    }

    @FXML
    private void searchPropertyButton(ActionEvent event) {
    }

    @FXML
    private void clearButton(ActionEvent event) {
    }

    @FXML
    private void aboutButton(ActionEvent event) {
    }

    @FXML
    private void exitButton(ActionEvent event) {
    }
}
