package com.yzf.template.http;


import android.support.annotation.NonNull;

import com.yzf.core.base.BaseApplication;
import com.yzf.core.http.api.HttpResult;
import com.yzf.core.http.cookie.CacheInterceptor;
import com.yzf.core.http.exception.ApiException;
import com.yzf.core.utils.NetUtil;
import com.yzf.template.login.model.bean.CheckBean;
import com.yzf.template.login.model.bean.LoginBean;
import com.yzf.template.login.model.bean.WxLoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Http服务类
 *
 * @author Yzf
 * @date 2018-7-24 10:32:52
 */

public class HttpManager {

    //服务器请求地址
    public static final String BASE_URL = "http://47.93.33.136/app/";

    /**
     * volatile
     * 　1.保证此变量对所有的线程的可见性
     * 　2.禁止指令重排序优化。
     * volatile 性能：
     * 　　volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行。
     */
    private volatile static HttpManager INSTANCE;

    public volatile static String msg = "";

    private Service service;

    /**
     * 设置超时时间
     * 默认6秒
     */
    public static int CONNECTION_TIME = 6;

    /**
     * 设置缓存有效期
     * 两天
     */
    private static final long CACHE_SATLE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存Cache-Control设置  为if-only-chache时只查询缓存不请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指 客户机可以接收超出超时期间的响应消息。如果指定max-stale的值 那么客户机可以接收超出超时期指定值之内的响应消息
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_SATLE_SEC;

    /**
     * 查询网络的Cache-Control设置  头部Cache-Control 设为max-age=0 时  不会使用缓存直接请求服务器
     * 如果请求服务器 并且服务器在a时刻返回相应结果 那么在max-age规定的秒数内 浏览器不会再向服务器发送请求  数据直接由缓存直接返回
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    /**
     * 是否显示log
     */
    private static final Boolean SHOW_LOG = true;

    /**
     * 根据网络获取缓存策略
     */
    @NonNull
    private String getChaControl() {
        return NetUtil.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    //构造私有方法
    private HttpManager(String baseUrl) {
        //手动创建一个OkTttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (SHOW_LOG) {
            //输出打印log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        builder.connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS);
//        builder.readTimeout(40, TimeUnit.SECONDS);
        builder.addInterceptor(new CacheInterceptor());
        /**
         * 缓存位置和大小
         */
        builder.cache(new Cache(BaseApplication.app.getCacheDir(), 10 * 1024 * 1024));

        /**
         * 创建retrofit对象
         */
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        //关联service
        service = retrofit.create(Service.class);
    }

    //获取单例 无缓存
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            /*
            synchronized关键字是用来控制线程同步的，就是在多线程的环境下，控制synchronized代码段不被多个线程同时执行。
             */
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager(BASE_URL);
                }
            }
        }
        return INSTANCE;
    }




    /**
     * 用来统一处理 Http的resultCode 并将 HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的类型  也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            msg = tHttpResult.getMsg().toString();
            if (!"success".equals(tHttpResult.getState())) {
                throw new ApiException(tHttpResult.getMsg());
            }
            return tHttpResult.getData();
        }
    }

    /**
     * 用RxAndroid 来统一处理网络请求线程
     */
    public Observable initObservable(Observable observable) {
        /*失败后的retry配置*/
//        return observable.retryWhen(new RetryWhenNetworkException())
//                /*http请求线程*/
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                /*回调线程*/
//                .observeOn(AndroidSchedulers.mainThread());
        return observable
                //http请求线程
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //回调线程
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 用来配置请求参数json
     */
    public RequestBody initRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public MultipartBody.Part getRequstBody(File file) {
        if (file == null) {
            return null;
        }
        //create Request from file
        RequestBody requessFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requessFile);
        return body;
    }


    /**
     * 登陆
     *
     * @param phone    账号
     * @param password 密码
     */
    public Observable userLogin(String phone, String password) {

        JSONObject json = new JSONObject();
        try {
            json.put("pwd", password);
            json.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.userLogin(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<LoginBean>());

        return initObservable(observable);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 手机号
     * @param type  类型 //1 注册获取  2 找回密码获取 3绑定获取 4 更换手机号
     */
    public Observable getCode(String phone, String type) {

        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
            json.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.getCode(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<CheckBean>());

        return initObservable(observable);
    }

    /**
     * 微信登录
     *
     * @param openid

     * @return
     */
    public Observable otherLogin(String openid) {
        JSONObject json = new JSONObject();
        try {
            json.put("openid", openid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.wxLogin(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<WxLoginBean>());

        return initObservable(observable);
    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param code
     * @return
     */
    public Observable register(String phone, String pwd, String code) {
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("pwd", pwd);
            json.put("smsCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.userRegister(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<CheckBean>());

        return initObservable(observable);
    }

    /**
     * 修改密码
     * @param phone
     * @param pwd
     * @param code
     * @return
     */
    public Observable retryRegister(String phone, String pwd, String code) {
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("newpwd", pwd);
            json.put("smsCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.resetPassword(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<CheckBean>());

        return initObservable(observable);
    }

    /**
     * 微信绑定手机号 WxBinding
     * @param openid
     * @param phone
     * @param pwd
     * @param code
     * @param img
     * @return
     */
    public Observable bindPhoNub(String openid, String phone, String pwd, String code, String img) {
        JSONObject json = new JSONObject();
        try {
            json.put("openid", openid);
            json.put("phone", phone);
            json.put("pwd", pwd);
            json.put("code", code);
            json.put("img", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = service.bindPhoNub(initRequestBody(json.toString()))
                /*结果判断*/
                .map(new HttpResultFunc<WxLoginBean>());

        return initObservable(observable);
    }

}
