/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbooking;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class BookingHistoryError implements Serializable{
    private String dateFormatError;

    public BookingHistoryError() {
    }

    public BookingHistoryError(String dateFormatError) {
        this.dateFormatError = dateFormatError;
    }

    /**
     * @return the dateFormatError
     */
    public String getDateFormatError() {
        return dateFormatError;
    }

    /**
     * @param dateFormatError the dateFormatError to set
     */
    public void setDateFormatError(String dateFormatError) {
        this.dateFormatError = dateFormatError;
    }
    
}
