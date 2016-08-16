package com.zero.domvp.common.http;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.zero.domvp.common.utils.NetworkUtils;
import com.zero.domvp.common.utils.ToastUtil;
import com.zero.domvp.entity.BaseRes;

/**
 * Created by Jin_ on 2016/8/15
 * 邮箱：Jin_Zboy@163.com
 */
public abstract class LoadingSubscriber<T extends BaseRes> extends BaseSubscriber<T> {

    public LoadingSubscriber(Context context) {
        super(context);
    }

    @Override
    public void onCompleted() {
        /** 完成，
         *  hide Loading
         * */
        super.onCompleted();
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        /** 出错，
         *  hide Loading
         * */
        ToastUtil.showShort(getContext(), NetworkUtils.getMsgByError(e));
        super.onError(e);
    }

    @Override
    public void onNext(T t) {
        /** 在这显示loading */

        super.onNext(t);
    }
}
