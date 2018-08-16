package com.yzf.template.action.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.core.utils.DataCleanManager;
import com.yzf.template.R;
import com.yzf.template.action.view.activity.FeedBackActivity;
import com.yzf.template.action.view.activity.MessageActivity;
import com.yzf.template.action.view.activity.SettingActivity;
import com.yzf.template.action.view.adapter.Info.MineInfo;
import com.yzf.template.action.view.adapter.VipAdapter;
import com.yzf.template.base.BaseRecyclerAdapter;
import com.yzf.template.base.HttpToolBarFragment;
import com.yzf.template.common.Constants;
import com.yzf.template.glide.ImageLoader;
import com.yzf.template.login.view.activity.LoginActivity;
import com.yzf.template.main.MainActivity;
import com.yzf.template.utils.AppUtil;
import com.yzf.template.view.BasicBottomDialog;
import com.yzf.template.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import skin.support.SkinCompatManager;

public class VipFragment extends HttpToolBarFragment {

    @BindView(R.id.user_img)
    RoundImageView userImg;
    @BindView(R.id.user_background)
    ImageView userBackground;
    @BindView(R.id.autograph)
    TextView autograph;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_info)
    RelativeLayout userInfo;
    @BindView(R.id.vip_Recycler)
    RecyclerView vip_Recy;
    @BindView(R.id.without_login)
    TextView withoutLogin;
    private boolean isNight;
    private List<MineInfo> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vip;
    }

    @Override
    protected void initView() {
        setToolBarTitle("我的");
        setSubImg(R.mipmap.setting);
        setSubClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        //初始化个人信息
        initUserMessage();

        getToolbar().setNavigationIcon(R.mipmap.mine_news);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });
        try {
            isNight = Hawk.get(Constants.MODEL);
        } catch (Exception e) {
            isNight = false;
        }
        SwitchSkin(isNight);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        MineInfo info = new MineInfo();
        info.setTitle("夜间模式");
        info.setResId(R.mipmap.icon_night);
        list.add(info);
        info = new MineInfo();
        info.setResId(R.mipmap.icon_cache);
        info.setTitle("清除缓存");
        info.setContent(DataCleanManager.getTotalCacheSize(getActivity()));
        list.add(info);
        info = new MineInfo();
        info.setResId(R.mipmap.icon_feedback);
        info.setTitle("问题反馈");
        list.add(info);
        info = new MineInfo();
        info.setResId(R.mipmap.icon_author);
        info.setTitle("关于作者");
        list.add(info);
        info = new MineInfo();
        info.setResId(R.mipmap.icon_author);
        info.setTitle("检查自动更新");
        list.add(info);
        final VipAdapter adapter = new VipAdapter(getActivity(), R.layout.item_mine);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        vip_Recy.setLayoutManager(manager);
        vip_Recy.setItemAnimator(new DefaultItemAnimator());
        vip_Recy.setAdapter(adapter);
        adapter.updateWithClear(list);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, final int position) {
                switch (position) {
                    case 0:
                        //昼夜模式切换
                        if (AppUtil.isFastDoubleClick(500)) {
                            return;
                        }

                        if (AppUtil.isExamined(getActivity())) {
                            switchSkin();
                            adapter.setNight(isNight);
                        }
                        break;
                    case 1:
                        if (AppUtil.isFastDoubleClick(500)) {
                            return;
                        }
                        if (pd==null)
                        pd = CustomProgressDialog.createDialog(getActivity());
                        DataCleanManager.clearAllCache(getActivity());
                        Observable.timer(1000, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {

                                            if (pd != null)
                                            pd.dismiss();
                                        list.get(position).setContent(DataCleanManager.getTotalCacheSize(getActivity()));
                                        adapter.updateWithClear(list);
                                    }
                                });
                        break;
                    case 2:
                        if (AppUtil.isFastDoubleClick(500)) {
                            return;
                        }
                        if (AppUtil.isExamined(getActivity())) {
                            startActivity(new Intent(getActivity(), FeedBackActivity.class));
                        }
                        break;
                    case 3:
                        if (AppUtil.isFastDoubleClick(500)) {
                            return;
                        }
                        if (AppUtil.isExamined(getActivity())) {

                        }
                        break;
                    case 4:
                        if (AppUtil.isFastDoubleClick(500)) {
                            return;
                        }
                        if (AppUtil.isExamined(getActivity())) {
                            BasicBottomDialog.Builder builder1 = new BasicBottomDialog.Builder(getActivity());
                            builder1
                                    .setPositiveButton(new AlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            AppUtil.clearUserInfo(getActivity());
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("取消", new AlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder1.create().show();
                        }
                        break;
                }
            }
        });

    }

    private void initUserMessage() {
        if (AppUtil.getUserId(getActivity()) == null) {
            userName.setVisibility(View.GONE);
            autograph.setVisibility(View.GONE);
            withoutLogin.setVisibility(View.VISIBLE);
            ImageLoader.loadCircleImage(getActivity(), "http://img2.imgtn.bdimg.com/it/u=2905649902,1519096845&fm=214&gp=0.jpg", userImg, R.mipmap.default_head_comment);
        } else {
            userName.setVisibility(View.VISIBLE);
            autograph.setVisibility(View.VISIBLE);
            withoutLogin.setVisibility(View.GONE);
//            userName.setText(AppUtil.getUserInfo(getActivity()).getUserName() == null ? "yzf" : AppUtil.getUserInfo(getActivity()).getUserName());
//            autograph.setText(AppUtil.getUserInfo(getActivity()).getAutograph() == null ? "哈哈" : AppUtil.getUserInfo(getActivity()).getAutograph());
//            ImageLoader.loadCircleImage(getActivity(), AppUtil.getUserInfo(getActivity()).getUserImg() == null ? "http://img2.imgtn.bdimg.com/it/u=2905649902,1519096845&fm=214&gp=0.jpg" : AppUtil.getUserInfo(getActivity()).getUserImg(), userImg, R.mipmap.default_head_comment);
            userName.setText("yzf");
            autograph.setText("15104422827");
            ImageLoader.loadCircleImage(getActivity(), "http://img2.imgtn.bdimg.com/it/u=2905649902,1519096845&fm=214&gp=0.jpg", userImg, R.mipmap.default_head_comment);
        }
    }

    @OnClick({R.id.user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info:
                if (AppUtil.isFastDoubleClick(500)) {
                    return;
                }
                if (withoutLogin.getVisibility() == View.VISIBLE && AppUtil.getUserInfo(getActivity()) == null)
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                else
                    startActivity(new Intent(getActivity(), MessageActivity.class));

                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserMessage();
    }

    /**
     * 更换皮肤
     */
    private void switchSkin() {
        if (isNight) {
            ((MainActivity) getActivity()).setSwitchNightVisible(View.GONE, isNight);
            SkinCompatManager.getInstance().restoreDefaultTheme();
            Hawk.put(Constants.MODEL, Constants.DEFAULT_MODEL);
            SwitchSkin(!isNight);
        } else {
            SkinCompatManager.getInstance().loadSkin("night", new SkinCompatManager.SkinLoaderListener() {
                @Override
                public void onStart() {
                    ((MainActivity) getActivity()).setSwitchNightVisible(View.GONE, isNight);
                }

                @Override
                public void onSuccess() {
                    Hawk.put(Constants.MODEL, Constants.NIGHT_MODEL);
                    SwitchSkin(isNight);
                }

                @Override
                public void onFailed(String errMsg) {
                    showMsg("更换失败");
                }
            }, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // load by suffix
        }
        isNight = !isNight;
    }

    private void SwitchSkin(boolean isNight) {
        ((MainActivity) getActivity()).switchModel();
    }

}
