/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais.client;

import com.ais.client.constraint.Constraints;
import com.ais.model.DashboardDTO;
import com.ais.model.ManagementModel;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 12213436
 */
public class ManangementPanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane AddRecruitsPane;

    @FXML
    private Pane DashboardPane;

    @FXML
    private Pane ManagementPane;

    @FXML
    private Pane ReportPane;

    @FXML
    private Pane UpdateRecruitsPane;

    @FXML
    private Button add_recruits;

    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private Button dashboard;

    @FXML
    private Button management;

    @FXML
    private Button report;

    @FXML
    private Label lblAerospace;

    @FXML
    private Label lblElectronic;

    @FXML
    private Label lblMechanical;

    @FXML
    private Label lblSoftware;

    @FXML
    private ComboBox<String> cmbSort;

    @FXML
    private Button update_recruits;

    @FXML
    private TextField txtAddAddress;

    @FXML
    private TextField txtAddEmail;

    @FXML
    private TextField txtAddFullName;

    @FXML
    private TextField txtAddPassword;

    @FXML
    private TextField txtAddPhoneNo;

    @FXML
    private TextField txtAddUserName;

    @FXML
    private DatePicker dtPkAddInteviewDate;

    @FXML
    private ComboBox<String> cmbAddQualification;

    @FXML
    private TextField txtUpdAddress;

    @FXML
    private TextField txtUpdEmail;

    @FXML
    private TextField txtUpdFullName;

    @FXML
    private TextField txtUpdPassword;

    @FXML
    private TextField txtUpdPhoneNo;

    @FXML
    private TextField txtUpdSearchUserName;

    @FXML
    private TextField txtUpdUserName;

    @FXML
    private DatePicker dtPkUpdInterviewDate;

    @FXML
    private ComboBox<String> cmbUpdQualification;

    @FXML
    private TableView<RecruitModel> tblRecruit;

    @FXML
    private TableColumn<RecruitModel, String> clmAddress;

    @FXML
    private TableColumn<RecruitModel, String> clmBranch;

    @FXML
    private TableColumn<RecruitModel, String> clmContact;

    @FXML
    private TableColumn<RecruitModel, String> clmEmail;

    @FXML
    private TableColumn<RecruitModel, Integer> clmId;

    @FXML
    private TableColumn<RecruitModel, String> clmName;

    @FXML
    private TableColumn<RecruitModel, String> clmPosition;

    @FXML
    private TableView<ManagementModel> tblManagement;

    @FXML
    private TableColumn<ManagementModel, String> clmMgmAddress;

    @FXML
    private TableColumn<ManagementModel, String> clmMgmContact;

    @FXML
    private TableColumn<ManagementModel, String> clmMgmEmail;

    @FXML
    private TableColumn<ManagementModel, Integer> clmMgmId;

    @FXML
    private TableColumn<ManagementModel, String> clmMgmName;

    @FXML
    private TableColumn<ManagementModel, String> clmMgmPosition;

    private Socket socket = null;
    private ObjectInputStream in = null;
    private DataOutputStream out = null;
    private ObjectOutputStream outObj = null;
    private int recruitId;
    private int managementId;
    private int adminId;

    @FXML
    void handleNav(ActionEvent event) {
        if (event.getSource() == dashboard) {
            DashboardPane.toFront();
        } else if (event.getSource() == management) {
            ManagementPane.toFront();
        } else if (event.getSource() == report) {
            ReportPane.toFront();
        } else if (event.getSource() == add_recruits) {
            AddRecruitsPane.toFront();
        } else if (event.getSource() == update_recruits) {
            UpdateRecruitsPane.toFront();
        }
    }

    @FXML
    void handleSort(ActionEvent event) throws IOException, ClassNotFoundException {
        initSocket();
        if (cmbSort.getValue().equals("Lastname Dec and Groupby Locaton")) {
            out.writeUTF("GET_RECRUITS_ORDER_BY_FULLNAME");
        } else {
            out.writeUTF("GET_RECRUITS_ORDER_BY_QUALIFICATION");
        }
        out.flush();
        outObj.writeObject(new RecruitModel());
        outObj.flush();
        List<RecruitModel> recruits = (List<RecruitModel>) in.readObject();
        tblRecruit.getItems().clear();
        if (recruits != null) {
            mapRecuitTableColumns(recruits);
        }
    }

    @FXML
    void saveRecruitHandler(ActionEvent event) throws IOException {
        try {
            initSocket();

            out.writeUTF("REGISTER");
            out.flush();

            RecruitModel recruit = new RecruitModel(txtAddFullName.getText(), txtAddAddress.getText(),
                    txtAddPhoneNo.getText(), txtAddEmail.getText(), txtAddUserName.getText(),
                    txtAddPassword.getText(), dtPkAddInteviewDate.getValue().format(DateTimeFormatter.ISO_DATE),
                    cmbAddQualification.getValue(), null);

            outObj.writeObject(recruit);
            outObj.flush();

            resetAddRecruit();

        } finally {
            finalCall();
        }
    }

    @FXML
    void searchRecruitHandler(ActionEvent event) throws IOException {
        try {
            RecruitModel authenticate = new RecruitModel(txtUpdSearchUserName.getText());
            initSocket();
            out.writeUTF("GET_RECRUIT_USER_NAME");
            out.flush();
            outObj.writeObject(authenticate);
            outObj.flush();
            RecruitModel recruit = (RecruitModel) in.readObject();
            if (recruit != null) {
                recruitId = recruit.getId();
                txtUpdFullName.setText(recruit.getFullName());
                txtUpdAddress.setText(recruit.getAddress());
                txtUpdUserName.setText(recruit.getUserName());
                txtUpdPassword.setText(recruit.getPassword());
                txtUpdPhoneNo.setText(recruit.getPhoneNo());
                txtUpdEmail.setText(recruit.getEmail());
                LocalDate datetime = LocalDate.parse(recruit.getInterviewDate(),
                        DateTimeFormatter.ofPattern("d/MM/yyyy"));
                dtPkUpdInterviewDate.setValue(datetime);
                cmbUpdQualification.setValue(recruit.getQualificationLevel());
                cmbDepartment.setValue(recruit.getDepartment());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            finalCall();
        }
    }

    @FXML
    void updateRecruitHandler(ActionEvent event) throws IOException {
        try {
            initSocket();

            out.writeUTF("UPDATE");
            out.flush();

            RecruitModel recruit = new RecruitModel(recruitId, txtUpdFullName.getText(), txtUpdAddress.getText(),
                    txtUpdPhoneNo.getText(), txtUpdEmail.getText(), txtUpdUserName.getText(),
                    txtUpdPassword.getText(), dtPkUpdInterviewDate.getValue().format(DateTimeFormatter.ISO_DATE),
                    cmbUpdQualification.getValue(), cmbDepartment.getValue());

            outObj.writeObject(recruit);
            outObj.flush();

            resetUpdateRecruit();
        } finally {
            finalCall();
        }
    }

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        recruitId = 0;
        managementId = 0;
        App.setRoot("Login");
        Session.getInstance().setAdmin(null);
        Session.getInstance().setManagement(null);
        Session.getInstance().setRecruit(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] sortings = {"Lastname Dec and Groupby Locaton", "Highest level of Qualification"};
        String[] departments = {"Software", "Aerospace", "Mechanical", "Electronics Engineering"};
        String[] qualifications = {"Bachelors", "Masters", "PhD"};

        // Set the items to the ComboBox
        cmbAddQualification.setItems(FXCollections.observableArrayList(qualifications));
        cmbUpdQualification.setItems(FXCollections.observableArrayList(qualifications));
        // Set the items to the ComboBox
        cmbSort.setItems(FXCollections.observableArrayList(sortings));
        cmbDepartment.setItems(FXCollections.observableArrayList(departments));

        try {
            loadRecruits();
            loadManagement();
            loadDashBoard();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void loadDashBoard() throws IOException, ClassNotFoundException {
        initSocket();
        out.writeUTF("MANAGEMENTS_DASHBOARD");
        out.flush();
        outObj.writeObject(new DashboardDTO());
        outObj.flush();
        List<DashboardDTO> recruits = (List<DashboardDTO>) in.readObject();

        if (recruits != null) {
            mapManagmentDashboardValues(recruits);
        }
    }

    private void loadRecruits() throws IOException, ClassNotFoundException {
        initSocket();
        out.writeUTF("GET_RECRUITS");
        out.flush();
        outObj.writeObject(new RecruitModel());
        outObj.flush();
        List<RecruitModel> recruits = (List<RecruitModel>) in.readObject();

        if (recruits != null) {
            mapRecuitTableColumns(recruits);
        }
    }

    private void loadManagement() throws IOException, ClassNotFoundException {
        initSocket();
        out.writeUTF("GET_MANAGEMENTS");
        out.flush();
        outObj.writeObject(new ManagementModel());
        outObj.flush();
        List<ManagementModel> managments = (List<ManagementModel>) in.readObject();

        if (managments != null) {
            mapManagementTableColumns(managments);
        }
    }

    private void mapManagmentDashboardValues(List<DashboardDTO> values) {
        int noOfRecruits;
        for (DashboardDTO value : values) {
            noOfRecruits = value.getNoOfRecruits();
            if (value.getDepartment().equalsIgnoreCase("Software")) {
                lblSoftware.setText(String.valueOf(noOfRecruits));
            } else if (value.getDepartment().equalsIgnoreCase("Aerospace")) {
                lblAerospace.setText(String.valueOf(noOfRecruits));
            } else if (value.getDepartment().equalsIgnoreCase("Mechanical")) {
                lblMechanical.setText(String.valueOf(noOfRecruits));
            } else if (value.getDepartment().equalsIgnoreCase("Electronics Engineering")) {
                lblElectronic.setText(String.valueOf(noOfRecruits));
            }
        }

    }

    private void mapRecuitTableColumns(List<RecruitModel> recruits) {
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmBranch.setCellValueFactory(new PropertyValueFactory<>("department"));
        clmPosition.setCellValueFactory(new PropertyValueFactory<>("qualificationLevel"));
        tblRecruit.getItems().addAll(recruits);
    }

    private void mapManagementTableColumns(List<ManagementModel> managments) {
        clmMgmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmMgmName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        clmMgmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmMgmContact.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        clmMgmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmMgmPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        tblManagement.getItems().addAll(managments);
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

    private void resetUpdateRecruit() {
        txtUpdFullName.setText(null);
        txtUpdAddress.setText(null);
        txtUpdPhoneNo.setText(null);
        txtUpdEmail.setText(null);
        txtUpdUserName.setText(null);
        txtUpdPassword.setText(null);
        dtPkUpdInterviewDate.setValue(null);
        cmbUpdQualification.setValue("Highest Qualification Level");
        cmbDepartment.setValue("Department");
    }

    private void resetAddRecruit() {
        txtAddFullName.setText(null);
        txtAddAddress.setText(null);
        txtAddPhoneNo.setText(null);
        txtAddEmail.setText(null);
        txtAddUserName.setText(null);
        txtAddPassword.setText(null);
        dtPkAddInteviewDate.setValue(null);
        cmbAddQualification.setValue("Highest Qualification Level");
    }
}
