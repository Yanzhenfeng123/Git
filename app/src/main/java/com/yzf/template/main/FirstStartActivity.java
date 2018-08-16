package com.yzf.template.main;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.yzf.core.base.BaseActivity;
import com.yzf.core.utils.SharedPreferencesUtils;
import com.yzf.template.R;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 引导页
 *
 * @author Yzf
 * @date 2018-7-27 11:31:35
 */
public class FirstStartActivity extends BaseActivity {

    @BindView(R.id.banner_guide_background)
    BGABanner bg_banner;
    @BindView(R.id.banner_guide_foreground)
    BGABanner bf_banner;


    @Override
    protected void beforeLayout() {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        SharedPreferencesUtils.put(this, SharedPreferencesUtils.FIRST_TIME, false);
        setListener();
        progressLogic();
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        bf_banner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(FirstStartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void progressLogic() {
        // 设置数据源
        bg_banner.setData(R.mipmap.uoko_guide_background_1, R.mipmap.uoko_guide_background_2, R.mipmap.uoko_guide_background_3);

        bf_banner.setData(R.mipmap.uoko_guide_foreground_1, R.mipmap.uoko_guide_foreground_2, R.mipmap.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        bg_banner.setBackgroundResource(android.R.color.white);
    }
}
