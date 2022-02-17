package com.example.news_app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.news_app.model.AppDatabase;

public class AppPreferences {

    SharedPreferences preferences;
    final public static String IS_USER_LOGGED = "is_user_logged_in";
    public AppPreferences(Context context){
        preferences = context.getSharedPreferences("appPre",Context.MODE_PRIVATE);
    }
    public void setValue(String key, String value){
       preferences.edit().putString(key,value).commit();
    }
    public String getValue(String key){
        return preferences.getString(key,"");
    }
}



