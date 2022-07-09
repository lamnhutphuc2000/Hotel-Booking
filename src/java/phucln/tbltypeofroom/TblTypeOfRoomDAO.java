/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tbltypeofroom;

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
public class TblTypeOfRoomDAO {
    List<TblTypeOfRoomDTO> listTypeOfRoom;

    public List<TblTypeOfRoomDTO> getListTypeOfRoom() {
        return listTypeOfRoom;
    }

     
    public void loadAllTypeOfRoom() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT typeOfRoomID, typeOfRoomName "
                        + "FROM tblTypeOfRoom";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listTypeOfRoom == null) {
                        listTypeOfRoom = new ArrayList<>();
                    }
                    String typeOfRoomID = rs.getString("typeOfRoomID");
                    String typeOfRoomName = rs.getString("typeOfRoomName");
                    TblTypeOfRoomDTO dto = new TblTypeOfRoomDTO(typeOfRoomID, typeOfRoomName);
                    listTypeOfRoom.add(dto);
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
    public String getTypeOfRoomID(String typeOfRoomName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT typeOfRoomID "
                        + "FROM tblTypeOfRoom "
                        + "WHERE typeOfRoomName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, typeOfRoomName);
                rs = stm.executeQuery();
                if(rs.next()) {
                    result = rs.getString("typeOfRoomID");
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
        return result;
    }
}
