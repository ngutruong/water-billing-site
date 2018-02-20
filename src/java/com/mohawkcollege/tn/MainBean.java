/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Thom
 *
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
@Named(value = "springfield")
@SessionScoped
public class MainBean implements Serializable {

    @PersistenceContext(unitName = "WaterBillingFinalPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    @Inject
    private Login user;

    private final boolean isDebugging = true;

    public boolean getDebug() {
        return isDebugging;
    }

    public String toString() {
        String s;
        try {
            Class<?> c = this.getClass();
            s = "[CLASS=" + c.getName() + "]";
            Field fields[] = c.getDeclaredFields();
            for (Field f : fields) {
                Object fieldValue = f.get(this);
                s += "[" + f.getName() + "=" + fieldValue + "]  ";
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
        return s;
    }

    private Locale locale;

    public void switchLanguage(String preferredLanguage) {
        locale = new Locale(preferredLanguage);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    private List<Addresses> addresses;

    /**
     * Get the value of addresses
     *
     * @return the value of addresses
     */
    public List<Addresses> getAddresses() {
        addresses = em.createNamedQuery("Addresses.findAll").getResultList();
        return addresses;
    }

    public String goToData(String meter) {
        user.setMeterID(meter);
        return "getdata??faces-redirect=true";
    }

    private String meterID;
    private List<Billingdata> billingData;

    private double amountUsed;

    /**
     * Get the value of amountUsed
     *
     * @return the value of amountUsed
     */
    public double getAmountUsed() {
        return amountUsed;
    }

    private int lowAmountUsed;

    /**
     * Get the value of lowAmountUsed
     *
     * @return the value of lowAmountUsed
     */
    public int getLowAmountUsed() {
        return lowAmountUsed;
    }

    private int medAmountUsed;

    /**
     * Get the value of medAmountUsed
     *
     * @return the value of medAmountUsed
     */
    public int getMedAmountUsed() {
        return medAmountUsed;
    }

    private int highAmountUsed;

    /**
     * Get the value of highAmountUsed
     *
     * @return the value of highAmountUsed
     */
    public int getHighAmountUsed() {
        return highAmountUsed;
    }

    private double dailyHouseholdAverage;

    /**
     * Get the value of dailyHouseholdAverage
     *
     * @return the value of dailyHouseholdAverage
     */
    public double getDailyHouseholdAverage() {
        return dailyHouseholdAverage;
    }

    private double costLow;

    /**
     * Get the value of costLow
     *
     * @return the value of costLow
     */
    public double getCostLow() {
        return costLow;
    }

    private double costMed;

    /**
     * Get the value of costMed
     *
     * @return the value of costMed
     */
    public double getCostMed() {
        return costMed;
    }

    private double costHigh;

    /**
     * Get the value of costHigh
     *
     * @return the value of costHigh
     */
    public double getCostHigh() {
        return costHigh;
    }

    private double adminFee;

    /**
     * Get the value of adminFee
     *
     * @return the value of adminFee
     */
    public double getAdminFee() {
        return adminFee;
    }

    private double sub;

    /**
     * Get the value of sub
     *
     * @return the value of sub
     */
    public double getSub() {
        return sub;
    }

    private double tax;

    /**
     * Get the value of tax
     *
     * @return the value of tax
     */
    public double getTax() {
        return tax;
    }

    private double total;

    /**
     * Get the value of total
     *
     * @return the value of total
     */
    public double getTotal() {
        return total;
    }

    private double costLowRate;

    /**
     * Get the value of costLowRate
     *
     * @return the value of costLowRate
     */
    public double getCostLowRate() {
        return costLowRate;
    }

    private double costMedRate;

    /**
     * Get the value of costMedRate
     *
     * @return the value of costMedRate
     */
    public double getCostMedRate() {
        return costMedRate;
    }

    private double costHighRate;

    /**
     * Get the value of costHighRate
     *
     * @return the value of costHighRate
     */
    public double getCostHighRate() {
        return costHighRate;
    }

    private List<Usage> summaries;

    public List<Usage> getData() throws ParseException {
        List<Rates> allRates = em.createNamedQuery("Rates.findAll").getResultList();
        this.meterID = user.getMeterID();
        this.billingData = em.createNamedQuery("Billingdata.findByMeterid").setParameter("meterid", this.meterID).getResultList();
        summaries = new ArrayList<Usage>();

        for (int i = 0; i < this.billingData.size(); i++) {
            if (i == 0) {
                summaries.add(new Usage(
                        this.billingData.get(i).getMeterid(),
                        this.billingData.get(i).getReading(),
                        this.billingData.get(i).getBillingdate(),
                        0,
                        allRates.get(0).getLowRate(),
                        allRates.get(0).getMediumRate(),
                        allRates.get(0).getHighRate(),
                        allRates.get(0).getTaxRate(),
                        allRates.get(0).getAdminRate()
                ));
            } else {
                int j = i - 1; //This prevents repetitive data for some reason - can't use i-1 in parameter
                summaries.add(new Usage(
                        this.billingData.get(i).getMeterid(),
                        this.billingData.get(i).getReading(),
                        this.billingData.get(i).getBillingdate(),
                        this.billingData.get(j).getReading(),
                        allRates.get(0).getLowRate(),
                        allRates.get(0).getMediumRate(),
                        allRates.get(0).getHighRate(),
                        allRates.get(0).getTaxRate(),
                        allRates.get(0).getAdminRate()
                ));
            }
        }
        return summaries;
    }

    public String meterID() {
        this.meterID = user.getMeterID();
        return this.meterID;
    }

    public String setDetails(String date) {
        for (int i = 0; i < summaries.size(); i++) {
            if (date.equals(this.summaries.get(i).getDate())) {
                this.amountUsed = this.summaries.get(i).getAmountUsed();
                this.lowAmountUsed = this.summaries.get(i).lowAmountUsed;
                this.medAmountUsed = this.summaries.get(i).medAmountUsed;
                this.highAmountUsed = this.summaries.get(i).highAmountUsed;
                this.dailyHouseholdAverage = this.summaries.get(i).dailyHousehold;

                this.costLow = this.summaries.get(i).getLowAmountCost();
                this.costMed = this.summaries.get(i).getMediumAmountCost();
                this.costHigh = this.summaries.get(i).getHighAmountCost();
                this.adminFee = this.summaries.get(i).adminFee;
                this.sub = this.summaries.get(i).getSubtotal();
                this.tax = this.summaries.get(i).getTax();
                this.total = this.summaries.get(i).getTotalCost();

                this.costLowRate = this.summaries.get(i).lowRate;
                this.costMedRate = this.summaries.get(i).medRate;
                this.costHighRate = this.summaries.get(i).highRate;

                break;
            }
        }
        return "billdetails?faces-redirect=true";
    }

    public double testing() {
        this.meterID = user.getMeterID();
        List<Rates> allRates = em.createNamedQuery("Rates.findAll").getResultList();

        return allRates.get(0).getTaxRate();
    }

    private StringBuilder buildDate = new StringBuilder();

    public StringBuilder getSummaryChart() {
        for (int i = 0; i < this.summaries.size(); i++) {
            buildDate.append("['" + this.summaries.get(i).getDate() + "'," + this.summaries.get(i).getAmountUsed() + "],");
        }

        return buildDate;
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
