/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblroom;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class SearchRoomError implements Serializable{
    private String checkInDateFormatError; 
    private String checkOutDateFormatError; 
    private String checkOutBeforeCheckInError;
    private String checkInDateBeforeNowError;
    private String checkOutDateBeforeNowtError;

    public SearchRoomError() {
    }

    public SearchRoomError(String checkInDateFormatError, String checkOutDateFormatError, String checkOutBeforeCheckInError, String checkInDateBeforeNowError, String checkOutDateBeforeNowtError) {
        this.checkInDateFormatError = checkInDateFormatError;
        this.checkOutDateFormatError = checkOutDateFormatError;
        this.checkOutBeforeCheckInError = checkOutBeforeCheckInError;
        this.checkInDateBeforeNowError = checkInDateBeforeNowError;
        this.checkOutDateBeforeNowtError = checkOutDateBeforeNowtError;
    }

    

    /**
     * @return the checkInDateFormatError
     */
    public String getCheckInDateFormatError() {
        return checkInDateFormatError;
    }

    /**
     * @param checkInDateFormatError the checkInDateFormatError to set
     */
    public void setCheckInDateFormatError(String checkInDateFormatError) {
        this.checkInDateFormatError = checkInDateFormatError;
    }

    /**
     * @return the checkOutDateFormatError
     */
    public String getCheckOutDateFormatError() {
        return checkOutDateFormatError;
    }

    /**
     * @param checkOutDateFormatError the checkOutDateFormatError to set
     */
    public void setCheckOutDateFormatError(String checkOutDateFormatError) {
        this.checkOutDateFormatError = checkOutDateFormatError;
    }

    /**
     * @return the checkOutBeforeCheckInError
     */
    public String getCheckOutBeforeCheckInError() {
        return checkOutBeforeCheckInError;
    }

    /**
     * @param checkOutBeforeCheckInError the checkOutBeforeCheckInError to set
     */
    public void setCheckOutBeforeCheckInError(String checkOutBeforeCheckInError) {
        this.checkOutBeforeCheckInError = checkOutBeforeCheckInError;
    }

    /**
     * @return the checkInDateBeforeNowError
     */
    public String getCheckInDateBeforeNowError() {
        return checkInDateBeforeNowError;
    }

    /**
     * @param checkInDateBeforeNowError the checkInDateBeforeNowError to set
     */
    public void setCheckInDateBeforeNowError(String checkInDateBeforeNowError) {
        this.checkInDateBeforeNowError = checkInDateBeforeNowError;
    }

    /**
     * @return the checkOutDateBeforeNowtError
     */
    public String getCheckOutDateBeforeNowtError() {
        return checkOutDateBeforeNowtError;
    }

    /**
     * @param checkOutDateBeforeNowtError the checkOutDateBeforeNowtError to set
     */
    public void setCheckOutDateBeforeNowtError(String checkOutDateBeforeNowtError) {
        this.checkOutDateBeforeNowtError = checkOutDateBeforeNowtError;
    }
    
}
