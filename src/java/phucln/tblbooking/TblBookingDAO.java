/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbooking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException; 
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblBookingDAO {
    List<TblBookingDTO> listBooking; 

    public List<TblBookingDTO> getListBooking() {
        return listBooking;
    }
 
    public void searchBookingByCheckInCheckOut(Date checkInDate, Date checkOutDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID "
                        + "FROM tblBooking "
                        + "WHERE ((checkInDate <= ? AND checkOutDate >= ?) OR (checkInDate <= ? AND checkOutDate >= ?) OR (checkInDate >= ? AND checkOutDate <= ?)) AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setDate(1, checkInDate);
                stm.setDate(2, checkInDate);
                stm.setDate(3, checkOutDate);
                stm.setDate(4, checkOutDate);
                stm.setDate(5, checkInDate);
                stm.setDate(6, checkOutDate);
                stm.setString(7, "Active"); 
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBooking == null) {
                        listBooking = new ArrayList<>();
                    } 
                    String bookingID = rs.getString("bookingID"); 
                    TblBookingDTO bookingDTO = new  TblBookingDTO(bookingID, "", null, 0, null, null, "Active", "");
                    listBooking.add(bookingDTO);
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
    public boolean insertNewBooking(String bookingID, String userID, float totalPrice, Date checkInDate, Date checkOutDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblBooking(bookingID,userID,bookingDate,totalPrice,checkInDate,CheckOutDate,status,historyStatus) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, userID); 
                Timestamp now = new Timestamp(System.currentTimeMillis());
                stm.setTimestamp(3, now);
                stm.setFloat(4, totalPrice);
                stm.setDate(5, checkInDate);
                stm.setDate(6, checkOutDate);  
                stm.setString(7, "Waiting");
                stm.setString(8, "Active");
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
    public boolean confirmBooking(String bookingID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBooking "
                        + "SET status = ? "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                stm.setString(2, bookingID);  
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
    public void getBookingByUserID(String userID ) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql =
                        "SELECT tmp.bookingID, tmp.userID, tmp.bookingDate, tmp.totalPrice, tmp.checkInDate, tmp.checkOutDate, tmp.status, tmp.historyStatus "
                        + "FROM ( "
                        + "SELECT bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status, historyStatus "
                        + "FROM tblbooking "
                        + "WHERE userID = ? AND status = ? AND historyStatus = ?"
                        + ")tmp "
                        + "ORDER BY tmp.bookingDate DESC ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, "Active"); 
                stm.setString(3, "Active"); 
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBooking == null) {
                        listBooking = new ArrayList<>();
                    }
                    String bookingID = rs.getString("bookingID"); 
                    Timestamp bookingDate = rs.getTimestamp("bookingDate"); 
                    float totalPrice = rs.getFloat("totalPrice");
                    Date checkInDate = rs.getDate("checkInDate");
                    Date checkOutDate = rs.getDate("checkOutDate"); 
                    String status = rs.getString("status"); 
                    String historyStatus = rs.getString("historyStatus"); 
                    TblBookingDTO dto = new  TblBookingDTO(bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status,historyStatus);
                    listBooking.add(dto);
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
    public void getBookingByUserIDAndDate(String userID, String searchDate ) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql =
                        "SELECT tmp.bookingID, tmp.userID, tmp.bookingDate, tmp.totalPrice, tmp.checkInDate, tmp.checkOutDate, tmp.status, tmp.historyStatus "
                        + "FROM ( "
                        + "SELECT bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status, historyStatus "
                        + "FROM tblbooking "
                        + "WHERE userID = ? AND status = ? AND ( CONVERT(VARCHAR(25), bookingDate, 126) LIKE ? ) AND historyStatus = ? "
                        + ")tmp "
                        + "ORDER BY tmp.bookingDate DESC ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, "Active"); 
                stm.setString(3, searchDate + "%"); 
                stm.setString(4, "Active"); 
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBooking == null) {
                        listBooking = new ArrayList<>();
                    }
                    String bookingID = rs.getString("bookingID"); 
                    Timestamp bookingDate = rs.getTimestamp("bookingDate"); 
                    float totalPrice = rs.getFloat("totalPrice");
                    Date checkInDate = rs.getDate("checkInDate");
                    Date checkOutDate = rs.getDate("checkOutDate"); 
                    String status = rs.getString("status"); 
                    String historyStatus = rs.getString("historyStatus");
                    TblBookingDTO dto = new  TblBookingDTO(bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status,historyStatus);
                    listBooking.add(dto);
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
     public boolean deleteBookingHistory(String bookingID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBooking "
                        + "SET historyStatus = ? "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Inactive");
                stm.setString(2, bookingID);  
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
    public TblBookingDTO getBookingByBookingID(String bookingID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        TblBookingDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status, historyStatus "
                        + "FROM tblbooking "
                        + "WHERE bookingID = ?"; 
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID); 
                rs = stm.executeQuery();
                if (rs.next()) {  
                    String userID = rs.getString("userID");
                    Timestamp bookingDate = rs.getTimestamp("bookingDate"); 
                    float totalPrice = rs.getFloat("totalPrice");
                    Date checkInDate = rs.getDate("checkInDate");
                    Date checkOutDate = rs.getDate("checkOutDate"); 
                    String status = rs.getString("status"); 
                    String historyStatus = rs.getString("historyStatus");
                    result = new  TblBookingDTO(bookingID, userID, bookingDate, totalPrice, checkInDate, checkOutDate, status,historyStatus);
                    
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
