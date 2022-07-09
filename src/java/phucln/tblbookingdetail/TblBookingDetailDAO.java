/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbookingdetail;

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
public class TblBookingDetailDAO implements Serializable{
    List<TblBookingDetailDTO> listBookingDetail; 

    public List<TblBookingDetailDTO> getListBookingDetail() {
        return listBookingDetail;
    }

     
    public void searchBookingDetailByID(String bookingID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT roomID "
                        + "FROM tblBookingDetail "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);  
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBookingDetail == null) {
                        listBookingDetail = new ArrayList<>();
                    } 
                    String roomID = rs.getString("roomID"); 
                    TblBookingDetailDTO bookingDetalDTO = new TblBookingDetailDTO(bookingID, roomID, "", 0, "");
                    listBookingDetail.add(bookingDetalDTO);
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
    public boolean insertNewBookingDetail(String bookingID, String roomID, String typeOfRoom, float price, String rating) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblBookingDetail(bookingID,roomID,typeOfRoom,price,rating) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, roomID); 
                stm.setString(3, typeOfRoom);
                stm.setFloat(4, price);  
                stm.setString(5, null);
                int rowsOfEffected = stm.executeUpdate();
                if(rowsOfEffected > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    public void searchBookingDetailByIDName(String bookingID, String nameSearch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT roomID, typeOfRoom, price, rating "
                        + "FROM tblBookingDetail "
                        + "WHERE bookingID = ? AND typeOfRoom LIKE ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);  
                stm.setString(2, "%"+nameSearch+"%");  
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBookingDetail == null) {
                        listBookingDetail = new ArrayList<>();
                    } 
                    String roomID = rs.getString("roomID");
                    String typeOfRoom = rs.getString("typeOfRoom");
                    float price = rs.getFloat("price");
                    String rating = rs.getString("rating");
                    TblBookingDetailDTO bookingDetalDTO = new TblBookingDetailDTO(bookingID, roomID, typeOfRoom, price, rating);
                    listBookingDetail.add(bookingDetalDTO);
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
    public boolean insertFeedback(String bookingID, String roomID, String rating) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBookingDetail "
                        + "SET rating = ? "
                        + "WHERE bookingID = ? AND roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, rating);
                stm.setString(2, bookingID);  
                stm.setString(3, roomID);  
                int rowsOfEffected = stm.executeUpdate();
                if(rowsOfEffected == 1) {
                    result = true;
                }
            }
        } finally {
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
