package com.machinetest.quiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by javed.salat on 08-Aug-17.
 */

public class SharedPrefUtil {

    public static final String SHARE_PREF = "SHARE_PREF";
    public final static String SHARED_PREF_FIRST_LAUNCH = "first_launch";


    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return context.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE).edit();

    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }
}
