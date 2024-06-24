/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais.client;

import com.ais.client.constraint.Constraints;
import com.ais.model.AdminModel;
import com.ais.model.ManagementModel;
import com.ais.model.RecruitModel;
import com.ais.util.Session;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12213436
 */
public class LoginController implements Initializable {

    @FXML
    private Button RegisterButton;

    @FXML
    private ComboBox<String> cmbUserType;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    private Socket socket = null;
    private ObjectInputStream in = null;
    private DataOutputStream out = null;
    private ObjectOutputStream outObj = null;

    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        String selectedUserType = cmbUserType.getValue();
        if (null != selectedUserType) {
            socket = new Socket(Constraints.SERVER_URL, Constraints.SERVER_PORT);
            out = new DataOutputStream(socket.getOutputStream());
            outObj = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            switch (selectedUserType) {
                case "Admin":
                    try {
                        AdminModel authenticate = new AdminModel(txtUserName.getText(), txtPassword.getText());
                        out.writeUTF("LOGIN_ADMIN");
                        out.flush();
                        outObj.writeObject(authenticate);
                        outObj.flush();
                        AdminModel admin = (AdminModel) in.readObject();
                        if (null != admin) {
                            Session.getInstance().setAdmin(admin);
                            App.setRoot("AdminPanel");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Management":
                    try {
                        ManagementModel authenticate = new ManagementModel(txtUserName.getText(), txtPassword.getText());
                        out.writeUTF("LOGIN_MANAGEMENT");
                        out.flush();
                        outObj.writeObject(authenticate);
                        outObj.flush();
                        ManagementModel management = (ManagementModel) in.readObject();
                        if (null != management) {
                            Session.getInstance().setManagement(management);
                            App.setRoot("ManagementPanel");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Recruit":
                    try {
                        RecruitModel authenticate = new RecruitModel(txtUserName.getText(), txtPassword.getText());
                        out.writeUTF("LOGIN_RECRUIT");
                        out.flush();
                        outObj.writeObject(authenticate);
                        outObj.flush();
                        RecruitModel recruit = (RecruitModel) in.readObject();
                        if (null != recruit) {
                            Session.getInstance().setRecruit(recruit);
                            App.setRoot("RecruitsPanel");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    void registerHandler(ActionEvent event) throws IOException {
        App.setRoot("Register");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] qualifications = {"Admin", "Management", "Recruit"};

        // Set the items to the ComboBox
        cmbUserType.setItems(FXCollections.observableArrayList(qualifications));
    }

}
