package com.teamadr.ecommerceapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.model.response.auth.AuthenticationResult;

public class UserAuth {
    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(StringConstant.SHARE_PREF_AUTH,
                Context.MODE_PRIVATE);
        return preferences.getString(StringConstant.KEY_USER_ID, "");
    }

    public static String getUserType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(StringConstant.SHARE_PREF_AUTH,
                Context.MODE_PRIVATE);
        return preferences.getString(StringConstant.KEY_USER_TYPE, "");
    }

    public static String getBearerToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                StringConstant.SHARE_PREF_AUTH, Context.MODE_PRIVATE);
        return RequestContansts.BEARER + preferences.getString(StringConstant.KEY_ACCESS_TOKEN, "");
    }

    public static String getUserPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                StringConstant.PREF_PASSWORD, Context.MODE_PRIVATE);
        return preferences.getString(StringConstant.KEY_PASSWORD, "");
    }

    public static void saveAccessToken(Context context, AuthenticationResult authenticationResult) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                StringConstant.SHARE_PREF_AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (authenticationResult == null) {
            editor.putString(StringConstant.KEY_ACCESS_TOKEN, null);
            editor.putString(StringConstant.KEY_REFRESH_TOKEN, null);
            editor.putBoolean(StringConstant.KEY_LOGGED_IN, false);
        } else {
            editor.putString(StringConstant.KEY_ACCESS_TOKEN,
                    authenticationResult.getAccessToken());
            editor.putString(StringConstant.KEY_REFRESH_TOKEN,
                    authenticationResult.getRefreshToken());
            editor.putBoolean(StringConstant.KEY_LOGGED_IN, true);
        }
        editor.apply();
    }

    public static void saveUser(Context context, String userID, String userType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                StringConstant.SHARE_PREF_AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StringConstant.KEY_USER_ID, userID);
        editor.putString(StringConstant.KEY_USER_TYPE, userType);
        editor.apply();
    }

    public static void savePassword(Context context, String password){
        SharedPreferences preferences = context.getSharedPreferences(StringConstant.PREF_PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(StringConstant.KEY_PASSWORD, password);
        editor.apply();
    }

    public static void clearAllSaveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                StringConstant.SHARE_PREF_AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
