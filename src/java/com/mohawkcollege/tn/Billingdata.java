/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thom
 * 
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
@Entity
@Table(name = "BILLINGDATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Billingdata.findAll", query = "SELECT b FROM Billingdata b")
    , @NamedQuery(name = "Billingdata.findByBillingId", query = "SELECT b FROM Billingdata b WHERE b.billingId = :billingId")
    , @NamedQuery(name = "Billingdata.findByBillingdate", query = "SELECT b FROM Billingdata b WHERE b.billingdate = :billingdate")
    , @NamedQuery(name = "Billingdata.findByMeterid", query = "SELECT b FROM Billingdata b WHERE b.meterid = :meterid")
    , @NamedQuery(name = "Billingdata.findByReading", query = "SELECT b FROM Billingdata b WHERE b.reading = :reading")})
public class Billingdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BILLING_ID")
    private Integer billingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "BILLINGDATE")
    private String billingdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "METERID")
    private String meterid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "READING")
    private int reading;

    public Billingdata() {
    }

    public Billingdata(Integer billingId) {
        this.billingId = billingId;
    }

    public Billingdata(Integer billingId, String billingdate, String meterid, int reading) {
        this.billingId = billingId;
        this.billingdate = billingdate;
        this.meterid = meterid;
        this.reading = reading;
    }

    public Integer getBillingId() {
        return billingId;
    }

    public void setBillingId(Integer billingId) {
        this.billingId = billingId;
    }

    public String getBillingdate() {
        return billingdate;
    }

    public void setBillingdate(String billingdate) {
        this.billingdate = billingdate;
    }

    public String getMeterid() {
        return meterid;
    }

    public void setMeterid(String meterid) {
        this.meterid = meterid;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billingId != null ? billingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Billingdata)) {
            return false;
        }
        Billingdata other = (Billingdata) object;
        if ((this.billingId == null && other.billingId != null) || (this.billingId != null && !this.billingId.equals(other.billingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mohawkcollege.tn.Billingdata[ billingId=" + billingId + " ]";
    }
    
}
