/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblpaymentmethod;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblPaymentMethodDto implements Serializable {

    private int methodId;
    private String name;

    public TblPaymentMethodDto() {
    }

    public TblPaymentMethodDto(int methodId, String name) {
        this.methodId = methodId;
        this.name = name;
    }

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
