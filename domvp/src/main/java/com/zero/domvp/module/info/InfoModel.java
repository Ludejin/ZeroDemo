package com.zero.domvp.module.info;

import com.zero.domvp.api.ApiClient;
import com.zero.domvp.common.http.SchedulerTransformer;
import com.zero.domvp.entity.BaseRes;
import com.zero.domvp.entity.bean.JokeInfo;

import rx.Observable;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public class InfoModel implements InfoContract.Model {
    @Override
    public Observable<BaseRes<JokeInfo>> getJokes(int page) {
        return ApiClient.getInstance().mApiService
                .getJokes(page)
                .compose(SchedulerTransformer.<BaseRes<JokeInfo>>create());
    }
}
