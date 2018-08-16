package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;

public interface IBindPhoNubView extends HttpView {

    void isBindPhoNubSuccess();

    void isBindPhoNubFailed(String msg);

    void getCodeSuccess();

    void getCodeFailed(String str);

}
