package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;

public interface IGetCodeView extends HttpView {

    Boolean isPhoNub();

    void getCodeSuccess();

    void getCodeFailed(String str);


}
