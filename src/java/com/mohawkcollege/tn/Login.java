/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thom
 * 
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    @PersistenceContext(unitName = "WaterBillingFinalPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public Login() {
    }

    private String meterID;

    /**
     * Get the value of meterID
     *
     * @return the value of meterID
     */
    public String getMeterID() {
        return meterID;
    }

    /**
     * Set the value of meterID
     *
     * @param meterID new value of meterID
     */
    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    private String role;

    /**
     * Get the value of role
     *
     * @return the value of role
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the value of role
     *
     * @param role new value of role
     */
    public void setRole(String role) {
        this.role = role;
    }

    protected boolean isAdmin;

    /**
     * Get the value of isAdmin
     *
     * @return the value of isAdmin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    protected boolean loggedin;

    /**
     * Get the value of loggedin
     *
     * @return the value of loggedin
     */
    public boolean isLoggedin() {
        return loggedin;
    }

    private String username;

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private String loginErrorMessage = "";

    /**
     * Get login error message
     *
     * @return
     */
    public String getLoginErrorMessage() {
        return this.loginErrorMessage;
    }

    private boolean hasError = false;

    public String commenceLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        Registration registerClass = new Registration();
        List<Users> credentialsMatch = em.createNamedQuery("Users.findByUsername").setParameter("username", this.username).getResultList();

        if ((credentialsMatch.isEmpty())) {
            this.loginErrorMessage = "Incorrect username or password";
            this.hasError = true;
        } else {
            if ((credentialsMatch.get(0).getPassword().equals(registerClass.encryptPassword(this.password)))) {
                this.loggedin = true;
                this.role = credentialsMatch.get(0).getMemberrole();

                this.meterID = credentialsMatch.get(0).getMeterid();

                if (this.role.equals("admin")) {
                    this.isAdmin = true;
                    return "index?faces-redirect=true";
                } else if (this.role.equals("user")) {
                    return "getdata?faces-redirect=true";
                } else {
                    return "login?faces-redirect=true";
                }
            } else {
                this.loginErrorMessage = "Incorrect username or password";
                this.hasError = true;
            }
        }
        return "";
    }

    public String logout() {
        loggedin = false;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();

        return "loggedout";
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}