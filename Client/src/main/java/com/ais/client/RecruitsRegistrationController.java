/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais.client;

import com.ais.client.constraint.Constraints;
import com.ais.model.RecruitModel;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12213436
 */
public class RecruitsRegistrationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<String> cmbQualification;

    @FXML
    private DatePicker dtPkInterviewDate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtUserName;

    private Socket socket = null;
    private ObjectInputStream in = null;
    private DataOutputStream out = null;
    private ObjectOutputStream outObj = null;

    @FXML
    void saveHandler(ActionEvent event) throws IOException {
        try {
            socket = new Socket(Constraints.SERVER_URL, Constraints.SERVER_PORT);
            out = new DataOutputStream(socket.getOutputStream());
            outObj = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeUTF("REGISTER");
            out.flush();

            RecruitModel recruit = new RecruitModel(txtFullName.getText(), txtAddress.getText(),
                    txtPhoneNo.getText(), txtEmail.getText(), txtUserName.getText(),
                    txtPassword.getText(), dtPkInterviewDate.getValue().format(DateTimeFormatter.ISO_DATE),
                    cmbQualification.getValue(), null);

            outObj.writeObject(recruit);
            outObj.flush();
        } finally {
            if (in != null) {
                in.close();
            }
            if (outObj != null) {
                outObj.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        }

        App.setRoot("Login");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] qualifications = {"Bachelors", "Masters", "PhD"};

        // Set the items to the ComboBox
        cmbQualification.setItems(FXCollections.observableArrayList(qualifications));
    }

}
