/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tbllogs;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import thinhtpt.utils.DBHelper;

/**
 *
 * @author ThinhTPT
 */
public class TblLogsDao implements Serializable {

    public boolean addLog(TblLogsDto dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            String sql = "INSERT INTO tblLogs (userId, productId, date) "
                    + "VALUES (?, ?, ?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, dto.getUserId());
            stm.setInt(2, dto.getProductId());
            stm.setTimestamp(3, dto.getDate());

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
