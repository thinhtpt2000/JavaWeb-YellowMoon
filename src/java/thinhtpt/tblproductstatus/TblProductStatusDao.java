/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblproductstatus;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thinhtpt.utils.DBHelper;

/**
 *
 * @author ThinhTPT
 */
public class TblProductStatusDao implements Serializable {

    public int getStatusIdByName(String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT statusId "
                    + "FROM tblProductStatus "
                    + "WHERE name = ?";
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

    public List<TblProductStatusDto> getAllProductStatus()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblProductStatusDto> listStatus = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT statusId, name "
                    + "FROM tblProductStatus";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int statusId = rs.getInt("statusId");
                String name = rs.getString("name");
                TblProductStatusDto dto = new TblProductStatusDto(statusId, name);
                if (listStatus == null) {
                    listStatus = new ArrayList<>();
                }
                listStatus.add(dto);
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
        return listStatus;
    }
}
