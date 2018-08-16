package com.yzf.template.http;

import com.yzf.core.http.api.HttpResult;
import com.yzf.template.login.model.bean.CheckBean;
import com.yzf.template.login.model.bean.LoginBean;
import com.yzf.template.login.model.bean.WxLoginBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface Service {

    @POST("UserLogin")
    Observable<HttpResult<LoginBean>> userLogin(@Body RequestBody route);

    @POST("WxLogin")
    Observable<HttpResult<WxLoginBean>> wxLogin(@Body RequestBody route);

    @POST("GetCode")
    Observable<HttpResult<CheckBean>> getCode(@Body RequestBody route);

    @POST("Registration")
    Observable<HttpResult<CheckBean>> userRegister(@Body RequestBody route);

    @POST("ResetPassword")
    Observable<HttpResult<CheckBean>> resetPassword(@Body RequestBody route);

    @POST("WxBinding")
    Observable<HttpResult<WxLoginBean>> bindPhoNub(@Body RequestBody route);
}
