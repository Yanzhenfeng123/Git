package com.yzf.template.action.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.template.R;
import com.yzf.template.action.view.activity.DetailScaleActivity;
import com.yzf.template.action.view.activity.DialogActivity;
import com.yzf.template.base.HttpToolBarFragment;
import com.yzf.template.glide.GlideApp;
import com.yzf.template.glide.ImageLoader;
import com.yzf.template.utils.AppUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;

public class MoneyFragment extends HttpToolBarFragment {
    @BindView(R.id.alipy)
    TextView tv_Alipy;
    @BindView(R.id.wechat)
    TextView tv_Wechat;
    @BindView(R.id.load_img)
    ImageView img;
    CustomProgressDialog dialog;
    ProgressManager manager;

    private String url = new String("http://m1.sinaimg.cn/maxwidth.640/m1.sinaimg.cn/b7c4c0be576dc14e642a8eb00cae94fe_362_500.jpg");


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money;
    }

    @Override
    protected void initView() {
        setToolBarTitle("找投资");
        ProgressManager.getInstance().addRequestListener(url, getGlideListener());
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img);
//        ImageLoader.getInstance().loadImage(getActivity(), url, img);
//        if (dialog == null) {
//            dialog = CustomProgressDialog.createDialog(getActivity());
//        }
    }

    @OnClick({R.id.alipy, R.id.wechat})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alipy:
                startActivity(new Intent(getActivity(), DialogActivity.class));
                break;
            case R.id.wechat:
//                initListener(url);
//                GlideApp.with(this)
//                        .load(url)
//                        .centerCrop()
//                        .placeholder(R.color.colorPrimary)
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .into(img);
                startActivity(new Intent(getActivity(), DetailScaleActivity.class));
                break;
        }
    }

    private void initListener(String url) {
        ImageLoader.getInstance().loadImage(getActivity(), url, img, AppUtil.dip2px(getActivity(), AppUtil.getScreenWith()), AppUtil.dip2px(getActivity(), AppUtil.getScreenWith()));
        if (dialog == null) {
            dialog = CustomProgressDialog.createDialog(getActivity());
        }
    }

    @NonNull
    private ProgressListener getGlideListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                int progress = progressInfo.getPercent();
                Log.d("yy", "--Glide-- " + progress + " %  " + progressInfo.getSpeed() + " byte/s  " + progressInfo.toString());
//                if (progress >= 0) {
//                    if (dialog == null) {
//                        dialog = CustomProgressDialog.createDialog(getActivity());
//                    }
//                }
                if (progressInfo.isFinish()) {
                    if (dialog != null)
                        dialog.dismiss();
                }
            }

            @Override
            public void onError(long id, Exception e) {
                if (dialog != null)
                    dialog.dismiss();
            }
        };
    }

}
