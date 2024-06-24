package com.ais.model;

import java.io.Serializable;

public class AdminModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String fullName;
    private String address;
    private String phoneNo;
    private String email;
    private String userName;
    private String password;
    private String level;
    private String branch;

    // Constructors, getters, setters
    public AdminModel(int id, String fullName, String address, String phoneNo, String email, String username, String password, String level, String branch) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = username;
        this.password = password;
        this.level = level;
        this.branch = branch;
    }

    public AdminModel(String fullName, String address, String phoneNo, String email, String username, String password, String level, String branch) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = username;
        this.password = password;
        this.level = level;
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", fullName=" + fullName + ", address=" + address + ", phoneNo=" + phoneNo + ", email=" + email + ", userName=" + userName + ", password=" + password + ", level=" + level + ", branch=" + branch + '}';
    }
}
