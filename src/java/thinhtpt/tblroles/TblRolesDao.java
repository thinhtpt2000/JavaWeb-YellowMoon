/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblroles;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import thinhtpt.utils.DBHelper;

/**
 *
 * @author ThinhTPT
 */
public class TblRolesDao implements Serializable {

    public int getRoleByName(String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            String sql = "SELECT roleId FROM tblRoles WHERE name = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            rs = stm.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt("roleId");
                return roleId;
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
        return -1;
    }

    public String getRoleNameById(int roleId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name FROM tblRoles WHERE roleId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, roleId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return name;
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
        return null;
    }
}
