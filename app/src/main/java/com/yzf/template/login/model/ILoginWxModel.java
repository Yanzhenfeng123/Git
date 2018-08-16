package com.yzf.template.login.model;

import rx.Observable;

public interface ILoginWxModel {

   Observable wxLogin(String openid);
}
