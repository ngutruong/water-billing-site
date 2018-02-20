/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Thom
 * 
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
@Named(value = "reg")
@SessionScoped
public class Registration implements Serializable {

    @PersistenceContext(unitName = "WaterBillingFinalPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public Registration() {
    }

    private String registerUsernameError = "";

    /**
     * Get the username error message during registration
     *
     * @return the username error message
     */
    public String getRegisterUsernameError() {
        return registerUsernameError;
    }

    private String registerPasswordError = "";

    /**
     * Get the password error message during registration
     *
     * @return the password error message
     */
    public String getRegisterPasswordError() {
        return registerPasswordError;
    }

    private String registerAddressError = "";

    /**
     * Get the address error message during registration
     *
     * @return the address error message
     */
    public String getRegisterAddressError() {
        return registerAddressError;
    }

    private String username;

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    /**
     * Get password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private String address;

    /**
     * Get address
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private boolean isInvalid = false;
    private int errorCount;
    
    public String encryptPassword(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        final int ITERATION = 1537;
        final String salt = "S&mpl3s&LT";
        
        MessageDigest md = MessageDigest.getInstance("SHA");
        String inputText = new String(salt + pw);
        
        for(int i = 0; i < ITERATION; i++){
            md.update( inputText.getBytes("UTF-8") );
            byte rawByte [] = md.digest();
            inputText = (new BASE64Encoder()).encode(rawByte);
        }
        String hash = inputText;
        
        return hash;
    }

    public String register() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        this.errorCount = 0;
        Users u = new Users();

        List<Addresses> addressesByAddress = em.createNamedQuery("Addresses.findByAddress").setParameter("address", this.address).getResultList();
        List<Users> usersByUsername = em.createNamedQuery("Users.findByUsername").setParameter("username", this.username).getResultList();
        List<Users> usersByAddress = em.createNamedQuery("Users.findByAddress").setParameter("address", this.address).getResultList();

        if (this.username.length() == 0) {
            this.registerUsernameError = "You must enter username";
            this.errorCount++;
        } else {
            this.registerUsernameError = "";
        }
        
        Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*[a-z])(?=.*[A-Z])(?=.*d)(?=.*[!@#$%^&*]).{8,})");
        if (this.password.length() == 0) {
            this.registerPasswordError = "You must enter password";
            this.errorCount++;
        } else {
            if (this.password.length() < 8) {
                this.registerPasswordError = "The password you entered must be longer than 8 characters";
                this.errorCount++;
            } else if (!PASSWORD_PATTERN.matcher(this.password).matches()) {
                this.registerPasswordError = "The password must contain at least 8 characters including an upper case letter, lower case letter, a digit and a special character (!@#$%^&*).";
                this.errorCount++;
            } else {
                this.registerPasswordError = "";
            }
        }

        if (this.address.length() == 0) {
            this.registerAddressError = "Address cannot be empty.";
            this.errorCount++;
        } else {
            this.registerAddressError = "";
        }

        if (usersByUsername.isEmpty()) {
            if (usersByAddress.isEmpty()) {
                if (addressesByAddress.isEmpty()) {
                    this.registerAddressError = "This address is not available.";
                    this.errorCount++;
                } else {
                    this.registerAddressError = "";
                }
            } else {
                this.registerAddressError = "This address has been taken.";
                this.errorCount++;
            }
        } else {
            this.registerUsernameError = "This username has been taken.";
            this.errorCount++;
        }

        if (this.errorCount == 0) {
            this.isInvalid = false;
        } else {
            this.isInvalid = true;
            this.errorCount = 0; //Reset error count
        }

        if (this.isInvalid == false) {
            u.setMemberrole("user");
            u.setUsername(this.username);
            u.setPassword(encryptPassword(this.password));
            u.setAddress(this.address);
            u.setMeterid(addressesByAddress.get(0).getMeterid());

            persist(u);
            return "registered.xhtml";
        }

        return "";
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
