package com.example.project_prm.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.project_prm.R;


public class SharedPreferencesUtil {
    private static Context context;
    public SharedPreferencesUtil(Context context) {
        this.context = context;
    }
    static public void addOrUpdateData(SharedPreferencesKey dataKey, String value) {
        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(dataKey.name(), value);
        editor.apply();
    }

    static public void deleteData(SharedPreferencesKey key) {
        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key.name());
        editor.apply();
    }

    static public String getData(SharedPreferencesKey key) {
        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(key.name(), "");
    }
}




