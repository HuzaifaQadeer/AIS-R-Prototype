package com.ais.model;

import java.io.Serializable;
import java.util.Objects;

public class DashboardDTO implements Serializable{
    private String department;
    private int noOfRecruits;

    public DashboardDTO() {
    }

    public DashboardDTO(String department, int noOfRecruits) {
        this.department = department;
        this.noOfRecruits = noOfRecruits;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getNoOfRecruits() {
        return noOfRecruits;
    }

    public void setNoOfRecruits(int noOfRecruits) {
        this.noOfRecruits = noOfRecruits;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.department);
        hash = 37 * hash + this.noOfRecruits;
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
        final DashboardDTO other = (DashboardDTO) obj;
        if (this.noOfRecruits != other.noOfRecruits) {
            return false;
        }
        return Objects.equals(this.department, other.department);
    }

    @Override
    public String toString() {
        return "DashboardDTO{" + "department=" + department + ", noOfRecruits=" + noOfRecruits + '}';
    }
}
