package com.yzf.template.login.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzf.core.utils.AppManager;
import com.yzf.core.utils.ToastUtils;
import com.yzf.template.R;
import com.yzf.template.base.HttpToolBarActivity;
import com.yzf.template.login.presenter.BindPhoNubPresenter;
import com.yzf.template.login.presenter.ForgetPasswordPresenter;
import com.yzf.template.login.view.IBindPhoNubView;
import com.yzf.template.login.view.IForgetPasswordView;
import com.yzf.template.main.MainActivity;
import com.yzf.template.utils.ValidationUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetAndResetPassWordActivity extends HttpToolBarActivity implements IForgetPasswordView, IBindPhoNubView {

    @BindView(R.id.phone)
    EditText ed_account;
    @BindView(R.id.code)
    EditText ed_code;
    @BindView(R.id.password)
    EditText ed_password;
    @BindView(R.id.send)
    TextView tv_getCode;
    @BindView(R.id.img_eye)
    ImageView img_buttun;
    @BindView(R.id.account_submit)
    TextView tv_sub;

    private int i = -1;
    private ForgetPasswordPresenter presenter;
    private BindPhoNubPresenter bd_presenter;
    private TimeCount timer;
    private Intent intent;
    private String type = "";

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pass_word;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        type=intent.getStringExtra("type").toString();
        if (type.equals("1")) {
            setToolBarTitle("忘记密码");
            presenter = new ForgetPasswordPresenter(this);
        } else if (type.equals("2")) {
            setToolBarTitle("绑定手机号");
            bd_presenter = new BindPhoNubPresenter(this);
        }

    }

    @OnClick({R.id.account_submit, R.id.img_eye, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.account_submit:

                if (!ed_code.getText().toString().equals("") && isPhoNub() && isPassWord()) {
                    if (type.equals("1")){
                        presenter.resetPassword(ed_account.getText().toString(), ed_password.getText().toString(), ed_code.getText().toString());
                    }else if (type.equals("2")){
                        bd_presenter.bindPhoNub(intent.getStringExtra("openid"),ed_account.getText().toString(),ed_password.getText().toString(),ed_code.getText().toString(),intent.getStringExtra("iconurl"));
                    }

                }
                break;
            case R.id.img_eye:
                i++;
                if (i % 2 == 0) {
                    ed_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                    img_buttun.setImageResource(R.mipmap.me_mima_eye_blue);
                } else {
                    ed_password.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
                    img_buttun.setImageResource(R.mipmap.me_mima_eye_grey);
                }
                ed_password.setSelection(TextUtils.isEmpty(ed_password.getText()) ? 0 : ed_password.length());//光标挪到最后
                break;
            case R.id.send:
                if (isPhoNub()) {
                    timer = new TimeCount(60000, 1000);
                    timer.start();
                    if (type.equals("1")){
                        presenter.getCode(ed_account.getText().toString(), "2");
                    }else if (type.equals("2")){
                        bd_presenter.getCode(ed_account.getText().toString(), "3");
                    }
                }
                break;
        }
    }

    @Override
    public Boolean isPhoNub() {
        if (ed_account.getText().length() == 0) {
            ToastUtils.showToast(this, "请填写手机号码");
            return false;
        } else if (!ValidationUtil.isMobile(ed_account.getText().toString())) {
            ToastUtils.showToast(this, "请填写正确手机号码");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void getCodeSuccess() {
        ToastUtils.showToast(this, "获取验证码成功");
    }

    @Override
    public void getCodeFailed(String str) {
        ToastUtils.showToast(this, str);
    }

    @Override
    public void resetPasswordSuccess(String msg) {
        showMsg(msg);
        Intent intent = new Intent();
        intent.putExtra("account", ed_account.getText().toString());
        setResult(200, intent);
        finish();
    }

    @Override
    public void resetPasswordFailed(String msg) {
        showMsg(msg);
    }


    private boolean isPassWord() {
        //判断是密码
        if (TextUtils.isEmpty(ed_password.getText().toString())) {
            ToastUtils.showToast(this, "请输入密码");
            return false;
        } else if (ed_password.getText().toString().length() < 6) {
            ToastUtils.showToast(this, "请输入6位密码");
            return false;
        } else if (ValidationUtil.isChinese(ed_password.getText().toString())) {
            ToastUtils.showToast(this, "请输入数字和字母组成的密码");
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void isBindPhoNubSuccess() {
        showMsg("绑定成功");
        startActivity(new Intent(ForgetAndResetPassWordActivity.this, MainActivity.class));
        AppManager.finishActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void isBindPhoNubFailed(String msg) {
        showMsg(msg);
    }

    //60秒重新发送短信
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_getCode.setTextColor(Color.parseColor("#ff7044"));
            tv_getCode.setClickable(false);
            tv_getCode.setText("重新获取(" + millisUntilFinished / 1000 + ") ");
        }

        @Override
        public void onFinish() {
            tv_getCode.setText("发送验证码");
            tv_getCode.setClickable(true);
            tv_getCode.setTextColor(Color.parseColor("#00a0e9"));
            tv_getCode.setClickable(true);
        }
    }
}
