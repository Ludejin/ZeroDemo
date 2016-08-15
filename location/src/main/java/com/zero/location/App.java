package com.zero.location;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Jin_ on 2016/8/5
 * 邮箱：Jin_Zboy@163.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
