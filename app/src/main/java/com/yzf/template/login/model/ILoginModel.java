package com.yzf.template.login.model;

import rx.Observable;

public interface ILoginModel {

    Observable login(String phone, String password);

    Observable getCode(String phone, String type);

    Observable register(String phone, String pwd, String code);
}
