package com.ais.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RecruitModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String fullName;
    private String address;
    private String phoneNo;
    private String email;
    private String userName;
    private String password;
    private String interviewDate;
    private String qualificationLevel;
    private String otp;
    private String department;

    public RecruitModel(String fullName, String address, String phoneNo, String email, String userName, String password, String interviewDate, String qualificationLevel, String department) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.interviewDate = interviewDate;
        this.qualificationLevel = qualificationLevel;
        this.department = department;
    }

    public RecruitModel(int id, String fullName, String address, String phoneNo, String email, String userName, String password, String interviewDate, String qualificationLevel, String department) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.interviewDate = interviewDate;
        this.qualificationLevel = qualificationLevel;
        this.department = department;
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

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.fullName);
        hash = 83 * hash + Objects.hashCode(this.address);
        hash = 83 * hash + Objects.hashCode(this.phoneNo);
        hash = 83 * hash + Objects.hashCode(this.email);
        hash = 83 * hash + Objects.hashCode(this.userName);
        hash = 83 * hash + Objects.hashCode(this.password);
        hash = 83 * hash + Objects.hashCode(this.interviewDate);
        hash = 83 * hash + Objects.hashCode(this.qualificationLevel);
        hash = 83 * hash + Objects.hashCode(this.otp);
        hash = 83 * hash + Objects.hashCode(this.department);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RecruitModel other = (RecruitModel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phoneNo, other.phoneNo)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.interviewDate, other.interviewDate)) {
            return false;
        }
        if (!Objects.equals(this.qualificationLevel, other.qualificationLevel)) {
            return false;
        }
        if (!Objects.equals(this.otp, other.otp)) {
            return false;
        }
        return Objects.equals(this.department, other.department);
    }

    @Override
    public String toString() {
        return "RecruitModel{" + "id=" + id + ", fullName=" + fullName + ", address=" + address + ", phoneNo=" + phoneNo + ", email=" + email + ", userName=" + userName + ", password=" + password + ", interviewDate=" + interviewDate + ", qualificationLevel=" + qualificationLevel + ", otp=" + otp + ", department=" + department + '}';
    }

}
