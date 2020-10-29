/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblpaymentstatus;

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
public class TblPaymentStatusDao implements Serializable {

    public int getStatusId(String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT statusId FROM tblPaymentStatus WHERE name = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            rs = stm.executeQuery();
            if (rs.next()) {
                int statusId = rs.getInt("statusId");
                return statusId;
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

    public String getStatusName(int statusId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name FROM tblPaymentStatus WHERE statusId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, statusId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String statusName = rs.getString("name");
                return statusName;
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
