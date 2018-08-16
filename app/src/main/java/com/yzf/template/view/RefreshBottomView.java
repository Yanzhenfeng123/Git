package com.yzf.template.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.lcodecore.tkrefreshlayout.Footer.ProgressView;
import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * 刷新view
 *
 * @author BiHaidong
 * @date 2017-4-25
 */
public class RefreshBottomView extends ProgressView implements IBottomView {


    public RefreshBottomView(Context context) {
        this(context, null);
    }

    public RefreshBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        setLayoutParams(params);
        setIndicatorColor(getResources().getColor(com.lcodecore.tkrefreshlayout.R.color.Orange));
        setIndicatorId(BallPulse);
    }

    private int normalColor = 0xffeeeeee;
    private int animatingColor = 0xffeeeeee;

    public void setNormalColor(@ColorInt int color) {
        normalColor = color;
    }

    public void setAnimatingColor(@ColorInt int color) {
        animatingColor = color;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        setIndicatorColor(normalColor);
        stopAnim();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        setIndicatorColor(animatingColor);
        startAnim();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        stopAnim();
    }

    @Override
    public void onFinish() {
        stopAnim();
    }

    @Override
    public void reset() {
        stopAnim();
    }
}
