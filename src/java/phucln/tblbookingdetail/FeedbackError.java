/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbookingdetail;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class FeedbackError implements Serializable{
    private String dateNotYetError;

    public FeedbackError() {
    }

    public FeedbackError(String dateNotYetError) {
        this.dateNotYetError = dateNotYetError;
    }

    /**
     * @return the dateNotYetError
     */
    public String getDateNotYetError() {
        return dateNotYetError;
    }

    /**
     * @param dateNotYetError the dateNotYetError to set
     */
    public void setDateNotYetError(String dateNotYetError) {
        this.dateNotYetError = dateNotYetError;
    }
    
}
