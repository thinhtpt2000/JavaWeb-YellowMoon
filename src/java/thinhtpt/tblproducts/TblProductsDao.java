/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblproducts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class TblProductsDao implements Serializable {

    public List<TblProductsDto> searchProducts(String searchName, int categoryId,
            int minPrice, int maxPrice, int statusId, int pageInt, Timestamp time)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblProductsDto> listProducts = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT productId, name, image, description, price, categoryId, createDate, expirationDate "
                    + "FROM tblProducts "
                    + "WHERE (name LIKE ? ";
            if (searchName == null || searchName.trim().length() == 0) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND (categoryId = ? ";
            if (categoryId < 0) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND ((price BETWEEN ? AND ?) ";
            if ((minPrice == -1 && maxPrice == -1) || maxPrice < minPrice) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND statusId = ? "
                    + "AND quantity > 0 "
                    + "AND expirationDate > ? "
                    + "ORDER BY expirationDate ASC "
                    + "OFFSET (?-1)*20 ROWS "
                    + "FETCH NEXT 20 ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchName + "%");
            stm.setInt(2, categoryId);
            stm.setInt(3, minPrice);
            stm.setInt(4, maxPrice);
            stm.setInt(5, statusId);
            stm.setTimestamp(6, time);
            stm.setInt(7, pageInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String image = rs.getString("image");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int cateId = rs.getInt("categoryId");
                Timestamp createDate = rs.getTimestamp("createDate");
                Timestamp expirationDate = rs.getTimestamp("expirationDate");
                TblProductsDto dto
                        = new TblProductsDto(productId, name, description, image, price, cateId, createDate, expirationDate);
                if (listProducts == null) {
                    listProducts = new ArrayList<>();
                }
                listProducts.add(dto);
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
        return listProducts;
    }

    public List<TblProductsDto> getAllProducts(int pageInt)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblProductsDto> listProducts = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT productId, name, image, description, price, quantity "
                    + "FROM tblProducts "
                    + "ORDER BY expirationDate ASC "
                    + "OFFSET (?-1)*20 ROWS "
                    + "FETCH NEXT 20 ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setInt(1, pageInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String image = rs.getString("image");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                TblProductsDto dto
                        = new TblProductsDto(productId, name, description, image, price, quantity);
                if (listProducts == null) {
                    listProducts = new ArrayList<>();
                }
                listProducts.add(dto);
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
        return listProducts;
    }

    public int countNumberOfProduct()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT COUNT(productId) AS total "
                    + "FROM tblProducts";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                int result = rs.getInt("total");
                return result;
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
        return 0;
    }

    public TblProductsDto getProductById(int productId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name, image, price, quantity, categoryId, statusId, createDate, expirationDate "
                    + "FROM tblProducts "
                    + "WHERE productId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                int categoryId = rs.getInt("categoryId");
                int statusId = rs.getInt("statusId");
                Timestamp createDate = rs.getTimestamp("createDate");
                Timestamp expirationDate = rs.getTimestamp("expirationDate");
                TblProductsDto dto
                        = new TblProductsDto(productId, name, image, price, quantity, categoryId, createDate, expirationDate, statusId);
                return dto;
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

    public boolean updateProduct(TblProductsDto dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            String sql = "UPDATE tblProducts "
                    + "SET name = ?, "
                    + "image = ?, "
                    + "price = ?, "
                    + "quantity = ?, "
                    + "categoryId = ?, "
                    + "statusId = ?, "
                    + "createDate = ?, "
                    + "expirationDate = ? "
                    + "WHERE productId = ?";
            stm = con.prepareStatement(sql);
            stm.setNString(1, dto.getName());
            stm.setString(2, dto.getImage());
            stm.setInt(3, dto.getPrice());
            stm.setInt(4, dto.getQuantity());
            stm.setInt(5, dto.getCategoryId());
            stm.setInt(6, dto.getStatusId());
            stm.setTimestamp(7, dto.getCreateDate());
            stm.setTimestamp(8, dto.getExpirationDate());
            stm.setInt(9, dto.getProductId());

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

    public boolean addProduct(TblProductsDto dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            String sql = "INSERT INTO tblProducts (name, description, price, "
                    + "quantity, image, categoryId, statusId, createDate, expirationDate) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stm = con.prepareStatement(sql);
            stm.setNString(1, dto.getName());
            stm.setNString(2, dto.getDescription());
            stm.setInt(3, dto.getPrice());
            stm.setInt(4, dto.getQuantity());
            stm.setString(5, dto.getImage());
            stm.setInt(6, dto.getCategoryId());
            stm.setInt(7, dto.getStatusId());
            stm.setTimestamp(8, dto.getCreateDate());
            stm.setTimestamp(9, dto.getExpirationDate());

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

    public int countProductBySearchParams(String searchName, int categoryId,
            int minPrice, int maxPrice, int statusId, Timestamp time)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT COUNT(productId) AS total "
                    + "FROM tblProducts "
                    + "WHERE (name LIKE ? ";
            if (searchName == null || searchName.trim().length() == 0) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND (categoryId = ? ";
            if (categoryId < 0) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND ((price BETWEEN ? AND ?) ";
            if ((minPrice == -1 && maxPrice == -1) || maxPrice < minPrice) {
                sql += "OR 1 = 1";
            }
            sql += ") "
                    + "AND statusId = ? "
                    + "AND quantity > 0 "
                    + "AND expirationDate > ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchName + "%");
            stm.setInt(2, categoryId);
            stm.setInt(3, minPrice);
            stm.setInt(4, maxPrice);
            stm.setInt(5, statusId);
            stm.setTimestamp(6, time);
            rs = stm.executeQuery();
            if (rs.next()) {
                int result = rs.getInt("total");
                return result;
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
        return 0;
    }

    public TblProductsDto getProductCartById(int productId, int statusId, Timestamp time)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name, price, quantity "
                    + "FROM tblProducts "
                    + "WHERE productId = ? "
                    + "AND quantity > 0 "
                    + "AND statusId = ? "
                    + "AND expirationDate > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, productId);
            stm.setInt(2, statusId);
            stm.setTimestamp(3, time);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                TblProductsDto dto
                        = new TblProductsDto(productId, name, price, quantity);
                return dto;
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

    public int getProductQuantity(int productId, int statusId, Timestamp time)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT quantity "
                    + "FROM tblProducts "
                    + "WHERE productId = ? "
                    + "AND statusId = ? "
                    + "AND expirationDate > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, productId);
            stm.setInt(2, statusId);
            stm.setTimestamp(3, time);
            rs = stm.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                return quantity;
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
        return 0;
    }

    public boolean updateProductQuantity(Map<Integer, CartDetails> items)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblProducts "
                        + "SET quantity = (SELECT quantity FROM tblProducts WHERE productId = ? ) - ? "
                        + "OUTPUT Inserted.quantity AS newQuantity "
                        + "WHERE productId = ?";
                con.setAutoCommit(false);
                for (int key : items.keySet()) {
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, key);
                    int quantity = items.get(key).getAmount();
                    stm.setInt(2, quantity);
                    stm.setInt(3, key);
                    boolean result = stm.execute();
                    if (result) {
                        rs = stm.getResultSet();
                        if (rs.next()) {
                            int newQuantity = rs.getInt("newQuantity");
                            if (newQuantity < 0) {
                                throw new SQLException("Quantity cannot be negative");
                            }
                        }
                    }
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
        return false;
    }

    public boolean redoUpdateProductQuantity(Map<Integer, CartDetails> items)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblProducts "
                        + "SET quantity = (SELECT quantity FROM tblProducts WHERE productId = ? ) + ? "
                        + "WHERE productId = ?";
                con.setAutoCommit(false);
                for (int key : items.keySet()) {
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, key);
                    int quantity = items.get(key).getAmount();
                    stm.setInt(2, quantity);
                    stm.setInt(3, key);
                    int row = stm.executeUpdate();
                    if (row <= 0) {
                        throw new SQLException("Something wrong when redo update");
                    }
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

    public String getProductName(int productId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            String sql = "SELECT name "
                    + "FROM tblProducts "
                    + "WHERE productId = ? ";
//                    + "AND statusId = ? "
//                    + "AND expirationDate > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, productId);
//            stm.setInt(2, statusId);
//            stm.setTimestamp(3, time);
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
