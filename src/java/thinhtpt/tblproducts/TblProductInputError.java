/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblproducts;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblProductInputError implements Serializable {

    private String nameErr;
    private String priceErr;
    private String quantityErr;
    private String createDateErr;
    private String expDateErr;
    private String categoryErr;
    private String statusErr;
    private String imgErr;
    private String descriptionErr;

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getQuantityErr() {
        return quantityErr;
    }

    public void setQuantityErr(String quantityErr) {
        this.quantityErr = quantityErr;
    }

    public String getCreateDateErr() {
        return createDateErr;
    }

    public void setCreateDateErr(String createDateErr) {
        this.createDateErr = createDateErr;
    }

    public String getExpDateErr() {
        return expDateErr;
    }

    public void setExpDateErr(String expDateErr) {
        this.expDateErr = expDateErr;
    }

    public String getCategoryErr() {
        return categoryErr;
    }

    public void setCategoryErr(String categoryErr) {
        this.categoryErr = categoryErr;
    }

    public String getStatusErr() {
        return statusErr;
    }

    public void setStatusErr(String statusErr) {
        this.statusErr = statusErr;
    }

    public String getImgErr() {
        return imgErr;
    }

    public void setImgErr(String imgErr) {
        this.imgErr = imgErr;
    }

    public String getDescriptionErr() {
        return descriptionErr;
    }

    public void setDescriptionErr(String descriptionErr) {
        this.descriptionErr = descriptionErr;
    }

}
