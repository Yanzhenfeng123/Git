package com.yzf.template.login.view;

import com.yzf.core.base.HttpView;
import com.yzf.template.login.model.bean.WxLoginBean;

public interface ILoginWXView extends HttpView {

    void WxLoginSuccess(WxLoginBean bean);

}
