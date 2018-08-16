package com.yzf.template.login.presenter;

import android.content.Context;

import com.yzf.core.http.listener.HttpOnNextListener;
import com.yzf.core.http.subscribers.BaseSubscriber;
import com.yzf.core.utils.SharedPreferencesUtils;
import com.yzf.template.login.model.ILoginWxModel;
import com.yzf.template.login.model.bean.WxLoginBean;
import com.yzf.template.login.model.impl.LoginWxModel;
import com.yzf.template.login.view.ILoginWXView;

public class LoginWxPresenter {

    private ILoginWXView iLoginWXView;
    private ILoginWxModel iLoginWxModel;

    public LoginWxPresenter(ILoginWXView iLoginWXView) {
        this.iLoginWXView = iLoginWXView;
    }

    public void wxLogin(String openid) {
        iLoginWxModel = new LoginWxModel();
        iLoginWxModel.wxLogin(openid).compose(iLoginWXView.bindRecycler()).subscribe(new BaseSubscriber(new HttpOnNextListener<WxLoginBean>() {
            @Override
            public void OnNext(WxLoginBean bean) {
                iLoginWXView.loadingFinished();
//                AppUtil.saveUserInfo((Context) iLoginWXView, bean.getUserBean());
                SharedPreferencesUtils.put((Context) iLoginWXView, SharedPreferencesUtils.USE_ID, bean.getUserid());
                iLoginWXView.WxLoginSuccess(bean);
            }
        }, iLoginWXView));
    }

}
