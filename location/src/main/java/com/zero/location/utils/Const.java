package com.zero.location.utils;

import android.os.Environment;

/**
 * Created by Jin_ on 2016/6/6
 * 邮箱：dejin_lu@creawor.com
 */
public class Const {
//    public static final String BASE_URL = "http://192.168.10.169:8080/PocketCollect/";
//    public static final String BASE_URL = "http://192.168.10.52:8080/PocketCollect/";
//    public static final String BASE_URL = "http://www.creawor.com:59087/PocketCollect/";
    public static final String BASE_URL = "http://www.creawor.com:59087/PocketCollect-1.0/";

    // ------------------------app状态码
    public static final int CODE_SUCCESS        = 0; // 执行成功
    public static final int CODE_FAIL           = 1; // 执行异常

    public static final String SAVE_FILE_PATH = Environment.getExternalStorageDirectory()
            .toString() + "/掌上采";    // 保存文件路径
}
