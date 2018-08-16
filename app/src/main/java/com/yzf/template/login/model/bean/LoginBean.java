package com.yzf.template.login.model.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {

    private String result;
    private String userid;

    private UserBean userBean;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {

        this.userid = userid;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
