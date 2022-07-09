/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tbltypeofroom;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class TblTypeOfRoomDTO implements Serializable{
    private String typeOfRoomID;
    private String typeOfRoomName;

    public TblTypeOfRoomDTO() {
    }

    public TblTypeOfRoomDTO(String typeOfRoomID, String typeOfRoomName) {
        this.typeOfRoomID = typeOfRoomID;
        this.typeOfRoomName = typeOfRoomName;
    }

    /**
     * @return the typeOfRoomID
     */
    public String getTypeOfRoomID() {
        return typeOfRoomID;
    }

    /**
     * @param typeOfRoomID the typeOfRoomID to set
     */
    public void setTypeOfRoomID(String typeOfRoomID) {
        this.typeOfRoomID = typeOfRoomID;
    }

    /**
     * @return the typeOfRoomName
     */
    public String getTypeOfRoomName() {
        return typeOfRoomName;
    }

    /**
     * @param typeOfRoomName the typeOfRoomName to set
     */
    public void setTypeOfRoomName(String typeOfRoomName) {
        this.typeOfRoomName = typeOfRoomName;
    }
    
}
