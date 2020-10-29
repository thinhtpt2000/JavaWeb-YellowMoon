/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblusers;

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
public class TblUsersDao implements Serializable {

    public TblUsersDto checkLogin(String username, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT userId, fullName, roleId FROM tblUsers "
                    + "WHERE username = ? AND password = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);

            rs = stm.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");
                String fullName = rs.getString("fullName");
                int roleId = rs.getInt("roleId");
                TblUsersDto user = new TblUsersDto(userId, username, fullName, roleId);
                return user;
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

    public TblUsersDto getUserInfo(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT fullName, phone, address FROM tblUsers "
                    + "WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);

            rs = stm.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("fullName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                TblUsersDto user = new TblUsersDto(fullName, phone, address);
                return user;
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

    public TblUsersDto checkExistEmail(String email)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT userId, fullName, roleId, googleId FROM tblUsers "
                    + "WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);

            rs = stm.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");
                String fullName = rs.getString("fullName");
                int roleId = rs.getInt("roleId");
                String googleId = rs.getString("googleId");
                TblUsersDto user = new TblUsersDto(userId, email, fullName, roleId, googleId);
                return user;
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

    public int addNewGoogleAccount(TblUsersDto dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "INSERT INTO tblUsers (username, fullName, roleId, googleId) "
                    + "OUTPUT Inserted.userId AS userId "
                    + "VALUES(?, ?, ?, ?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getUsername());
            stm.setNString(2, dto.getFullName());
            stm.setInt(3, dto.getRoleId());
            stm.setString(4, dto.getGoogleId());
            boolean result = stm.execute();
            if (result) {
                rs = stm.getResultSet();
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    return userId;
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
        return -1;
    }

    public boolean updateGoogleIdForAccount(String gooleId, int userId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            String sql = "UPDATE tblUsers SET googleId = ? "
                    + "WHERE userId = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, gooleId);
            stm.setInt(2, userId);
            int row = stm.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
