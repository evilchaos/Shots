package com.dribbble.evilchaos.shots.util;

import android.content.Context;
import android.text.TextUtils;

import com.dribbble.evilchaos.shots.entity.User;

/**
 * Created by liujiachao on 2017/1/3.
 */

public class UserLocalData {

    public static void putUser(Context context, User user) {
        String user_json = JsonUtil.toJSON(user);
        PreferencesUtils.putString(context,Constant.USER_JSON,user_json);
    }

    public static void putToken(Context context,String token) {
        PreferencesUtils.putString(context,Constant.TOKEN,token);
    }

    public static User getUser(Context context) {
        String user_json = PreferencesUtils.getString(context,Constant.USER_JSON);
        if (!TextUtils.isEmpty(user_json)) {
            return JsonUtil.fromJson(user_json,User.class);
        }

        return null;
    }

    public static String getToken(Context context) {
        return PreferencesUtils.getString(context,Constant.TOKEN);
    }

    public static void clearUser(Context context) {
        PreferencesUtils.putString(context,Constant.USER_JSON,"");
    }

    public static void clearToken(Context context) {
        PreferencesUtils.putString(context,Constant.TOKEN,"");
    }
}
