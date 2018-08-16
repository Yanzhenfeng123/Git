package com.yzf.template.action.view.activity;

import com.yzf.template.R;
import com.yzf.template.base.HttpToolBarActivity;

public class FeedBackActivity extends HttpToolBarActivity {


    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        setToolBarTitle("意见反馈");
    }
}
