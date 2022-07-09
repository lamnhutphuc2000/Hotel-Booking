/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbooking;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class TblBookingDTO implements Serializable{
    private String bookingID;
    private String userID;
    private Timestamp bookingDate;
    private float price;
    private Date checkInDate;
    private Date checkOutDate;
    private String status;
    private String historyStatus;

    public TblBookingDTO() {
    }

    public TblBookingDTO(String bookingID, String userID, Timestamp bookingDate, float price, Date checkInDate, Date checkOutDate, String status, String historyStatus) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.bookingDate = bookingDate;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.historyStatus = historyStatus;
    }

    

    /**
     * @return the bookingID
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * @param bookingID the bookingID to set
     */
    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the bookingDate
     */
    public Timestamp getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the checkInDate
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate the checkInDate to set
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return the checkOutDate
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate the checkOutDate to set
     */
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the historyStatus
     */
    public String getHistoryStatus() {
        return historyStatus;
    }

    /**
     * @param historyStatus the historyStatus to set
     */
    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
    }
 
    
}
