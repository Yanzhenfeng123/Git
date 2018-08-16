package com.yzf.template.login.presenter;

import android.content.Context;

import com.yzf.core.http.listener.HttpOnNextListener;
import com.yzf.core.http.subscribers.BaseSubscriber;
import com.yzf.template.http.HttpManager;
import com.yzf.template.login.model.IBindPhoNubModel;
import com.yzf.template.login.model.bean.CheckBean;
import com.yzf.template.login.model.bean.WxLoginBean;
import com.yzf.template.login.model.impl.BindPhoNubModel;
import com.yzf.template.login.view.IBindPhoNubView;
import com.yzf.template.utils.AppUtil;

public class BindPhoNubPresenter {

    private IBindPhoNubView mIBindPhoNumView;

    private IBindPhoNubModel mIBindPhoNumModel;

    public BindPhoNubPresenter(IBindPhoNubView iBindPhoNubView) {
        this.mIBindPhoNumView = iBindPhoNubView;
    }

    public void bindPhoNub(String openid, String phone, String pwd, String code, String img) {
        mIBindPhoNumModel = new BindPhoNubModel();
        mIBindPhoNumModel.bindPhoNub(openid, phone, pwd, code, img).compose(mIBindPhoNumView.bindRecycler()).subscribe(new BaseSubscriber(new HttpOnNextListener<WxLoginBean>() {
            @Override
            public void OnNext(WxLoginBean bean) {
                mIBindPhoNumView.loadingFinished();
                if (bean.getType().equals(1)){
                    mIBindPhoNumView.isBindPhoNubSuccess();
                    AppUtil.saveUserInfo((Context) mIBindPhoNumView, bean.getUserBean());
                }else {
                    mIBindPhoNumView.isBindPhoNubFailed(HttpManager.msg);
                }
            }
        }, mIBindPhoNumView));
    }

    public void getCode(String phone,String type){
        mIBindPhoNumModel = new BindPhoNubModel();
        mIBindPhoNumModel.getCode(phone, type).compose(mIBindPhoNumView.bindRecycler()).subscribe(new BaseSubscriber<>(new HttpOnNextListener<CheckBean>() {
            @Override
            public void OnNext(CheckBean bean) {
                mIBindPhoNumView.loadingFinished();
                if (bean.getResult().equals("1"))
                    mIBindPhoNumView.getCodeSuccess();
                else
                    mIBindPhoNumView.getCodeFailed(HttpManager.msg);
            }
        }, mIBindPhoNumView));
    }
}
