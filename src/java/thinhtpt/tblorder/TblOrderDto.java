/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderDto implements Serializable {

    private String orderId;
    private int userId;
    private long total;
    private Timestamp date;
    private String userName;
    private String userAddress;
    private String userPhone;
    private int paymentMethod;
    private int paymentStatus;

    public TblOrderDto() {
    }

    public TblOrderDto(String orderId, int userId, long total, Timestamp date, String userName, String userAddress, String userPhone, int paymentMethod, int paymentStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.total = total;
        this.date = date;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public TblOrderDto(int userId, long total, Timestamp date, String userName, String userAddress, String userPhone, int paymentMethod, int paymentStatus) {
        this.userId = userId;
        this.total = total;
        this.date = date;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public TblOrderDto(long total, Timestamp date, String userName, String userAddress, String userPhone, int paymentMethod, int paymentStatus) {
        this.total = total;
        this.date = date;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
