package com.alex.mirash.testtask.tool;

import android.util.Log;

/**
 * @author Mirash
 */

public class LogUtils {
    public static final String LOG_TAG = "LOL";

    public static void log(String message) {
        System.out.println(message);
        Log.d(LOG_TAG, message);
    }
}
