package com.cws.cwsbaseapplication.controller.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import com.cws.cwsbaseapplication.controller.config.CWSBaseApplication;
import com.cws.cwsbaseapplication.controller.utils.Constants;

public class SharedPreferencesManager {
    private static SharedPreferencesManager manager;
    private SharedPreferences sharedPref;

    private SharedPreferencesManager() {

    }

    private static SharedPreferences getSharedPreferencesInstance() {
        if (manager == null) {
            manager = new SharedPreferencesManager();
            Context ctx = CWSBaseApplication.getInstance();
            manager.sharedPref = ctx.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        }
        return manager.sharedPref;
    }

    public static void deleteAll() {
        //String token = SharedPreferencesManager.getStringPreference(Constants.GCM_REGISTRATION_TOKEN, Constants.EMPTY_STRING);
        getSharedPreferencesInstance().edit().clear().commit();
        //setPreference(Constants.GCM_REGISTRATION_TOKEN,token);
    }

    public static void setPreference(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPreference(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setPreference(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void removePreference(String key) {
        SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
        editor.remove(key);
        editor.commit();
    }

    public static String getStringPreference(String key, String defValue) {
        return getSharedPreferencesInstance().getString(key, defValue);
    }

    public static int getIntPreference(String key, int defValue) {
        return getSharedPreferencesInstance().getInt(key, defValue);
    }

    public static Boolean getBooleanPreference(String key, boolean defValue) {
        return getSharedPreferencesInstance().getBoolean(key, defValue);
    }
}
