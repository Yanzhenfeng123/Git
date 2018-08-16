package com.yzf.template.login.presenter;

import android.content.Context;

import com.yzf.core.http.listener.HttpOnNextListener;
import com.yzf.core.http.subscribers.BaseSubscriber;
import com.yzf.core.utils.SharedPreferencesUtils;
import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.ILoginModel;
import com.yzf.template.login.model.bean.CheckBean;
import com.yzf.template.login.model.bean.LoginBean;
import com.yzf.template.login.model.impl.LoginModel;
import com.yzf.template.login.view.IGetCodeView;
import com.yzf.template.login.view.ILoginView;
import com.yzf.template.login.view.IRegisterView;

public class LoginPresenter {

    private ILoginView mILoginView;

    private ILoginModel mILoginModel;

    private IGetCodeView mIGetCodeView;

    private IRegisterView mIRegisterView;

    public LoginPresenter(ILoginView mView, IGetCodeView mCodeView, IRegisterView iRegisterView) {
        this.mILoginView = mView;
        this.mIGetCodeView = mCodeView;
        this.mIRegisterView = iRegisterView;
    }

    public void login(String phone, String name) {
        mILoginModel = new LoginModel();
        mILoginModel.login(phone, name).compose(mILoginView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<LoginBean>() {
            @Override
            public void OnNext(LoginBean bean) {
                mILoginView.loadingFinished();
                if (bean.getResult().equals("1")) {
//                    AppUtil.saveUserInfo((Context) mILoginView, bean.getUserBean());
                    SharedPreferencesUtils.put((Context) mILoginView, SharedPreferencesUtils.USE_ID, bean.getUserid());
                    mILoginView.loginSuccess();
                } else {
                    mILoginView.loginFailed(HttpManager.msg);
                }


            }
        }, mILoginView));
    }

    public void getCode(String phone, final String type) {
        mILoginModel = new LoginModel();
        mILoginModel.getCode(phone, type).compose(mIGetCodeView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<CheckBean>() {
            @Override
            public void OnNext(CheckBean bean) {
                mIGetCodeView.loadingFinished();

                if (bean.getResult().equals("1"))
                    mIGetCodeView.getCodeSuccess();
                else
                    mIGetCodeView.getCodeFailed(HttpManager.msg);
            }
        }, mIGetCodeView));
    }

    public void register(String phone, String pwd, String code) {
        mILoginModel = new LoginModel();
        mILoginModel.register(phone, pwd, code).compose(mIRegisterView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<CheckBean>() {
            @Override
            public void OnNext(CheckBean bean) {
                mIRegisterView.loadingFinished();
                if (bean.getResult().equals("1"))
                    mIRegisterView.registerSuccess(HttpManager.msg);
                else
                    mIRegisterView.registerFailed(HttpManager.msg);
            }
        }, mIRegisterView));
    }
}
