package com.zero.location;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Jin_ on 2016/7/14
 * 邮箱：Jin_Zboy@163.com
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("app/basic/toSave")
    Observable<CodeRes> uploadData(@Field("phoneBrand") String phoneBrand, @Field("phoneX") String phoneX,
                                   @Field("phoneY") String phoneY, @Field("baiDuX") String baiDuX,
                                   @Field("baiDuY") String baiDuY, @Field("collectTime") String collectTime);
}
