package com.zero.domvp.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jin_ on 2016/6/6
 * 邮箱：dejin_lu@creawor.com
 */
public class App extends Application {
    private static final String KEY_IS_FIRST = "IS_FIRST";
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
