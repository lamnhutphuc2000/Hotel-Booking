/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.role;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblRoleDAO implements Serializable {

    public String getRoleName(String roleID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT roleName "
                        + "FROM tblRole "
                        + "WHERE roleID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, roleID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString("roleName");
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
