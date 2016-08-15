package com.zero.location.mvp;

/**
 * 基类Presenter
 * Created by Jin_ on 2016/6/6
 * 邮箱：dejin_lu@creawor.com
 */
public abstract class BasePresenter<E, T> {
    public E mModel;
    public T mView;
    public RxManage mRxManage = new RxManage();

    public void setVM(T v, E m) {
        this.mModel = m;
        this.mView = v;
        this.onStart();
    }

    public abstract void onStart();

    public void onDestroy() {
        mRxManage.clear();
    }
}
