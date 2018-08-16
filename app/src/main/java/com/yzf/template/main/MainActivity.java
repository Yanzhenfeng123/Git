package com.yzf.template.main;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.vondear.rxtool.RxBarTool;
import com.yzf.core.base.BaseActivity;
import com.yzf.core.utils.AnimalUtil;
import com.yzf.core.utils.AppManager;
import com.yzf.core.utils.ToastUtils;
import com.yzf.template.R;
import com.yzf.template.action.view.fragment.MoneyFragment;
import com.yzf.template.action.view.fragment.NewsFragment;
import com.yzf.template.action.view.fragment.VipFragment;
import com.yzf.template.common.Constants;
import com.yzf.template.view.SwitchNightRelativeLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * 主页面
 *
 * @author Yzf
 * @date 2018-7-27 15:10:38
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_one)
    LinearLayout tabOne;
    @BindView(R.id.tab_two)
    LinearLayout tabTwo;
    @BindView(R.id.tab_three)
    LinearLayout tabThree;
    @BindView(R.id.tab_four)
    LinearLayout tabFour;
    @BindView(R.id.tab_five)
    LinearLayout tabFive;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.tab_three_img)
    ImageView tabThreeImg;
    @BindView(R.id.tab_four_img)
    ImageView tabFourImg;
    @BindView(R.id.tab_five_img)
    ImageView tabFiveImg;
    @BindView(R.id.tab_one_txt)
    TextView tabOneTxt;
    @BindView(R.id.tab_two_txt)
    TextView tabTwoTxt;
    @BindView(R.id.tab_three_txt)
    TextView tabThreeTxt;
    @BindView(R.id.tab_four_txt)
    TextView tabFourTxt;
    @BindView(R.id.tab_five_txt)
    TextView tabFiveTxt;
    @BindView(R.id.tab_one_img)
    ImageView tabOneImg;
    @BindView(R.id.tab_two_img)
    ImageView tabTwoImg;
    @BindView(R.id.rl_switch_night)
    SwitchNightRelativeLayout mSwitchNight;
    @BindView(R.id.v_night)
    View NightModel;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    private boolean isExit = false;
    //个人中心
    private VipFragment vipFragment;

    //资讯
    private NewsFragment newsFragment;

    //支付页面
    private MoneyFragment moneyFragment;

    @Override
    protected void beforeLayout() {
        RxBarTool.noTitle(this);
        RxBarTool.setTransparentStatusBar(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fragmentManager = getFragmentManager();
        setToNewsFragment();
    }

    /**
     * 设置当前的Fragment 为资讯页
     */
    private void setToNewsFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (newsFragment != null) {
            transaction.show(newsFragment);
        } else {
            newsFragment = new NewsFragment();
            transaction.add(R.id.main_frame, newsFragment, "newsFragment");
        }
        transaction.commit();
        currentFragment = newsFragment;
    }

    /**
     * 设置当前的Fragment 为项目页
     */
    private void setToProjectFragment() {
    }

    /**
     * 设置当前的Fragment 为活动页
     */
    private void setToActivityFragment() {
    }

    /**
     * 设置当前的Fragment 为找投资页
     */
    private void setToInvestmentFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (moneyFragment != null) {
            transaction.show(moneyFragment);
        } else {
            moneyFragment = new MoneyFragment();
            transaction.add(R.id.main_frame, moneyFragment, "moneyFragment");
        }
        transaction.commit();
        currentFragment = moneyFragment;
    }

    /**
     * 设置当前的Fragment 为个人中心页
     */
    private void setToVipFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (vipFragment != null) {
            transaction.show(vipFragment);
        } else {
            vipFragment = new VipFragment();
            transaction.add(R.id.main_frame, vipFragment, "vipFragment");
        }
        transaction.commit();
        currentFragment = vipFragment;
    }

    private void hideAll(FragmentTransaction transaction) {
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four, R.id.tab_five})
    public void onclick(View view) {

        switch (view.getId()) {
            case R.id.tab_one:// 咨询
                setBg(1);
                setToNewsFragment();
                break;
            case R.id.tab_two:// 项目
                setBg(2);
                setToProjectFragment();
                break;
            case R.id.tab_three:// 活动
                setBg(3);
                setToActivityFragment();
                break;
            case R.id.tab_four:// 找投资
                setBg(4);
                setToInvestmentFragment();
                break;
            case R.id.tab_five:// 我的
                setBg(5);
                setToVipFragment();
                break;
            default:
                break;
        }
    }

    /**
     * s设置底部Tab的样式
     *
     * @param id 为tab的顺序
     */
    private void setBg(int id) {

        switch (id) {
            case 1: // 咨询
                AnimalUtil.QTanAnimal(tabOneImg, R.mipmap.icon_news_select,
                        200, new AnimalUtil.OnAnimalFinished() {
                            @Override
                            public void doSomething() {
                                setAllToGrey();
                                tabOneTxt.setTextColor(getResources().getColor(R.color.theme_color));
                            }
                        });

                break;
            case 2: // 项目
                AnimalUtil.QTanAnimal(tabTwoImg, R.mipmap.icon_project_select,
                        200, new AnimalUtil.OnAnimalFinished() {
                            @Override
                            public void doSomething() {
                                setAllToGrey();
                                tabTwoTxt.setTextColor(getResources().getColor(R.color.theme_color));
                            }
                        });

                break;
            case 3: // 活动
                AnimalUtil.QTanAnimal(tabThreeImg, R.mipmap.icon_activity_select,
                        200, new AnimalUtil.OnAnimalFinished() {
                            @Override
                            public void doSomething() {
                                setAllToGrey();
                                tabThreeTxt.setTextColor(getResources().getColor(R.color.theme_color));
                            }
                        });

                break;
            case 4: // 找投资
                AnimalUtil.QTanAnimal(tabFourImg, R.mipmap.icon_investment_select,
                        200, new AnimalUtil.OnAnimalFinished() {
                            @Override
                            public void doSomething() {
                                setAllToGrey();
                                tabFourTxt.setTextColor(getResources().getColor(R.color.theme_color));
                            }
                        });

                break;
            case 5: // 我的
                AnimalUtil.QTanAnimal(tabFiveImg, R.mipmap.icon_persion_select,
                        200, new AnimalUtil.OnAnimalFinished() {
                            @Override
                            public void doSomething() {
                                setAllToGrey();
                                tabFiveTxt.setTextColor(getResources().getColor(R.color.theme_color));
                            }
                        });

                break;
            default:
                break;
        }
    }

    /**
     * 重置
     */
    private void setAllToGrey() {
        tabOneImg.setImageResource(R.mipmap.icon_news);
        tabTwoImg.setImageResource(R.mipmap.icon_project);
        tabThreeImg.setImageResource(R.mipmap.icon_activity);
        tabFourImg.setImageResource(R.mipmap.icon_investment);
        tabFiveImg.setImageResource(R.mipmap.icon_persion);

        tabOneTxt.setTextColor(getResources().getColor(R.color.hint_middle_color));
        tabTwoTxt.setTextColor(getResources().getColor(R.color.hint_middle_color));
        tabThreeTxt.setTextColor(getResources().getColor(R.color.hint_middle_color));
        tabFourTxt.setTextColor(getResources().getColor(R.color.hint_middle_color));
        tabFiveTxt.setTextColor(getResources().getColor(R.color.hint_middle_color));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtils.showToast(this, "再按一次退出");
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                isExit = false;
                            }
                        });
                return false;
            }
            AppManager.AppExit(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setSwitchNightVisible(int Visible, boolean isNight) {
        mSwitchNight.setVisibility(Visible, isNight);
    }

    public void switchModel() {
        try {
            if (Hawk.get(Constants.MODEL)) {
                NightModel.setVisibility(View.VISIBLE);
            } else {
                NightModel.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            NightModel.setVisibility(View.GONE);
        }
    }
}
