package com.zero.domvp.common;

import android.os.Environment;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public class Const {
    public static final String SAVE_FILE_PATH = Environment.getExternalStorageDirectory()
            .toString() + "/ZeroDemo";    // 保存文件路径

    // ------------------------app状态码
    public static final int CODE_SUCCESS        = 0; // 执行成功
    public static final int CODE_FAIL           = 1; // 执行异常
}
