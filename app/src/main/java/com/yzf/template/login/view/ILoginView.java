package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;


public interface ILoginView extends HttpView {

    Boolean validate();

    void loginSuccess();

    void loginFailed(String msg);

}
