package com.unlogicon.headhunter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nik on 04.09.14.
 */
public class Settings {

    private SharedPreferences sharedPreferences;

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_TOKEN_TYPE = "token_type";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    public Settings(Activity activity) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String token){
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).commit();
    }

    public String getTokenType(){
        return sharedPreferences.getString(KEY_TOKEN_TYPE, null);
    }

    public void setTokenType(String token){
        sharedPreferences.edit().putString(KEY_TOKEN_TYPE, token).commit();
    }

    public String getExpiresIn(){
        return sharedPreferences.getString(KEY_EXPIRES_IN, null);
    }

    public void setExpiresIn(String expiresIn){
        sharedPreferences.edit().putString(KEY_EXPIRES_IN, expiresIn).commit();
    }

    public String getRefreshToken(){
        return sharedPreferences.getString(KEY_TOKEN_TYPE, null);
    }

    public void setRefreshToken(String token){
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, token).commit();
    }

}
