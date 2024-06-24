/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais.client;

import com.ais.client.constraint.Constraints;
import com.ais.model.RecruitModel;
import com.ais.util.Session;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
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
public class RecruitsPanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private ComboBox<String> cmbQualification;

    @FXML
    private DatePicker dtPkInvterviewDate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtOtp;

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
    private int recruitId;
    private String otp;

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        App.setRoot("Login");
        Session.getInstance().setAdmin(null);
        Session.getInstance().setManagement(null);
        Session.getInstance().setRecruit(null);
        recruitId = 0;
        otp = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] departments = {"Software", "Aerospace", "Mechanical", "Electronics Engineering"};
        String[] qualifications = {"Bachelors", "Masters", "PhD"};

        cmbDepartment.setItems(FXCollections.observableArrayList(departments));
        cmbQualification.setItems(FXCollections.observableArrayList(qualifications));

        RecruitModel loggedInRecruit = Session.getInstance().getRecruit();
        txtFullName.setText(loggedInRecruit.getFullName());
        txtAddress.setText(loggedInRecruit.getAddress());
        txtUserName.setText(loggedInRecruit.getUserName());
        txtPassword.setText(loggedInRecruit.getPassword());
        txtPhoneNo.setText(loggedInRecruit.getPhoneNo());
        txtEmail.setText(loggedInRecruit.getEmail());
        LocalDate datetime = LocalDate.parse(loggedInRecruit.getInterviewDate(),
                DateTimeFormatter.ofPattern("d/MM/yyyy"));
        dtPkInvterviewDate.setValue(datetime);
        cmbQualification.setValue(loggedInRecruit.getQualificationLevel());
        cmbDepartment.setValue(loggedInRecruit.getDepartment());
        recruitId = loggedInRecruit.getId();
        otp = loggedInRecruit.getOtp();
    }

    @FXML
    void updateRecruitHandler(ActionEvent event) throws IOException {
        try {
            if (otp.equals(txtOtp.getText())) {
                initSocket();

                out.writeUTF("UPDATE");
                out.flush();

                RecruitModel recruit = new RecruitModel(recruitId, txtFullName.getText(), txtAddress.getText(),
                        txtPhoneNo.getText(), txtEmail.getText(), txtUserName.getText(),
                        txtPassword.getText(), dtPkInvterviewDate.getValue().format(DateTimeFormatter.ISO_DATE),
                        cmbQualification.getValue(), cmbDepartment.getValue());

                outObj.writeObject(recruit);
                outObj.flush();
                resetateRecruit();
            }
        } finally {
            finalCall();
        }
    }

    private void initSocket() throws IOException {
        socket = new Socket(Constraints.SERVER_URL, Constraints.SERVER_PORT);
        out = new DataOutputStream(socket.getOutputStream());
        outObj = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    private void finalCall() throws IOException {
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

    private void resetateRecruit() {
        txtFullName.setText(null);
        txtAddress.setText(null);
        txtPhoneNo.setText(null);
        txtEmail.setText(null);
        txtUserName.setText(null);
        txtPassword.setText(null);
        dtPkInvterviewDate.setValue(null);
        cmbQualification.setValue("Highest Qualification Level");
        cmbDepartment.setValue("Department");
        txtOtp.setText(null);
    }
}
