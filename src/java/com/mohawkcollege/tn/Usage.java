/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Thom
 * 
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
public class Usage {

    public Usage() {
    }

    public Usage(String meter, int reading, String date, int previous, double lowRate, double medRate, double highRate, double taxRate, double adminFee) throws ParseException {
        this.meterId = meter;
        this.date = date;
        this.reading = reading;
        this.previousMonthReading = previous;

        this.lowRate = lowRate;
        this.medRate = medRate;
        this.highRate = highRate;
        this.taxRate = taxRate;
        this.adminFee = adminFee;

        this.calculateBill();
    }
    private double lowAmountCost;

    /**
     * Get the value of lowAmountCost
     *
     * @return the value of lowAmountCost
     */
    public double getLowAmountCost() {
        return lowAmountCost;
    }

    private double mediumAmountCost;

    /**
     * Get the value of mediumAmountCost
     *
     * @return the value of mediumAmountCost
     */
    public double getMediumAmountCost() {
        return mediumAmountCost;
    }

    private double highAmountCost;

    /**
     * Get the value of highAmountCost
     *
     * @return the value of highAmountCost
     */
    public double getHighAmountCost() {
        return highAmountCost;
    }

    private double subtotal;

    /**
     * Get the value of subtotal
     *
     * @return the value of subtotal
     */
    public double getSubtotal() {
        return subtotal;
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

    private double totalCost;

    /**
     * Get the value of totalCost
     *
     * @return the value of totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    public double dailyHousehold;

    /**
     * Get the value of dailyHousehold
     *
     * @return the value of dailyHousehold
     */
    public double getDailyHousehold() {
        return dailyHousehold;
    }

    /**
     * Set the value of dailyHousehold
     *
     * @param dailyHousehold
     */
    public void setDailyHousehold(int dailyHousehold) {
        this.dailyHousehold = dailyHousehold;
    }

    private int previousMonthReading;

    /**
     * Get the value of previousMonthReading
     *
     * @return the value of previousMonthReading
     */
    public int getReadingPrevious() {
        return previousMonthReading;
    }

    /**
     * Set the value of previousMonthReading
     *
     * @param previousMonthReading
     */
    public void setPreviousMonthReading(int previousMonthReading) {
        this.previousMonthReading = previousMonthReading;
    }

    private double billTotal;

    /**
     * Get the value of billTotal
     *
     * @return the value of billTotal
     */
    public double getBillTotal() {
        return billTotal;
    }

    private double amountUsed;

    /**
     * Get the value of amountUsed
     *
     * @return the value of amountUsed
     */
    public double getAmountUsed() {
        return amountUsed;
    }

    private int reading;

    /**
     * Get the value of reading
     *
     * @return the value of reading
     */
    public int getReading() {
        return reading;
    }

    /**
     * Set the value of reading
     *
     * @param reading
     */
    public void setReading(int reading) {
        this.reading = reading;
    }

    public String date;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    private String meterId;

    /**
     * Get the value of meterId
     *
     * @return the value of meterId
     */
    public String getMeterID() {
        return meterId;
    }

    /**
     * Set the value of meterId
     *
     * @param meterId
     */
    public void setMeterID(String meterId) {
        this.meterId = meterId;
    }

    public double lowRate;
    public double medRate;
    public double highRate;
    public double taxRate;
    public double adminFee;

    public int lowAmountUsed = 0;
    public int medAmountUsed = 0;
    public int highAmountUsed = 0;

    private void calculateBill() throws ParseException {
        if (this.previousMonthReading == 0) {
            this.amountUsed = 0;
        } else {
            this.amountUsed = (this.reading / 50) - (this.previousMonthReading / 50);
        }

        if (this.amountUsed >= 5) {
            this.lowAmountUsed = 5;
        } else {
            this.lowAmountUsed = (int) this.amountUsed;
        }
        if (this.amountUsed >= 25) {
            this.medAmountUsed = 25 - this.lowAmountUsed;
        } else {
            this.medAmountUsed = (int) (this.amountUsed - this.lowAmountUsed);
        }
        if (this.medAmountUsed >= 25) {
            this.highAmountUsed = (int) (this.amountUsed - (this.medAmountUsed + this.lowAmountUsed));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse(this.date));
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        this.dailyHousehold = (this.amountUsed * 1000) / numberOfDays;

        this.lowAmountCost = this.lowAmountUsed * this.lowRate;
        this.mediumAmountCost = this.medAmountUsed * this.medRate;
        this.highAmountCost = this.highAmountUsed * this.highRate;

        this.subtotal = this.lowAmountCost + this.mediumAmountCost + this.highAmountCost + this.adminFee;

        this.tax = (this.subtotal * (this.taxRate / 100));

        this.totalCost = this.subtotal + this.tax;
    }
}
