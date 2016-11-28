package com.cui.video.utils;

import android.util.Log;

import com.cui.video.BuildConfig;


/**
 * Created by cuiyang on 16/6/2.
 */
public class LogUtils {

    public static void v(Class cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(cls.getName(), msg);
        }
    }

    public static void e(Class cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(cls.getName(), msg);
        }
    }

    public static void d(Class cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(cls.getName(), msg);
        }
    }

    public static void throwable(Class cls, String errorMsg) {
        if (BuildConfig.DEBUG) {
            Log.e(cls.getName(), "throwable==" + errorMsg);
        }
    }

    public static void printStackTrace(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(LogUtils.class.getName(), "Exception==" + e.toString());
        }
    }

    public static void i(Class cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(cls.getName(), msg);
        }
    }
}
