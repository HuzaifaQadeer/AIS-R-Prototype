/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author 12213436
 */
public class RegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button loginButton;

    @FXML
    private Button registerAdminButton;

    @FXML
    private Button registerManagementButton;

    @FXML
    private Button registerRecruitButton;

    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @FXML
    void registerAdminHandler(ActionEvent event) throws IOException {
        App.setRoot("AdminRegistration");
    }

    @FXML
    void registerManagementHandler(ActionEvent event) throws IOException {
        App.setRoot("ManagementRegistration");
    }

    @FXML
    void registerRecruitHandler(ActionEvent event) throws IOException {
        App.setRoot("RecruitsRegistration");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
