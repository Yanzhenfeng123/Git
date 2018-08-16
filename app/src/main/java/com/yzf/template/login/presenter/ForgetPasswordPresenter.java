package com.yzf.template.login.presenter;

import com.yzf.core.http.listener.HttpOnNextListener;
import com.yzf.core.http.subscribers.BaseSubscriber;
import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.IForgetPasswordModel;
import com.yzf.template.login.model.bean.CheckBean;
import com.yzf.template.login.model.impl.ForgetPasswordModel;
import com.yzf.template.login.view.IForgetPasswordView;

public class ForgetPasswordPresenter {

    private IForgetPasswordModel mIForgetPasswordModel;

    private IForgetPasswordView mIForgetpasswordView;

    public ForgetPasswordPresenter(IForgetPasswordView iForgetPasswordView) {
        this.mIForgetpasswordView = iForgetPasswordView;
    }

    public void getCode(String phone, final String type) {
        mIForgetPasswordModel = new ForgetPasswordModel();
        mIForgetPasswordModel.getCode(phone, type).compose(mIForgetpasswordView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<CheckBean>() {
            @Override
            public void OnNext(CheckBean bean) {
                mIForgetpasswordView.loadingFinished();
                if (bean.getResult().equals("1"))
                    mIForgetpasswordView.getCodeSuccess();
                else
                    mIForgetpasswordView.getCodeFailed(HttpManager.msg);
            }
        }, mIForgetpasswordView));
    }

    public void resetPassword(String phone,String pwd,String code){
        mIForgetPasswordModel = new ForgetPasswordModel();
        mIForgetPasswordModel.retryRegister(phone,pwd,code).compose(mIForgetpasswordView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<CheckBean>() {
            @Override
            public void OnNext(CheckBean bean) {
                mIForgetpasswordView.loadingFinished();
                if (bean.getResult().equals("1")){
                    mIForgetpasswordView.resetPasswordSuccess(HttpManager.msg);
                }else {
                    mIForgetpasswordView.resetPasswordFailed(HttpManager.msg);
                }
            }
        },mIForgetpasswordView));
    }
}
