package com.unbaja.inggi.bengkos.util;

import android.util.Log;

/**
 * Created by sigit on 01/07/2018.
 */

public class MyLogger {

    private static final String TAG = "kopi";

    public static void logPesan(String pesan) {
        Log.d(TAG, pesan);
    }
}
