package com.zero.location;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jin_ on 2016/7/20
 * 邮箱：Jin_Zboy@163.com
 */
public class MyModel implements Contract.Model {
    @Override
    public Observable<CodeRes> uploadData(String phoneBrand, String phoneX, String phoneY, String baiDuX, String baiDuY, String collectTime) {
        return ApiClient.getInstance()
                .mApiService
                .uploadData(phoneBrand,phoneX,phoneY,baiDuX,baiDuY,collectTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
