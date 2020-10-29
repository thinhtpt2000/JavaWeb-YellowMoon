/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;
import javax.naming.NamingException;
import thinhtpt.utils.DBHelper;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderDao implements Serializable {

    public String addNewOrder(TblOrderDto dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "INSERT INTO tblOrder (userId, total, date, name, address, phone, paymentMethod, paymentStatus) "
                    + "OUTPUT Inserted.orderId AS orderId "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            stm = con.prepareStatement(sql);
            if (dto.getUserId() != -1) {
                stm.setInt(1, dto.getUserId());
            } else {
                stm.setNull(1, Types.INTEGER);
            }
            stm.setLong(2, dto.getTotal());
            stm.setTimestamp(3, dto.getDate());
            stm.setNString(4, dto.getUserName());
            stm.setNString(5, dto.getUserAddress());
            stm.setString(6, dto.getUserPhone());
            stm.setInt(7, dto.getPaymentMethod());
            stm.setInt(8, dto.getPaymentStatus());
            boolean result = stm.execute();
            if (result) {
                rs = stm.getResultSet();
                if (rs.next()) {
                    String orderId = rs.getString("orderId");
                    return orderId;
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
        return null;
    }

    public boolean updatePaymentStatusOrder(String orderId, int paymentStatus)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            String sql = "UPDATE tblOrder SET paymentStatus = ? "
                    + "WHERE orderId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, paymentStatus);
            stm.setString(2, orderId);
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

    public TblOrderDto getOrderInfo(String orderId, int userId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT total, date, name, address, phone, paymentMethod, paymentStatus "
                    + "FROM tblOrder "
                    + "WHERE orderId = ? "
                    + "AND userId = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            stm.setInt(2, userId);

            rs = stm.executeQuery();
            if (rs.next()) {
                long total = rs.getLong("total");
                Timestamp date = rs.getTimestamp("date");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int paymentMethod = rs.getInt("paymentMethod");
                int paymentStatus = rs.getInt("paymentStatus");
                TblOrderDto orderDto = new TblOrderDto(total, date, name, address, phone, paymentMethod, paymentStatus);
                orderDto.setOrderId(orderId);
                return orderDto;
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
