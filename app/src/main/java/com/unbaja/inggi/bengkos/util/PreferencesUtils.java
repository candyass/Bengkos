package com.unbaja.inggi.bengkos.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sigit on 28/06/2018.
 */

public class PreferencesUtils {

    private static final String KEY_DATABASE_SETUP = "com.unbaja.inggi.bengkos.key.databasesetup";

    private static PreferencesUtils sSingleton;


    public static PreferencesUtils getInstance(Context context) {
        if(sSingleton == null) {
            sSingleton = new PreferencesUtils(context);
        }
        return sSingleton;
    }


    private SharedPreferences mSharedPreferences;

    private PreferencesUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("MyPreference", Context.MODE_PRIVATE);
    }

    public void saveBoolean(boolean value) {
        mSharedPreferences.edit().putBoolean(KEY_DATABASE_SETUP, value).apply();
    }

    public boolean getBoolean() {
        return mSharedPreferences.getBoolean(KEY_DATABASE_SETUP, false);
    }
}
