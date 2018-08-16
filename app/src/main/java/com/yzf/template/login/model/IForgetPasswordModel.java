package com.yzf.template.login.model;

import rx.Observable;

public interface IForgetPasswordModel {

    Observable getCode(String phone, String type);

    Observable retryRegister(String phone, String pwd, String code);
}
