package com.zero.domvp.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jin_ on 2016/8/15
 * 邮箱：Jin_Zboy@163.com
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * 短时间显示  Toast
     *
     * @param context
     * @param sequence
     */
    public static void showShort(Context context, CharSequence sequence) {

        if (mToast == null) {
            mToast = Toast.makeText(context, sequence, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(sequence);
        }
        mToast.show();

    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (null == mToast) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (null == mToast) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            //    toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 自定义显示时间
     *
     * @param context
     * @param sequence
     * @param duration
     */
    public static void show(Context context, CharSequence sequence, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, sequence, duration);
        } else {
            mToast.setText(sequence);
        }
        mToast.show();

    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
