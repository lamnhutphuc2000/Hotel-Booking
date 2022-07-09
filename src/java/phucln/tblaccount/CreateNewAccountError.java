/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblaccount;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class CreateNewAccountError implements Serializable{
    private String emailFormatError;
    private String emailDuplicateError;
    private String emailEmptyError;
    private String passwordLengthError;
    private String confirmPasswordLengthError;
    private String confirmPasswordNotMatchedError;
    private String nameLengthError;
    private String phoneFormatError;
    private String phoneLengthError;
    private String addressLengthError;

    public CreateNewAccountError() {
    }

    public CreateNewAccountError(String emailFormatError, String emailDuplicateError, String emailEmptyError, String passwordLengthError, String confirmPasswordLengthError, String confirmPasswordNotMatchedError, String nameLengthError, String phoneFormatError, String phoneLengthError, String addressLengthError) {
        this.emailFormatError = emailFormatError;
        this.emailDuplicateError = emailDuplicateError;
        this.emailEmptyError = emailEmptyError;
        this.passwordLengthError = passwordLengthError;
        this.confirmPasswordLengthError = confirmPasswordLengthError;
        this.confirmPasswordNotMatchedError = confirmPasswordNotMatchedError;
        this.nameLengthError = nameLengthError;
        this.phoneFormatError = phoneFormatError;
        this.phoneLengthError = phoneLengthError;
        this.addressLengthError = addressLengthError;
    }

    

    /**
     * @return the emailFormatError
     */
    public String getEmailFormatError() {
        return emailFormatError;
    }

    /**
     * @param emailFormatError the emailFormatError to set
     */
    public void setEmailFormatError(String emailFormatError) {
        this.emailFormatError = emailFormatError;
    }

    /**
     * @return the emailDuplicateError
     */
    public String getEmailDuplicateError() {
        return emailDuplicateError;
    }

    /**
     * @param emailDuplicateError the emailDuplicateError to set
     */
    public void setEmailDuplicateError(String emailDuplicateError) {
        this.emailDuplicateError = emailDuplicateError;
    }

    /**
     * @return the emailEmptyError
     */
    public String getEmailEmptyError() {
        return emailEmptyError;
    }

    /**
     * @param emailEmptyError the emailEmptyError to set
     */
    public void setEmailEmptyError(String emailEmptyError) {
        this.emailEmptyError = emailEmptyError;
    }

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the nameLengthError
     */
    public String getNameLengthError() {
        return nameLengthError;
    }

    /**
     * @param nameLengthError the nameLengthError to set
     */
    public void setNameLengthError(String nameLengthError) {
        this.nameLengthError = nameLengthError;
    }

    /**
     * @return the phoneFormatError
     */
    public String getPhoneFormatError() {
        return phoneFormatError;
    }

    /**
     * @param phoneFormatError the phoneFormatError to set
     */
    public void setPhoneFormatError(String phoneFormatError) {
        this.phoneFormatError = phoneFormatError;
    }

    /**
     * @return the phoneLengthError
     */
    public String getPhoneLengthError() {
        return phoneLengthError;
    }

    /**
     * @param phoneLengthError the phoneLengthError to set
     */
    public void setPhoneLengthError(String phoneLengthError) {
        this.phoneLengthError = phoneLengthError;
    }

    /**
     * @return the addressLengthError
     */
    public String getAddressLengthError() {
        return addressLengthError;
    }

    /**
     * @param addressLengthError the addressLengthError to set
     */
    public void setAddressLengthError(String addressLengthError) {
        this.addressLengthError = addressLengthError;
    }

    /**
     * @return the confirmPasswordLengthError
     */
    public String getConfirmPasswordLengthError() {
        return confirmPasswordLengthError;
    }

    /**
     * @param confirmPasswordLengthError the confirmPasswordLengthError to set
     */
    public void setConfirmPasswordLengthError(String confirmPasswordLengthError) {
        this.confirmPasswordLengthError = confirmPasswordLengthError;
    }

    /**
     * @return the confirmPasswordNotMatchedError
     */
    public String getConfirmPasswordNotMatchedError() {
        return confirmPasswordNotMatchedError;
    }

    /**
     * @param confirmPasswordNotMatchedError the confirmPasswordNotMatchedError to set
     */
    public void setConfirmPasswordNotMatchedError(String confirmPasswordNotMatchedError) {
        this.confirmPasswordNotMatchedError = confirmPasswordNotMatchedError;
    }
 
}
