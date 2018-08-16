package com.yzf.template.login.model.impl;

import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.ILoginWxModel;

import rx.Observable;


public class LoginWxModel implements ILoginWxModel {


    @Override
    public Observable wxLogin(String openid) {
        return HttpManager.getInstance().otherLogin(openid);
    }
}
