package com.yzf.template.login.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yzf.core.base.HttpActivity;
import com.yzf.core.utils.ToastUtils;
import com.yzf.template.R;
import com.yzf.template.login.model.bean.WxLoginBean;
import com.yzf.template.login.presenter.LoginPresenter;
import com.yzf.template.login.presenter.LoginWxPresenter;
import com.yzf.template.login.view.IGetCodeView;
import com.yzf.template.login.view.ILoginView;
import com.yzf.template.login.view.ILoginWXView;
import com.yzf.template.login.view.IRegisterView;
import com.yzf.template.main.MainActivity;
import com.yzf.template.utils.AppUtil;
import com.yzf.template.utils.ValidationUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends HttpActivity implements ILoginView, IGetCodeView, IRegisterView, ILoginWXView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.loginAccount)
    EditText ed_account;
    @BindView(R.id.loginPassword)
    EditText ed_password;
    @BindView(R.id.loginText)
    TextView tv_login;
    @BindView(R.id.rela_login)
    RelativeLayout rela_login;
    @BindView(R.id.rela_register)
    RelativeLayout rela_register;
    @BindView(R.id.line_login)
    LinearLayout linear_login;
    @BindView(R.id.line_register)
    LinearLayout linear_register;
    @BindView(R.id.register_phone)
    EditText edit_register_phone;
    @BindView(R.id.register_code)
    EditText edit_register_code;
    @BindView(R.id.register_password)
    EditText edit_register_password;
    @BindView(R.id.img_eye)
    ImageView img_eye;
    @BindView(R.id.send)
    TextView tv_send;
    @BindView(R.id.login_icon)
    ImageView img_login;
    @BindView(R.id.register_icon)
    ImageView img_register;
    @BindView(R.id.submit)
    TextView tv_register;
    @BindView(R.id.missPassword)
    TextView ev_missPass;
    @BindView(R.id.loginWeChat)
    ImageView img_login_wechat;
    @BindView(R.id.custom)
    TextView tv_cu;


    private LoginPresenter presenter;
    private LoginWxPresenter wxPresenter;
    private int i = -1;
    private TimeCount timeCount;
    private Animation animation_login, animation_register;
    //三方授权后获取信息
    private String uid, iconurl;
    //三方登录
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            otherLogin(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    //微信登录成功后调 接口
    private void otherLogin(Map<String, String> data) {
//                wxPresenter.wxLogin(data.get("openid"));
        //友盟封装新的字段
        uid = data.get("uid");
        iconurl = data.get("iconurl");
        wxPresenter.wxLogin(data.get("uid"));

    }

    //分享
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LoginActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        presenter = new LoginPresenter(this, this, this);
        wxPresenter = new LoginWxPresenter(this);
    }

    @OnClick({R.id.back, R.id.loginText, R.id.rela_register, R.id.rela_login, R.id.img_eye, R.id.send, R.id.submit, R.id.missPassword, R.id.loginWeChat, R.id.custom})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
