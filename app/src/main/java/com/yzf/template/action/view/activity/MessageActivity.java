package com.yzf.template.action.view.activity;

import com.yzf.template.R;
import com.yzf.template.base.HttpToolBarActivity;

public class MessageActivity extends HttpToolBarActivity {


    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        setToolBarTitle("消息");
    }
}
