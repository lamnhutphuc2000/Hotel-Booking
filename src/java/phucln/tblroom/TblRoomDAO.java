/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblroom;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblRoomDAO implements Serializable{
    List<TblRoomDTO> listRoom; 

    public List<TblRoomDTO> getListRoom() {
        return listRoom;  
    }

    
    public void searchRoomByType(String searchTypeOfRoomID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT roomID, image, price, typeOfRoomID "
                        + "FROM tblRoom "
                        + "WHERE typeOfRoomID LIKE ? AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchTypeOfRoomID + "%"); 
                stm.setString(2, "Active"); 
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listRoom == null) {
                        listRoom = new ArrayList<>();
                    } 
                    String roomID = rs.getString("roomID");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    image = "./Img/" + image; 
                    String typeOfRoomID = rs.getString("typeOfRoomID");
                    TblRoomDTO roomDTO = new  TblRoomDTO(roomID, typeOfRoomID, image, price, "Active");
                    listRoom.add(roomDTO);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
