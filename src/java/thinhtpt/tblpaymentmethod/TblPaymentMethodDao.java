/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblpaymentmethod;

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
public class TblPaymentMethodDao implements Serializable {

    public List<TblPaymentMethodDto> getListMethod()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblPaymentMethodDto> listMethod = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT paymentId, name FROM tblPaymentMethod";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int paymentId = rs.getInt("paymentId");
                String name = rs.getString("name");
                TblPaymentMethodDto dto = new TblPaymentMethodDto(paymentId, name);
                if (listMethod == null) {
                    listMethod = new ArrayList<>();
                }
                listMethod.add(dto);
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
        return listMethod;
    }

    public String getMethodNameById(int id)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name FROM tblPaymentMethod WHERE paymentId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
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
