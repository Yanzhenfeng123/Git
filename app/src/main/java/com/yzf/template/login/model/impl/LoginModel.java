package com.yzf.template.login.model.impl;

import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.ILoginModel;

import rx.Observable;


public class LoginModel implements ILoginModel {
    @Override
    public Observable login(String phone, String password) {
        return HttpManager.getInstance().userLogin(phone, password);
    }

    @Override
    public Observable getCode(String phone, String type) {
        return HttpManager.getInstance().getCode(phone, type);
    }

    @Override
    public Observable register(String phone, String pwd, String code) {
        return HttpManager.getInstance().register(phone, pwd, code);
    }
}
