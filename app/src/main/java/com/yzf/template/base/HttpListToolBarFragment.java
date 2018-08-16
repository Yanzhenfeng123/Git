package com.yzf.template.base;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.yzf.core.base.HttpView;
import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.core.utils.ToastUtils;

public abstract class HttpListToolBarFragment extends ListToolBarFragment implements HttpView {

    protected CustomProgressDialog pd;
    protected int postCount = 0;
    protected int finishedCount = 0;

    @Override
    public void onLoading() {
        postCount++;
        if (pd == null) {
            pd = CustomProgressDialog.createDialog(getActivity());
        }
        pd.show();
    }

    @Override
    public void loadingFinished() {
        finishedCount++;
        if (postCount == finishedCount) {
            if (pd != null) {
                pd.dismiss();
            }
        }
    }

    @Override
    public void showMsg(String str) {
        ToastUtils.showToast(getActivity(), str);
    }

    @Override
    public <T> LifecycleTransformer<T> bindRecycler() {
        return this.bindToLifecycle();
    }
}
