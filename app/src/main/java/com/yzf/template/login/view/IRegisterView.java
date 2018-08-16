package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;

public interface IRegisterView  extends HttpView{

    void registerSuccess(String msg);

    void registerFailed(String msg);
}
