/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorderdetail;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderDetailDtoExtends extends TblOrderDetailDto {

    private String productName;
    private long subTotal;

    public TblOrderDetailDtoExtends() {
        super();
    }

    public TblOrderDetailDtoExtends(int detailId, int productId, int price, int quantity) {
        super(detailId, productId, price, quantity);
        this.subTotal = price * quantity;
    }

    public TblOrderDetailDtoExtends(String productName, int detailId, int productId, int price, int quantity) {
        super(detailId, productId, price, quantity);
        this.productName = productName;
        this.subTotal = price * quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(long subTotal) {
        this.subTotal = subTotal;
    }

}
