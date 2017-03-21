package com.qs.sdk.common.other.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 这是一个保存共享的数据工具类
 * http://stackoverflow.com/questions/31926057/shared-preference-null-pointer-
 * exception java.lang.NullPointerException: Attempt to invoke virtual method
 * 'android.content.SharedPreferences
 * android.content.Context.getSharedPreferences(java.lang.String, int)' on a
 * null object reference
 *
 * @author Administrator
 */
public class SharedPreferencesUtil {
    /**
     * 服务器接口更改缓存
     */
    public static final String S_H_SERVICEADDRS_FLAG = "serviceAddrCache";

    private SharedPreferences mSharedPreferences;
    private Context context;

    public SharedPreferencesUtil(final Context context, String xmlName) {
        this.context = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//		mSharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
    }

    // 保存字符串
    public void saveString(String key, String value) {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error error saveString keyAndValue lenght must greater than 0  or use removeObject method " + key);
            return;
        }
        mSharedPreferences.edit().putString(key, value).commit();
    }

    // 获取字符串
    public String getString(String key, String defaultStr) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error error getString key lenght must greater than 0 ");
            return defaultStr;
        }
        return mSharedPreferences.getString(key, defaultStr);
    }

    // 保存布尔值
    public void saveInt(String key, Integer value) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... saveInt key lenght must greater than 0 ");
            return;
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    // 获取布尔值
    public Integer getInt(String key, Integer defValue) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... getInt key lenght must greater than 0 ");
            return defValue;
        }
        return mSharedPreferences.getInt(key, defValue);
    }

    // 保存布尔值
    public void saveBoolean(String key, Boolean value) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... saveBoolean key lenght must greater than 0 ");
            return;
        }
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    // 获取布尔值
    public Boolean getBoolean(String key, Boolean... defValue) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... getBoolean key lenght must greater than 0 ");
            return defValue[0];
        }
        if (defValue.length > 0)
            return mSharedPreferences.getBoolean(key, defValue[0]);
        else
            return mSharedPreferences.getBoolean(key, false);

    }

    // 保存布尔值
    public void saveFloat(String key, Float value) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... saveFloat key lenght must greater than 0 ");
            return;
        }
        mSharedPreferences.edit().putFloat(key, value).commit();
    }

    // 获取布尔值
    public Float getFloat(String key, Float... defValue) {
        if (TextUtils.isEmpty(key)) {
//            UtilByPhoneHardware.showToast(context, "error ... getFloat key lenght must greater than 0 ");
            return defValue[0];
        }
        if (defValue.length > 0)
            return mSharedPreferences.getFloat(key, defValue[0]);
        else
            return mSharedPreferences.getFloat(key, 0.0f);

    }

    // 返回用户的信息。
    public JSONObject getJSON(String count) {
        if (TextUtils.isEmpty(count)) {
//            UtilByPhoneHardware.showToast(context, "error ... getJSON key lenght must greater than 0 ");
            return null;
        }
        JSONObject object;
        try {
            object = new JSONObject(mSharedPreferences.getString(count, ""));
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveJSONObject(String count, String json) {
        if (TextUtils.isEmpty(count) || TextUtils.isEmpty(json)) {
//            UtilByPhoneHardware.showToast(context, "error ... saveJSONObject keyAndValue lenght must greater than 0 ");
            return;
        }
        mSharedPreferences.edit().putString(count, json).commit();
    }

    /**
     * @param count
     */
    public void removeObject(String count) {
        if (TextUtils.isEmpty(count)) {
//            UtilByPhoneHardware.showToast(context, "error ... removeObject key lenght must greater than 0 ");
            return;
        }
        try {
            mSharedPreferences.edit().remove(count).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }
}
