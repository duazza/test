/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 *
 * @author tqt13
 */
public class AboutPropertyController {

    @FXML
    public void switchToPropertyWindow() throws IOException {
        App.setRoot("mainScreen");
    }
}
