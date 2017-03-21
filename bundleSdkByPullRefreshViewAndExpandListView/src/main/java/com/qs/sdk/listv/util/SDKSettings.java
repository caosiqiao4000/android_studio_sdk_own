package com.qs.sdk.listv.util;

import android.util.Log;

public class SDKSettings {
    public static final boolean DEBUG = true;

    /**
     * 是否使用新服务器
     */
//	public static final boolean DEBUG_BY_NEW_SERVER = true;

    public static final int LOGGER_LEVEL = Log.DEBUG;
    public static final String LOG_TAG = "S_H_SDK";

    public static final String ASSETS_DIR = "shsdkconf/";

    // =================== 登录 =======================
    public static final String SH_SDK_LOGIN_BY_HTML_STATE = "login_state";
    /**
     * 登录成功
     */
    public static final int SH_SDK_LOGIN_SUCCESS = 0x11;
    /**
     * 登录失败
     */
    public static final int SH_SDK_LOGIN_FAIL = 0x12;
    // 末登录
    public static final int SH_SDK_LOGIN_NO = 0x13;
    // 登录未知状态
    public static final int SH_SDK_LOGIN_OTHER = 0x14;
    public static final String SH_SDK_LOGIN_NAME = "login_name";
    public static final String SH_SDK_LOGIN_UID = "login_uid";

    // ======================== 配置 =================================
    // 协议版本
    // public static final String TreatyEdition = "1.0.0.0";
    // SDK版本
    // public static final String ExtVersion = "1.0.1";
    // // 应用ID键值
    // public static final String AppIdKey = "DDleAppId";
    // // 渠道号
    // public static final String ChannelIdKey = "DDleChannelId";
    // // SDKId号
    // public static final String SdkIdKey = "DDleSdkId";

    public static String UrlReport = "";
    public static final String EXTRA_SAVED_URL = "EXTRA_SAVED_URL";

}
