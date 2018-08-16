package com.yzf.template.action.view.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vondear.rxtool.RxBarTool;
import com.vondear.rxui.view.dialog.RxDialog;
import com.vondear.rxui.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxui.view.dialog.RxDialogLoading;
import com.vondear.rxui.view.dialog.RxDialogScaleView;
import com.vondear.rxui.view.dialog.RxDialogShapeLoading;
import com.vondear.rxui.view.dialog.RxDialogSure;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.vondear.rxui.view.dialog.RxDialogWheelYearMonthDay;
import com.yzf.template.R;
import com.yzf.template.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogActivity extends ToolBarActivity {

    @BindView(R.id.button_tran)
    Button mButtonTran;
    @BindView(R.id.button_DialogSure)
    Button mButtonDialogSure;
    @BindView(R.id.button_DialogSureCancle)
    Button mButtonDialogSureCancle;
    @BindView(R.id.button_DialogEditTextSureCancle)
    Button mButtonDialogEditTextSureCancle;
    @BindView(R.id.button_DialogWheelYearMonthDay)
    Button mButtonDialogWheelYearMonthDay;
    @BindView(R.id.button_DialogShapeLoading)
    Button mButtonDialogShapeLoading;
    @BindView(R.id.button_DialogLoadingProgressAcfunVideo)
    Button mButtonDialogLoadingProgressAcfunVideo;
    @BindView(R.id.button_DialogLoadingspinkit)
    Button mButtonDialogLoadingspinkit;
    @BindView(R.id.button_DialogScaleView)
    Button mButtonDialogScaleView;
    private RxDialogWheelYearMonthDay mRxDialogWheelYearMonthDay;

    @Override
    protected void beforeLayout() {
        RxBarTool.noTitle(this);
        RxBarTool.setTransparentStatusBar(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
        setToolBarTitle("常见Dialog");
    }

    @OnClick({
            R.id.button_DialogScaleView,
            R.id.button_DialogLoadingspinkit,
            R.id.button_tran,
            R.id.button_DialogSure,
            R.id.button_DialogSureCancle,
            R.id.button_DialogEditTextSureCancle,
            R.id.button_DialogWheelYearMonthDay,
            R.id.button_DialogShapeLoading,
            R.id.button_DialogLoadingProgressAcfunVideo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_tran:
                RxDialog rxDialog = new RxDialog(this, R.style.tran_dialog);
                View view1 = LayoutInflater.from(this).inflate(R.layout.image, null);
                ImageView pageItem = view1.findViewById(R.id.page_item);
                pageItem.setImageResource(R.drawable.coin);
                rxDialog.setContentView(view1);
                rxDialog.show();
                break;
            case R.id.button_DialogSure:
                //提示弹窗
                final RxDialogSure rxDialogSure = new RxDialogSure(this);
                rxDialogSure.getTitleView().setText("英雄联盟");
                rxDialogSure.getContentView().setText("我的大刀已经饥渴难耐");
                rxDialogSure.getLogoView().setImageResource(R.drawable.logo);
                rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSure.cancel();
                    }
                });
                rxDialogSure.show();
                break;
            case R.id.button_DialogSureCancle:
                //提示弹窗
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(this);
                rxDialogSureCancel.getTitleView().setBackgroundResource(R.drawable.logo);
                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.show();
                break;
            case R.id.button_DialogEditTextSureCancle:
                //提示弹窗
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(this);
                rxDialogEditSureCancel.getTitleView().setBackgroundResource(R.drawable.logo);
                rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mButtonDialogEditTextSureCancle.setText(rxDialogEditSureCancel.getEditText().getText().toString());
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.show();
                break;
            case R.id.button_DialogWheelYearMonthDay:
                if (mRxDialogWheelYearMonthDay == null) {
                    initWheelYearMonthDayDialog();
                }
                mRxDialogWheelYearMonthDay.show();
                break;
            case R.id.button_DialogShapeLoading:
                RxDialogShapeLoading rxDialogShapeLoading = new RxDialogShapeLoading(this);
                rxDialogShapeLoading.show();
                break;
            case R.id.button_DialogLoadingProgressAcfunVideo:
//                new RxDialogAcfunVideoLoading(this).show();
                break;
            case R.id.button_DialogLoadingspinkit:
                RxDialogLoading rxDialogLoading = new RxDialogLoading(this);
                rxDialogLoading.show();
                //rxDialogLoading.cancel(RxDialogLoading.RxCancelType.error,"");
                break;
            case R.id.button_DialogScaleView:
                RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(this);
                rxDialogScaleView.setImage("squirrel.jpg", true);
                rxDialogScaleView.show();
                break;
            default:
                break;
        }
    }

    private void initWheelYearMonthDayDialog() {
        // ------------------------------------------------------------------选择日期开始
        mRxDialogWheelYearMonthDay = new RxDialogWheelYearMonthDay(this, 1994, 2020);
        mRxDialogWheelYearMonthDay.getSureView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (mRxDialogWheelYearMonthDay.getCheckBoxDay().isChecked()) {
                            mButtonDialogWheelYearMonthDay.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月"
                                            + mRxDialogWheelYearMonthDay.getSelectorDay() + "日");
                        } else {
                            mButtonDialogWheelYearMonthDay.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月");
                        }
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        mRxDialogWheelYearMonthDay.getCancleView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        // ------------------------------------------------------------------选择日期结束
    }
}
