package com.example.expressclient.bean;

import java.util.Date;

public class User {
    int userid;
    String username;
    String password;
    String role;
    Date createTime;
    Date updateTime;
    String telphone;

    public User(String username,String password,String telphone){
        this.username = username;
        this.password = password;
        this.telphone = telphone;
    }
    public User(int userid, String username, String telphone, String password, String role, Date createTime, Date updateTime) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.telphone = telphone;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
