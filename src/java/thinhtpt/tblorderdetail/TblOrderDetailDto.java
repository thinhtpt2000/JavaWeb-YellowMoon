/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorderdetail;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderDetailDto implements Serializable {

    private int detailId;
    private int orderId;
    private int productId;
    private int price;
    private int quantity;

    public TblOrderDetailDto() {
    }

    public TblOrderDetailDto(int detailId, int productId, int price, int quantity) {
        this.detailId = detailId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
