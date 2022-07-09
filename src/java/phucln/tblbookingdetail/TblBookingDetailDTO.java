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
public class TblBookingDetailDTO implements Serializable{
    private String bookingID;
    private String roomID;
    private String typeOfRoom;
    private float price; 
    private String rating;

    public TblBookingDetailDTO() {
    }

    public TblBookingDetailDTO(String bookingID, String roomID, String typeOfRoom, float price, String rating) {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.typeOfRoom = typeOfRoom;
        this.price = price;
        this.rating = rating;
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
     * @return the typeOfRoom
     */
    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    /**
     * @param typeOfRoom the typeOfRoom to set
     */
    public void setTypeOfRoom(String typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
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
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
    }
 
 
}
