package com.yzf.template.action.view.activity;

import com.yzf.template.R;
import com.yzf.template.base.ToolBarActivity;

public class CollectionActivity extends ToolBarActivity {


    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        setToolBarTitle("收藏");
    }
}
