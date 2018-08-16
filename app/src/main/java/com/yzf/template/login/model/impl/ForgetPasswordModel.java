package com.yzf.template.login.model.impl;

import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.IForgetPasswordModel;

import rx.Observable;

public class ForgetPasswordModel implements IForgetPasswordModel {
    @Override
    public Observable getCode(String phone, String type) {
        return HttpManager.getInstance().getCode(phone,type);
    }

    @Override
    public Observable retryRegister(String phone, String pwd, String code) {
        return HttpManager.getInstance().retryRegister(phone,pwd,code);
    }
}
