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
public class TblRoomDTO implements Serializable{
    private String roomID;
    private String typeOfRoomID;
    private String image;
    private float price;
    private String status;

    public TblRoomDTO() {
    }

    public TblRoomDTO(String roomID, String typeOfRoomID, String image, float price, String status) {
        this.roomID = roomID;
        this.typeOfRoomID = typeOfRoomID;
        this.image = image;
        this.price = price;
        this.status = status;
    }

    /**
     * @return the roomID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * @param roomID the roomID to set
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
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
 
    
}
