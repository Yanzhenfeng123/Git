package com.yzf.template.action.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vondear.rxtool.RxBarTool;
import com.yzf.core.base.BaseActivity;
import com.yzf.template.R;
import com.yzf.template.glide.ImageLoader;
import com.yzf.template.utils.AppUtil;
import com.yzf.template.utils.DisplayUtil;
import com.yzf.template.utils.ValidationUtil;
import com.yzf.template.view.FloatingLinearLayoutButton;
import com.yzf.template.view.PullDownScrollview;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailScaleActivity extends BaseActivity {
    @BindView(R.id.iv_image)
    ImageView mHeaderView;
    @BindView(R.id.sv_comic)
    PullDownScrollview mScrollView;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.ll_text)
    LinearLayout mText;
    @BindView(R.id.iv_image_bg)
    ImageView mHeaderViewBg;
    @BindView(R.id.tv_author_tag)
    TextView mAuthorTag;
    @BindView(R.id.tv_collects)
    TextView mCollects;
    @BindView(R.id.tv_describe)
    TextView mDescribe;
    @BindView(R.id.tv_popularity)
    TextView mPopularity;
    @BindView(R.id.tv_status)
    TextView mStatus;
    @BindView(R.id.tv_update)
    TextView mUpdate;
    @BindView(R.id.tv_point)
    TextView mPoint;
    @BindView(R.id.ll_detail)
    RelativeLayout mDetail;
    @BindView(R.id.tv_tab)
    TextView mTab;
    @BindView(R.id.iv_order)
    ImageView mOrder;
    @BindView(R.id.tv_actionbar_title)
    TextView mActionBarTitle;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.iv_oreder2)
    ImageView mOrder2;
    @BindView(R.id.btn_read)
    Button mRead;

    @BindView(R.id.ll_index)
    LinearLayout mIndex;
    @BindView(R.id.iv_collect)
    ImageView mCollect;
    @BindView(R.id.tv_collect)
    TextView mIsCollect;
    @BindView(R.id.ll_floatbottom)
    FloatingLinearLayoutButton mFloatButtom;


    private float mScale = 1.0f;
    private float Dy = 0;
    private Rect normal = new Rect();
    private int mCurrent;
    private Intent intent;
    private Map map;
    private int count;


    @Override
    protected void beforeLayout() {
        RxBarTool.noTitle(this);
        RxBarTool.setTransparentStatusBar(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_scale;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        ImageLoader.getInstance().loadImage(DetailScaleActivity.this, intent.getStringExtra("url") == null ? "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670424862&di=9980e38717884cf950b9f944cf585bd0&imgtype=0&src=http%3A%2F%2Fandroid-wallpapers.25pp.com%2F20140314%2F0852%2F532252da025ff9_900x675.jpg" : intent.getStringExtra("url"), mHeaderView, ValidationUtil.getScreenWidth(DetailScaleActivity.this), AppUtil.dip2px(DetailScaleActivity.this, 200));
        ImageLoader.loadBlurImage(DetailScaleActivity.this, intent.getStringExtra("url") == null ? "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670424862&di=9980e38717884cf950b9f944cf585bd0&imgtype=0&src=http%3A%2F%2Fandroid-wallpapers.25pp.com%2F20140314%2F0852%2F532252da025ff9_900x675.jpg" : intent.getStringExtra("url"), mHeaderViewBg, ValidationUtil.getScreenWidth(DetailScaleActivity.this), AppUtil.dip2px(DetailScaleActivity.this, 200));


        mScrollView.setScaleTopListener(new MyScaleTopListener());
        mFloatButtom.setListener(new FloatingLinearLayoutButton.FloatClickListener() {
            @Override
            public void onClickLocation(View view) {
                mScrollView.ScrollToPosition(0);
            }

            @Override
            public void onClickScroll(View view) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        initData();
    }

    private void initData() {
        map = new HashMap();
        for (int i = 0; i < 8; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.news_item, null);
            TextView tv = view.findViewById(R.id.item_name);
            ImageView iv = view.findViewById(R.id.item_img);
            map.put(i + "", intent.getStringExtra("url") == null ? "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670424862&di=9980e38717884cf950b9f944cf585bd0&imgtype=0&src=http%3A%2F%2Fandroid-wallpapers.25pp.com%2F20140314%2F0852%2F532252da025ff9_900x675.jpg" : intent.getStringExtra("url"));
            tv.setText("content" + i);
            ImageLoader.getInstance().loadImage(getApplicationContext(), intent.getStringExtra("url") == null ? "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670424862&di=9980e38717884cf950b9f944cf585bd0&imgtype=0&src=http%3A%2F%2Fandroid-wallpapers.25pp.com%2F20140314%2F0852%2F532252da025ff9_900x675.jpg" : intent.getStringExtra("url"), iv);
            mIndex.addView(view);
        }
        mScrollView.setInnerHeight();
    }

    public class MyScaleTopListener implements PullDownScrollview.ScaleTopListener {
        @Override
        public void isScale(float y) {
            int height = DisplayUtil.dip2px(DetailScaleActivity.this, 200);
            //屏幕宽度的倍数 1
            mScale = y / height;
            //拉伸后图片的宽度
            float width = DisplayUtil.getScreenWidth(DetailScaleActivity.this) * mScale;
            //超出屏幕宽的长度的一半
            float dx = (width - DisplayUtil.getScreenWidth(DetailScaleActivity.this)) / 2;
            //四个参数 ViewGroup.layout(int l, int t, int r, int b)
            // l 和 t 是控件左边缘和上边缘相对于父类控件左边缘和上边缘的距离。
            //r 和 b是空间右边缘和下边缘相对于父类控件左边缘和上边缘的距离。
            mHeaderView.layout((int) (0 - dx), 0, (int) (DisplayUtil.getScreenWidth(DetailScaleActivity.this) + dx), (int) y);
            Dy = y - height;
            mText.layout(normal.left, (int) (normal.top + Dy), normal.right, (int) (normal.bottom + Dy));
            //Log.d("DetailActivity","Dy="+Dy+",normal="+normal.toString());
        }

        @Override
        public void isFinished() {
            //Log.d("DetailScrollView","Dy="+Dy);
            Interpolator in = new DecelerateInterpolator();
            ScaleAnimation ta = new ScaleAnimation(mScale, 1.0f, mScale, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
            //第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
            //第二个参数toX为动画结束时 X坐标上的伸缩尺寸
            //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
            //第四个参数toY为动画结束时Y坐标上的伸缩尺寸
            //第五个参数pivotXType为动画在X轴相对于物件位置类型
            //第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
            //第七个参数pivotXType为动画在Y轴相对于物件位置类型
            //第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
            ta.setInterpolator(in);
            ta.setDuration(300);
            mHeaderView.startAnimation(ta);
            mHeaderView.layout(0, 0, DisplayUtil.getScreenWidth(DetailScaleActivity.this), DisplayUtil.dip2px(DetailScaleActivity.this, 200));
            //设置文字活动的动画
            TranslateAnimation trans = new TranslateAnimation(0, 0, Dy, 0);
            trans.setInterpolator(in);
            trans.setDuration(300);
            mText.startAnimation(trans);
            mText.layout(normal.left, normal.top, normal.right, normal.bottom);
            //统统重置
            mScale = 1.0f;
            Dy = 0;
        }

        @Override
        public void isBlurTransform(float y) {
            mHeaderViewBg.setAlpha(1 - y);

        }

        @Override
        public void isShowTab(int a) {
            setTab(a);
        }
    }

    public void setTab(int a) {
        switch (a) {
            case PullDownScrollview.SHOW_CHAPER_TAB:
                if (mDetail.getVisibility() == View.GONE) {
                    mDetail.setVisibility(View.VISIBLE);
                } else {

                    mTab.setText("详情");
                    mOrder.setVisibility(View.GONE);
                }
                break;
            case PullDownScrollview.SHOW_DETAIL_TAB:
                mTab.setText("目录");
                mOrder.setVisibility(View.VISIBLE);
                break;
            case PullDownScrollview.SHOW_ACTIONBAR_TITLE:
                if (mDetail.getVisibility() == View.VISIBLE) {
                    mOrder.setVisibility(View.GONE);
                    mDetail.setVisibility(View.GONE);
                    mTab.setText("详情");
                }
                if (mActionBarTitle.getVisibility() == View.GONE) {
                    mActionBarTitle.setVisibility(View.VISIBLE);
                    AnimationSet animationSet = new AnimationSet(true);
                    TranslateAnimation trans = new TranslateAnimation(0, 0, DisplayUtil.dip2px(getApplicationContext(), 12), 0);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                    animationSet.addAnimation(trans);
                    animationSet.addAnimation(alphaAnimation);
                    animationSet.setDuration(200);
                    mActionBarTitle.startAnimation(animationSet);
                }
                break;
            case PullDownScrollview.SHOW_NONE:
                if (mDetail.getVisibility() == View.VISIBLE) {
                    mTab.setText("详情");
                    mOrder.setVisibility(View.GONE);
                    mDetail.setVisibility(View.GONE);
                }
                if (mActionBarTitle.getVisibility() == View.VISIBLE) {
                    mActionBarTitle.setVisibility(View.GONE);
                    AnimationSet animationSet = new AnimationSet(true);
                    TranslateAnimation trans = new TranslateAnimation(0, 0, 0, DisplayUtil.dip2px(getApplicationContext(), 12));
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                    animationSet.addAnimation(trans);
                    animationSet.addAnimation(alphaAnimation);
                    animationSet.setDuration(200);
                    mActionBarTitle.startAnimation(animationSet);
                }
                break;
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_order, R.id.iv_oreder2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_order:
                count++;
                setOrder(mIndex);
                break;
            case R.id.iv_oreder2:
                count++;
                setOrder(mIndex);
                break;
        }
    }

    private void setOrder(LinearLayout mIndex) {
        for (int position = 0; position < map.keySet().size(); position++) {
            View view = mIndex.getChildAt(position);
            ImageView iv = view.findViewById(R.id.item_img);
            TextView tv = view.findViewById(R.id.item_name);
            //正序
            if (count % 2 == 0) {
                tv.setText("content" + position);
                ImageLoader.getInstance().loadImage(getApplicationContext(), map.get(position + "").toString(), iv);
                mOrder.setImageResource(R.mipmap.zhengxu);
                mOrder2.setImageResource(R.mipmap.zhengxu);
                //倒序
            } else {
                tv.setText("content" + (map.keySet().size() - position - 1));
                ImageLoader.getInstance().loadImage(getApplicationContext(), map.get(map.keySet().size() - position - 1 + "").toString(), iv);
                mOrder.setImageResource(R.mipmap.daoxu);
                mOrder2.setImageResource(R.mipmap.daoxu);
            }
        }

    }
}
