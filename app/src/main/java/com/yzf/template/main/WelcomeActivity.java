package com.yzf.template.main;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yzf.core.base.BaseActivity;
import com.yzf.core.utils.SharedPreferencesUtils;
import com.yzf.template.R;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    private Handler handler;
    private CloseAble closeAble;

    @Override
    protected void beforeLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        overridePendingTransition(android.R.anim.fade_in, 0);
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).transform(new BrightnessFilterTransformation());
        Glide.with(this).load(R.mipmap.activity_start).apply(options).into(img);//跳过内存缓存
        finishingActivity();
    }

    private void finishingActivity() {

        handler = new Handler(this.getMainLooper());
        closeAble = new CloseAble();
        handler.postDelayed(closeAble, 2000);
//        Observable.timer(2000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        //判断是不是第一次打开app
//                        if ((boolean) SharedPreferencesUtils.get(WelcomeActivity.this, SharedPreferencesUtils.FIRST_TIME, true)) {
//                            startActivity(new Intent(WelcomeActivity.this, FirstStartActivity.class));
//                        } else {
//                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                            overridePendingTransition(0, android.R.anim.fade_out);
//                        }
//                        finish();
//                    }
//                });
    }

    public class CloseAble implements Runnable {

        @Override
        public void run() {
            //判断是不是第一次打开app
            if ((boolean) SharedPreferencesUtils.get(WelcomeActivity.this, SharedPreferencesUtils.FIRST_TIME, true)) {
                startActivity(new Intent(WelcomeActivity.this, FirstStartActivity.class));
            } else {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                overridePendingTransition(0, android.R.anim.fade_out);
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(closeAble);
    }
}
