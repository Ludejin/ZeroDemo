package com.zero.domvp.common.http;

import android.content.Context;

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
    public void onNext(T t) {
        /** 在这之前执行loading效果 */

        super.onNext(t);
    }
}
