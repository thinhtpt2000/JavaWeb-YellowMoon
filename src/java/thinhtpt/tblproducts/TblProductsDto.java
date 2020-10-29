/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblproducts;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ThinhTPT
 */
public class TblProductsDto implements Serializable {

    private int productId;
    private String name;
    private String description;
    private String image;
    private int price;
    private int quantity;
    private int categoryId;
    private Timestamp createDate;
    private Timestamp expirationDate;
    private int statusId;

    public TblProductsDto() {
    }

    public TblProductsDto(int productId, String name, String description, String image,
            int price, int categoryId, Timestamp createDate, Timestamp expirationDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.categoryId = categoryId;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
    }

    public TblProductsDto(int productId, String name, String description, String image, int price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public TblProductsDto(int productId, String name, String image, int price, int quantity,
            int categoryId, Timestamp createDate, Timestamp expirationDate, int statusId) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.statusId = statusId;
    }

    public TblProductsDto(int productId, String name, int price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public TblProductsDto(String name, String description, String image, int price,
            int quantity, int categoryId, int statusId, Timestamp createDate, Timestamp expirationDate) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.statusId = statusId;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}
