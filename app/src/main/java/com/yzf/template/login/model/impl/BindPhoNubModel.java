package com.yzf.template.login.model.impl;


import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.IBindPhoNubModel;

import rx.Observable;


public class BindPhoNubModel implements IBindPhoNubModel {
    @Override
    public Observable bindPhoNub(String openid, String phone, String pwd, String code, String img) {
        return HttpManager.getInstance().bindPhoNub(openid, phone, pwd, code, img);
    }

    @Override
    public Observable getCode(String phone, String code) {
        return HttpManager.getInstance().getCode(phone, code);
    }
}
