package com.yzf.template.base;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.yzf.core.base.HttpView;
import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.core.utils.ToastUtils;


/**
 * 用于http请求的基类 Fragment
 *
 * @author Yzf
 * @date 2018-7-26 16:41:47
 */
public abstract class HttpToolBarFragment extends ToolBarFragment implements HttpView {

    protected CustomProgressDialog pd ;
    protected int postCount = 0;
    protected int finishCount = 0;

    @Override
    public void onLoading() {
        postCount++;
        if (pd == null)
            pd = CustomProgressDialog.createDialog(getActivity());
    }

    @Override
    public void loadingFinished() {
        finishCount++;
        if (finishCount == postCount) {
            if (pd != null)
                pd.dismiss();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindRecycler() {
        return this.bindToLifecycle();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }
}
