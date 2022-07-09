/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Cart implements Serializable {

    private List<RoomInCart> cart;

    public List<RoomInCart> getCart() {
        return cart;
    }

    public void addRoomToCart(String roomID, String typeOfRoom, float price) {
        if (this.cart == null) {
            this.cart = new ArrayList<>();
        }
        int quantity;
        boolean flag = false;
        for (int i = 0; i < this.cart.size(); i++) {
            if (this.cart.get(i).getRoomID().equals(roomID)) {
                flag = true;
                this.cart.get(i).setPrice(price);
            }
        }
        if (flag == false) {
            RoomInCart roomInCart = new RoomInCart(roomID, typeOfRoom, price);
            this.cart.add(roomInCart);
        }
    }

    public void removeRoomInCart(String roomID) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.isEmpty()) {
            return;
        }
        if (this.cart.size() == 1) {
            if (this.cart.get(0).getRoomID().equals(roomID)) {
                this.cart.remove(0);
                if (this.cart.isEmpty()) {
                    this.cart = null;
                }
            }
        } else {
            for (int i = 0; i < this.cart.size(); i++) {
                if (this.cart.get(i).getRoomID().equals(roomID)) {
                    this.cart.remove(i);
                    if (this.cart.isEmpty()) {
                        this.cart = null;
                    }
                }
            }
        }
    }

}
