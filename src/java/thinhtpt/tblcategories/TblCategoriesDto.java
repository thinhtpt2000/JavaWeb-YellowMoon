/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblcategories;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblCategoriesDto implements Serializable {

    private int categoryId;
    private String name;

    public TblCategoriesDto() {
    }

    public TblCategoriesDto(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
