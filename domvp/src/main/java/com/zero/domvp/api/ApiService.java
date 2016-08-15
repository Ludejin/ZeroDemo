package com.zero.domvp.api;


import com.zero.domvp.entity.BaseRes;
import com.zero.domvp.entity.bean.JokeInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public interface ApiService {

    /**
     * 获取版本号
     *
     * @return
     */
    @GET("showapi_open_bus/showapi_joke/joke_text")
    Observable<BaseRes<JokeInfo>> getJokes(@Query("page") int page);

}
