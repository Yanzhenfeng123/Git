package com.yzf.template;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.hawk.Hawk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.vondear.rxtool.RxTool;
import com.yzf.core.base.BaseApplication;
import com.yzf.template.common.Constants;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

public class MyApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        //设置该CrashHandler为程序的默认处理器
//        UnCeHandler catchExcep = new UnCeHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        UMConfigure.init(this, "5b5930fd8f4a9d492f000080"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        //SharedPreferences
        Hawk.init(this).build();
        //换皮肤
        SkinCompatManager.withoutActivity(this)                         // Basic Widget support
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // Disable statusBarColor skin support，default true   [selectable]
                .setSkinWindowBackgroundEnable(false)                   // Disable windowBackground skin support，default true [selectable]
                .loadSkin();
        //切换为默认皮肤
        try {
            if (!(boolean) Hawk.get(Constants.MODEL)) {
                SkinCompatManager.getInstance().restoreDefaultTheme();
            } else {
                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // load by suffix
            }
        } catch (Exception e) { // 默认加载默认模式
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
        init();
    }

    private void init() {
        PlatformConfig.setWeixin("wx78bbb649ae5bc2ab", "df92e31dc956b7daa378e91341f906ce");
        PlatformConfig.setQQZone("1107227886", "OHw8dsOPvArEt6wc");
        PlatformConfig.setSinaWeibo("3444379865", "0d83f623f83b55d876ee6d9d3e768f79", "http://sns.whalecloud.com");
    }
}
