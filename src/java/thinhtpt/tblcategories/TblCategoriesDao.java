/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblcategories;

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
public class TblCategoriesDao implements Serializable {

    public List<TblCategoriesDto> getAllCategories()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblCategoriesDto> listCategories = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT categoryId, name FROM tblCategories";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("categoryId");
                String name = rs.getString("name");
                TblCategoriesDto dto = new TblCategoriesDto(categoryId, name);
                if (listCategories == null) {
                    listCategories = new ArrayList<>();
                }
                listCategories.add(dto);
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
        return listCategories;
    }
}
