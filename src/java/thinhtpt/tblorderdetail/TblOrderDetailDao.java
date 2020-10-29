/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import thinhtpt.cart.CartDetails;
import thinhtpt.utils.DBHelper;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderDetailDao implements Serializable {

    public boolean addAProductToOrder(String orderId, Map<Integer, CartDetails> items)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblOrderDetail(orderId, productId, quantity, price) "
                        + "VALUES (?, ?, ?, ?)";
                con.setAutoCommit(false);
                for (int key : items.keySet()) {
                    stm = con.prepareStatement(sql);
                    stm.setString(1, orderId);
                    stm.setInt(2, key);
                    int quantity = items.get(key).getAmount();
                    stm.setInt(3, quantity);
                    int price = items.get(key).getPrice();
                    stm.setInt(4, price);
                    stm.executeUpdate();
                }
                con.commit();
                con.setAutoCommit(true);
                return true;
            }
        } catch (SQLException | NamingException ex) {
            if (con != null) {
                con.rollback();
            }
            throw ex;
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

    public List<TblOrderDetailDtoExtends> getListCartDetailByOrderId(String orderId) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblOrderDetailDtoExtends> listDetail = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT detailId, productId, price, quantity "
                    + "FROM tblOrderDetail "
                    + "WHERE orderId = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int detailId = rs.getInt("detailId");
                int productId = rs.getInt("productId");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                TblOrderDetailDtoExtends dto = new TblOrderDetailDtoExtends(detailId, productId, price, quantity);
                if (listDetail == null) {
                    listDetail = new ArrayList<>();
                }
                listDetail.add(dto);
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
        return listDetail;
    }
}
