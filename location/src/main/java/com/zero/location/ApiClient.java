package com.zero.location;


import com.zero.location.utils.Const;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jin_ on 2016/7/14
 * 邮箱：Jin_Zboy@163.com
 */
public class ApiClient {
    private static Retrofit mRetrofit;
    private static OkHttpClient.Builder mClientBuilder;

    public ApiService mApiService;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDir = new File(Const.SAVE_FILE_PATH + "/Cache");

        Cache cache = new Cache(httpCacheDir, 100 * 1024 * 1024);

        mClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .cache(cache);
    }


    private ApiClient() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClientBuilder.build())
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