//                UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
//                UMImage image = new UMImage(ShareActivity.this, file);//本地文件
//                UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
//                UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
//                UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流
//                image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                压缩格式设置
//                image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                UMImage img = new UMImage(LoginActivity.this, "http://img0.ph.126.net/yT0AiS--1Hz2d2sD9Q6DpA==/6632286024582835716.jpg");
                img.compressStyle = UMImage.CompressStyle.SCALE;
                new ShareAction(LoginActivity.this).withMedia(img).withText("发十块红包包夜")
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();
                break;
            case R.id.rela_login:
                /**
                 * Ctrl+shift+上下 上移行下移行
                 */
                linear_register.setVisibility(View.GONE);
                img_register.setVisibility(View.GONE);
                img_login.setVisibility(View.VISIBLE);
                linear_login.setVisibility(View.VISIBLE);
                animation_login = AnimationUtils.loadAnimation(this, R.anim.in_from_up);
                linear_login.setAnimation(animation_login);
                animation_login.start();
                if (animation_register != null) {
                    animation_register.cancel();
                }
                break;
            case R.id.rela_register:
                linear_login.setVisibility(View.GONE);
                linear_register.setVisibility(View.VISIBLE);
                img_login.setVisibility(View.GONE);
                img_register.setVisibility(View.VISIBLE);
                animation_register = AnimationUtils.loadAnimation(this, R.anim.in_from_up);
                linear_register.setAnimation(animation_register);
                animation_register.start();
                if (animation_login != null) {
                    animation_login.cancel();
                }
                break;
            case R.id.loginText:

                if (AppUtil.isFastDoubleClick(1000)) {
                    return;
                }
                if (validate()) {
                    presenter.login(ed_account.getText().toString(), ed_password.getText().toString());
                }

                break;
            case R.id.img_eye:
                i++;
                if (i % 2 == 0) {
                    edit_register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                    img_eye.setImageResource(R.mipmap.me_mima_eye_blue);
                } else {
                    edit_register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
                    img_eye.setImageResource(R.mipmap.me_mima_eye_grey);
                }
                edit_register_password.setSelection(TextUtils.isEmpty(edit_register_password.getText()) ? 0 : edit_register_password.length());//光标挪到最后
                break;
            case R.id.send:
                //type 参数 1 注册获取  2 找回密码获取 3绑定获取 4 更换手机号
                if (isPhoNub()) {
                    timeCount = new TimeCount(60000, 1000);
                    timeCount.start();
                    presenter.getCode(edit_register_phone.getText().toString(), "1");
                }
                break;
            case R.id.submit:
                if (AppUtil.isFastDoubleClick(1000)) {
                    return;
                }
                if (isPhoNub() && !edit_register_code.getText().toString().equals("") && isPassWord()) {
                    presenter.register(edit_register_phone.getText().toString(), edit_register_password.getText().toString(), edit_register_code.getText().toString());
                }
                break;
            case R.id.missPassword:
                Intent intent = new Intent(LoginActivity.this, ForgetAndResetPassWordActivity.class);
                //type  1 是忘记密码  2 是绑定手机号
                intent.putExtra("type", "1");
                startActivityForResult(intent, 100);
                break;
            case R.id.loginWeChat:
                if (AppUtil.isFastDoubleClick(1000)) {
                    return;
                }
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.custom:
                startActivity(new Intent(LoginActivity.this, CustomerKnowActivity.class));
                break;
        }

    }

    private boolean isPassWord() {
        //判断是密码
        if (TextUtils.isEmpty(edit_register_password.getText().toString())) {
            ToastUtils.showToast(this, "请输入密码");
            return false;
        } else if (edit_register_password.getText().toString().length() < 6) {
            ToastUtils.showToast(this, "请输入6位密码");
            return false;
        } else if (ValidationUtil.isChinese(edit_register_password.getText().toString())) {
            ToastUtils.showToast(this, "请输入数字和字母组成的密码");
            return false;
        } else {
            return true;
        }

    }

    @Override
    public Boolean validate() {
        if (ed_account.getText().length() == 0) {
            ToastUtils.showToast(this, "请填写手机号码");
            return false;
        } else if (!ValidationUtil.isMobile(ed_account.getText().toString())) {
            ToastUtils.showToast(this, "请填写正确手机号码");
            return false;
        } else if (ed_password.getText().length() == 0) {
            ToastUtils.showToast(this, "请填写密码");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        showMsg(msg);
    }

    @Override
    public Boolean isPhoNub() {
        if (edit_register_phone.getText().length() == 0) {
            ToastUtils.showToast(this, "请填写手机号码");
            return false;
        } else if (!ValidationUtil.isMobile(edit_register_phone.getText().toString())) {
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
    public void getCodeFailed(String msg) {
        ToastUtils.showToast(this, msg);
        if (timeCount != null)
            timeCount.onFinish();

    }

    @Override
    public void registerSuccess(String msg) {
        ToastUtils.showToast(this, msg);
        linear_register.setVisibility(View.GONE);
        img_register.setVisibility(View.GONE);
        img_login.setVisibility(View.VISIBLE);
        linear_login.setVisibility(View.VISIBLE);
        ed_account.setText(edit_register_phone.getText().toString());
        animation_login = AnimationUtils.loadAnimation(this, R.anim.in_from_up);
        linear_login.setAnimation(animation_login);
        animation_login.start();
        if (animation_register != null) {
            animation_register.cancel();
        }
    }

    @Override
    public void registerFailed(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void WxLoginSuccess(WxLoginBean bean) {
        if (bean.getType().equals("1")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else if (bean.getType().equals("0")) {

            Intent intent = new Intent(LoginActivity.this, ForgetAndResetPassWordActivity.class);
            //type  1 是忘记密码  2 是绑定手机号
            intent.putExtra("type", "2");
            intent.putExtra("openid", uid);
            intent.putExtra("iconurl", iconurl);
            startActivity(intent);
        }
    }


    //60秒重新发送短信
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setTextColor(Color.parseColor("#ff7044"));
            tv_send.setClickable(false);
            tv_send.setText("重新获取(" + millisUntilFinished / 1000 + ") ");
        }

        @Override
        public void onFinish() {
            tv_send.setText("发送验证码");
            tv_send.setClickable(true);
            tv_send.setTextColor(Color.parseColor("#00a0e9"));
            tv_send.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            ed_account.setText(data.getStringExtra("account").toString());
            ed_account.setSelection(TextUtils.isEmpty(ed_account.getText()) ? 0 : ed_account.length());//光标挪到最
        }
    }
}
