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
public class GoogleAccount implements Serializable{
    private String email;
    private String name;

    public GoogleAccount() {
    }

    public GoogleAccount(String email, String name) {
        this.email = email;
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
