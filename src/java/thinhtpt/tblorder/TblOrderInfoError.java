/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblorder;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblOrderInfoError implements Serializable {

    private String nameErr;
    private String phoneErr;
    private String addressErr;
    private String methodErr;

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    public String getAddressErr() {
        return addressErr;
    }

    public void setAddressErr(String addressErr) {
        this.addressErr = addressErr;
    }

    public String getMethodErr() {
        return methodErr;
    }

    public void setMethodErr(String methodErr) {
        this.methodErr = methodErr;
    }

}
