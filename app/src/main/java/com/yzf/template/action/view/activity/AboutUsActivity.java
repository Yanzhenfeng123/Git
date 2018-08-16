package com.yzf.template.action.view.activity;

import com.yzf.template.R;
import com.yzf.template.base.HttpToolBarActivity;

public class AboutUsActivity extends HttpToolBarActivity {


    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        setToolBarTitle("关于我们");
    }
}
