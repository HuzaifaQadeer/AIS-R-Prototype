/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais.util;

import com.ais.model.AdminModel;
import com.ais.model.ManagementModel;
import com.ais.model.RecruitModel;

/**
 *
 * @author Milan
 */
public class Session {

    private static Session instance;
    private AdminModel admin;
    private ManagementModel management;
    private RecruitModel recruit;

    private Session() {
        // Private constructor to enforce singleton pattern
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public AdminModel getAdmin() {
        return admin;
    }

    public void setAdmin(AdminModel admin) {
        this.admin = admin;
    }

    public ManagementModel getManagement() {
        return management;
    }

    public void setManagement(ManagementModel management) {
        this.management = management;
    }

    public RecruitModel getRecruit() {
        return recruit;
    }

    public void setRecruit(RecruitModel recruit) {
        this.recruit = recruit;
    }

    public void clear() {
        admin = null;
        management = null;
        recruit = null;
    }
}
