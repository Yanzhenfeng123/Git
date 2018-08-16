package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;

public interface IForgetPasswordView extends HttpView {

    Boolean isPhoNub();

    void getCodeSuccess();

    void getCodeFailed(String str);

    void resetPasswordSuccess(String msg);

    void resetPasswordFailed(String msg);

}
