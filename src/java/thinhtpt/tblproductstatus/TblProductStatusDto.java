/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblproductstatus;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblProductStatusDto implements Serializable {

    private int statusId;
    private String name;

    public TblProductStatusDto() {
    }

    public TblProductStatusDto(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
