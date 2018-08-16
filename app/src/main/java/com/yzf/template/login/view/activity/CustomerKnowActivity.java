package com.yzf.template.login.view.activity;

import android.webkit.WebView;

import com.yzf.template.R;
import com.yzf.template.base.ToolBarActivity;

import butterknife.BindView;

public class CustomerKnowActivity extends ToolBarActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_know;
    }

    @Override
    protected void initView() {
        setToolBarTitle("用户须知");
        setData();
    }

    private void setData() {
        String html = "<html> \n" + "<head> \n"
                + "<style type=\"text/css\"> \n"
                + "body {color: 696969;font-size:14px;line-height:150%;letter-spacing:0.5px}\n"
                + "img{width:100%;} \n" + "</style> \n" + "</head> \n"
                + "<body>\n<h4 style=\"text-align:center\">伙办网法律声明及隐私权政策</h4>\n<h4>提示条款</h4>\n%@</body> \n" + "</html>";
        String data = html.replace("%@", getResources().getString(R.string.customer_know));
        webView.loadData(data, "text/html; charset=utf-8", "utf-8");
    }
}
