package com.zero.domvp.base;

import android.content.Context;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public abstract class BasePresenter<M, V> {
    public M mModel;
    public V mView;
    public Context mContext;
    public RxManage mRxManage = new RxManage();

    public void setVM(M m, V v, Context context) {
        this.mModel = m;
        this.mView = v;
        this.mContext = context;
        this.onStart();
    }

    public abstract void onStart();

    public void onDestroy() {
        mRxManage.clear();
    }
}
