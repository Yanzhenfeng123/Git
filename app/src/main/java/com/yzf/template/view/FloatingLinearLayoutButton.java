package com.yzf.template.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FloatingLinearLayoutButton extends LinearLayout {

    private FloatClickListener listener;
    private RelativeLayout mLocation;
    private RelativeLayout mScroll;


    public void setListener(FloatClickListener listener) {
        this.listener = listener;
    }

    public FloatingLinearLayoutButton(Context context) {
        super(context, null);
    }

    public FloatingLinearLayoutButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FloatingLinearLayoutButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLocation = (RelativeLayout) getChildAt(0);
        mScroll = (RelativeLayout) getChildAt(1);
        mLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLocation(v);
            }
        });
        mScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickScroll(v);
            }
        });

    }

    public interface FloatClickListener {
        void onClickLocation(View view);

        void onClickScroll(View view);
    }
}
