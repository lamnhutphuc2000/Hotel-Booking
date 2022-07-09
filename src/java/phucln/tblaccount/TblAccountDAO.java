/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblaccount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblAccountDAO implements Serializable{
    
    public TblAccountDTO checkLogin(String email, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT roleID, name, phone, address, createDate "
                        + "FROM tblAccount "
                        + "WHERE email = ? AND password = ? AND status = 'Active'";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String roleID = rs.getString("roleID");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    Date createDate = rs.getDate("createDate"); 
                    result = new TblAccountDTO(email,roleID, name, phone, address, createDate, "Active");
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
    
    public boolean checkAccountExisted(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT name "
                        + "FROM tblAccount "
                        + "WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email); 
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
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
    public boolean createNewAccount(String email, String password, String name, String phone, String address ) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null; 
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblAccount(email,password,roleID,name,phone,address,createDate,status) "
                        + "VALUES(?,?,?,?,?,?,?,?) ";
                stm = con.prepareStatement(sql);
                long milis = System.currentTimeMillis();
                Date date = new Date(milis);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, "US");
                stm.setString(4, name);
                stm.setString(5, phone);
                stm.setString(6, address);
                stm.setDate(7, date); 
                stm.setString(8, "Active");
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
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
