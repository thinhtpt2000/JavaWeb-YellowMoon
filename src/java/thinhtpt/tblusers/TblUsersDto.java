/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.tblusers;

import java.io.Serializable;

/**
 *
 * @author ThinhTPT
 */
public class TblUsersDto implements Serializable {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private int roleId;
    private String googleId;

    public TblUsersDto() {
    }

    public TblUsersDto(int userId, String username, String fullName, int roleId) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
    }

    public TblUsersDto(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public TblUsersDto(int userId, String username, String fullName, int roleId, String googleId) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
        this.googleId = googleId;
    }

    public TblUsersDto(String username, String fullName, int roleId, String googleId) {
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
        this.googleId = googleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
