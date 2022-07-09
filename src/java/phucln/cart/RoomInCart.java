/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.cart;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class RoomInCart implements Serializable {

    private String roomID;
    private String typeOfRoom;
    private float price;

    public RoomInCart() {
    }

    public RoomInCart(String roomID, String typeOfRoom, float price) {
        this.roomID = roomID;
        this.typeOfRoom = typeOfRoom;
        this.price = price;
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

}
