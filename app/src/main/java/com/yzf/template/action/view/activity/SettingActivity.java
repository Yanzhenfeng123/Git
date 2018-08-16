package com.yzf.template.action.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.core.utils.DataCleanManager;
import com.yzf.core.utils.SharedPreferencesUtils;
import com.yzf.template.R;
import com.yzf.template.base.HttpToolBarActivity;
import com.yzf.template.utils.AppUtil;
import com.yzf.template.view.BasicWithCloseDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SettingActivity extends HttpToolBarActivity {

    @BindView(R.id.switch_img)
    ImageView switchImg;
    @BindView(R.id.caches_size)
    TextView cachesSize;
    @BindView(R.id.clear_cache)
    RelativeLayout clearCache;
    @BindView(R.id.feedback)
    RelativeLayout feedback;
    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.about_us)
    RelativeLayout aboutUs;

    CustomProgressDialog pd;

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        // 0:允许 1：不允许
        String state = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.MESSAGE_STATE, "0");
        if ("0".equals(state)) {
            switchImg.setImageResource(R.mipmap.switch_open);
        } else if ("1".equals(state)) {
            switchImg.setImageResource(R.mipmap.switch_close);
        }

        setToolBarTitle("设置");

        cachesSize.setText(DataCleanManager.getTotalCacheSize(this));

        if (AppUtil.isExamine(this)) {
            exit.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.exit, R.id.clear_cache, R.id.feedback, R.id.about_us, R.id.switch_img})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.exit:

                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }

                BasicWithCloseDialog.Builder builder = new BasicWithCloseDialog.Builder(this);
                builder.setTitle("退出登录？")
                        .setMessage("退出后不会删除任何历史数据，下次登录依然可以使用本账号")
                        .setPositiveButton("确定", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                mPresenter.userQuit(AppUtil.getUserId(SettingActivity.this));
                                pd = CustomProgressDialog.createDialog(SettingActivity.this);

                                Observable.timer(1000, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Long>() {
                                            @Override
                                            public void call(Long aLong) {

                                                if (pd != null)
                                                    pd.dismiss();
                                                exit.setVisibility(View.GONE);
                                                SharedPreferencesUtils.put(SettingActivity.this, SharedPreferencesUtils.USE_ID, "");
                                            }
                                        });
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("取消", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

                break;
            case R.id.clear_cache:

                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }

                pd = CustomProgressDialog.createDialog(this);
                DataCleanManager.clearAllCache(this);
                Observable.timer(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {

                                if (pd != null)
                                    pd.dismiss();
                                cachesSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                            }
                        });
                break;
            case R.id.feedback:

                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }

                if (AppUtil.isExamined(this))
                    startActivity(new Intent(this, FeedBackActivity.class));

                break;
            case R.id.about_us:

                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }

                startActivity(new Intent(this, AboutUsActivity.class));
                break;

            case R.id.switch_img:
                // 0:允许 1：不允许
                String state = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.MESSAGE_STATE, "0");
                if ("0".equals(state)) {
                    state = "1";
                    switchImg.setImageResource(R.mipmap.switch_close);
                } else if ("1".equals(state)) {
                    state = "0";
                    switchImg.setImageResource(R.mipmap.switch_open);
                }
                SharedPreferencesUtils.put(this, SharedPreferencesUtils.MESSAGE_STATE, state);
                break;
            default:
                break;
        }

    }
}
