package com.yzf.template.login.model;


import rx.Observable;

public interface IBindPhoNubModel {

    Observable bindPhoNub(String openid, String phone, String pwd, String code, String img);

    Observable getCode(String phone, String code);
}
