package com.yzf.template.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.yzf.template.R;


public class RefreshHeadView implements IHeaderView {
    private View view;
    private Context context;
    private ImageView iv;

    public RefreshHeadView(Context context){
        this.context = context.getApplicationContext();
        view = LayoutInflater.from(context).inflate(R.layout.refresh_layout, null);
        iv = (ImageView) view.findViewById(R.id.refresh_view);

    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        iv.setImageResource(R.drawable.refresh_round);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.stop();
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

    }
}
