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
@Table(name = "RATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rates.findAll", query = "SELECT r FROM Rates r")
    , @NamedQuery(name = "Rates.findById", query = "SELECT r FROM Rates r WHERE r.id = :id")
    , @NamedQuery(name = "Rates.findByLowRate", query = "SELECT r FROM Rates r WHERE r.lowRate = :lowRate")
    , @NamedQuery(name = "Rates.findByMediumRate", query = "SELECT r FROM Rates r WHERE r.mediumRate = :mediumRate")
    , @NamedQuery(name = "Rates.findByHighRate", query = "SELECT r FROM Rates r WHERE r.highRate = :highRate")
    , @NamedQuery(name = "Rates.findByTaxRate", query = "SELECT r FROM Rates r WHERE r.taxRate = :taxRate")
    , @NamedQuery(name = "Rates.findByAdminRate", query = "SELECT r FROM Rates r WHERE r.adminRate = :adminRate")})
public class Rates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOW_RATE")
    private double lowRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEDIUM_RATE")
    private double mediumRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIGH_RATE")
    private double highRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAX_RATE")
    private double taxRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADMIN_RATE")
    private double adminRate;

    public Rates() {
    }

    public Rates(Integer id) {
        this.id = id;
    }

    public Rates(Integer id, double lowRate, double mediumRate, double highRate, double taxRate, double adminRate) {
        this.id = id;
        this.lowRate = lowRate;
        this.mediumRate = mediumRate;
        this.highRate = highRate;
        this.taxRate = taxRate;
        this.adminRate = adminRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLowRate() {
        return lowRate;
    }

    public void setLowRate(double lowRate) {
        this.lowRate = lowRate;
    }

    public double getMediumRate() {
        return mediumRate;
    }

    public void setMediumRate(double mediumRate) {
        this.mediumRate = mediumRate;
    }

    public double getHighRate() {
        return highRate;
    }

    public void setHighRate(double highRate) {
        this.highRate = highRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getAdminRate() {
        return adminRate;
    }

    public void setAdminRate(double adminRate) {
        this.adminRate = adminRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rates)) {
            return false;
        }
        Rates other = (Rates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mohawkcollege.tn.Rates[ id=" + id + " ]";
    }
    
}
