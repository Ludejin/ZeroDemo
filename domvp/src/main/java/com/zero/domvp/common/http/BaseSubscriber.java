package com.zero.domvp.common.http;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.zero.domvp.common.Const;
import com.zero.domvp.common.utils.NetworkUtils;
import com.zero.domvp.common.utils.ToastUtil;
import com.zero.domvp.entity.BaseRes;

import rx.Subscriber;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public abstract class BaseSubscriber<T extends BaseRes> extends Subscriber<T> {
    private Context mContext;

    public BaseSubscriber() {

    }

    public BaseSubscriber(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCompleted() {
        /** hide Loading */
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        ToastUtil.showShort(getContext(), NetworkUtils.getMsgByError(e));
    }

    @Override
    public void onNext(T t) {

        if (t.showapi_res_code == Const.CODE_SUCCESS) {
            _onNext(t);
        } else {
            ToastUtil.showShort(mContext, t.showapi_res_error);
        }
    }

    public abstract void _onNext(T entity);
}
