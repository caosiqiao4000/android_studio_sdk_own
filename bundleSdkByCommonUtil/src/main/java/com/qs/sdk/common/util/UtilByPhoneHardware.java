package com.qs.sdk.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qs.sdk.common.inter.IUICallBackInterface;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.取手机号 设置ID 系统版本 型号 操作系统版本... 2.手机网络判断
 * <p/>
 * <pre>
 * 3. 手机调用系统功能(照相机,浏览器,音乐,视频...)
 *
 * 程序常用 的公共方法
 *
 * @author Administrator
 */
public class UtilByPhoneHardware {

    /**
     * 获取自身类名
     *
     * @author wubo
     * @createtime 2012-7-9
     */
    public static String getClassName() {
        return new Throwable().getStackTrace()[1].getClassName();
    }

    /**
     * 获取屏幕的大小
     *
     * @param context
     * @return dm.widthPixels屏幕宽度 dm.heightPixels屏幕高度
     */
    public static DisplayMetrics getPhoneScreenPixel(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);// dm用于获取手机屏幕大小
        return dm;
    }

    /**
     * 检查是否有网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkIsActive(Context context) {
        boolean mIsNetworkUp = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            mIsNetworkUp = info.isAvailable();
        }
        return mIsNetworkUp;
    }

    /**
     * 取得网络类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            return info.getType();
        }
        return -1;
    }


    /**
     * 打开浏览器
     *
     * @param context
     * @param url     http://www.duotaozx.com http://xxx
     */
    public static void toOSWebViewIntent(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param phoneNum
     */
    public static void toOSCallPhoneIntent(Context context, String phoneNum) {
        Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        context.startActivity(it);
    }

    /**
     * 判断SD卡目录是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExitFileSD(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 将editView的光标设置到文字的最后
     */
    public static void setEditCursorToTextEnd(EditText et_dialog) {
        CharSequence text = et_dialog.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 隐藏手机号
     *
     * @param phoneNum
     * @return
     * @author wubo
     * @createtime 2012-9-7
     */
    public static String hidePhoneNum(String phoneNum) {
        if (phoneNum == null) {
            return "";
        }
        if (phoneNum.length() == 11) {
            char[] cs = phoneNum.toCharArray();
            cs[3] = '*';
            cs[4] = '*';
            cs[5] = '*';
            cs[6] = '*';
            return new String(cs);
        }
        return phoneNum;
    }

    /**
     * 隐藏银行卡号
     *
     * @param bankNum
     * @return
     */
    public static String hideBankNum(String bankNum) {
        if (bankNum == null) {
            return "";
        }
        if (bankNum.length() == 19) {
            char[] cs = bankNum.toCharArray();
            for (int i = 4; i < bankNum.length() - 4; i++) {
                cs[i] = '*';
            }
            return new String(cs);
        }
        return bankNum;
    }


    /**
     * 删除指定的SD卡目录
     *
     * @param path
     * @return
     */
    public static boolean deleteFileSD(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 一个程序的根目录名称
     *
     * @return 2016年1月20日 qs
     */
    public static String getSdkardDir() {
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/TeleMarket");
//			File file = new File(Environment.getExternalStorageDirectory().toString() + rootDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.toString();
        }
        return null;
    }

    /**
     * 创建新的SD卡目录
     *
     * @param fileName
     * @return
     */
    public static boolean createFile(String fileName) {
        try {
            File file = new File(getSdkardDir(), fileName);
            if (file.exists()) {
                file.delete();
            }
            if (file.createNewFile()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取版本编号
     *
     * @param context
     * @return
     * @author wubo
     * @time 2012-11-23
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 将后台正常图片地址转换成自己需要的大小图片地址
     *
     * @param url    后台正常大小的图片地址
     * @param suffix 要获取的大小 如 FinalUtil.PIC_SMALL
     * @return
     * @author caosq 2013-6-6 下午7:59:43
     */
    public static String converPicPath(String url, String suffix) {
        if (url == null || url.trim().length() < 1) {
            return null;
        }
        int a = url.lastIndexOf(".");
        String aString = url.substring(a, a + 1);
        return url.replace(aString, suffix + aString);
    }

    static long difference_time = 0;

    /**
     * prompt user
     *
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {
        if (null != context)
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int aa) {
        if (null != context)
            Toast.makeText(context, aa, Toast.LENGTH_SHORT).show();
    }

    /**
     * 取得当前时间 格式yyyy-MM-dd HH:MM:ss
     * <p/>
     * 经过与服务器时间偏差处理
     *
     * @return
     */
    public static String getSysNowTime() {
        Date now = new Date();
        now = new Date(now.getTime() + difference_time);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formatTime = format.format(now);
        return formatTime;
    }

    /**
     * 取得当前时间 格式yyyyMMddHHMMss
     * 保存图片/文件使用
     *
     * @return
     */
    public static String getNowTime() {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String formatTime = format.format(now);
        return formatTime;
    }

    /**
     * @param dateStr yyyy-MM-dd HH:mm:ss "2008-08-08 12:10:12"
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateByString(String dateStr) {
        if (null == dateStr) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换指定的日期
     *
     * @param date
     * @return 2015年12月24日 qs
     */
    public static String getStringByDate(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formatTime = format.format(date);
        return formatTime;
    }

    /**
     * 系统时间与服务器时间差异
     *
     * @author wubo
     * @createtime 2012-9-12
     * @param differenceTime
     */
    // public static void setDifference_time() {
    // difference_time = Util.getServerSysTime() - new Date().getTime();
    // Log.e("myOpenfire", "系统时间与服务器时间差异 = " + difference_time);
    // }

    /**
     * 获取毫秒级时间
     *
     * @return
     * @author wubo
     * @createtime 2012-9-10
     */
    public static String getLongDate() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * 判断邮箱是否存在
     */
    public static boolean isEmpty(String x) {
        if (x == null) {
            return true;
        } else if (x.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 转换时间
     *
     * @return
     * @author caosq 2013-6-8 下午4:49:43
     */
    public static String getNowPullTime() {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String formatTime = format.format(now);
        return formatTime;
    }

    /**
     * 验证手机号
     *
     * @param str
     * @return
     * @author wubo
     * @createtime 2012-9-13
     */
    public static boolean isCellphone(String str) {
        String MOBILE_REGEX = "^((\\+86)|(86))?[1][123456789]\\d{9}$";
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * [`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]
     *
     * @param str
     * @return true 有特别字符 2015年11月18日 qs
     */
    public static boolean isSpecialStr(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 验证邮箱
     *
     * @param strEmail
     * @return
     * @author wubo
     * @createtime 2012-9-13
     */
    public static boolean isEmail(String strEmail) {
        // ^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证身份证
     *
     * @param strCard
     * @return
     * @author wubo
     * @createtime 2012-9-13
     */
    public static boolean isChinaCard(String strCard) {
        String strPattern = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strCard);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * getPhoneIMSIOrIMEI
     *
     * @param context
     * @param imeiOrimsi "imei" / "imsi"
     * @return
     */
    public static String getPhoneIMSIOrIMEI(Context context, String imeiOrimsi, String defaultStr) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String str;
        if ("imsi".equals(imeiOrimsi)) {
            str = mTelephonyMgr.getSubscriberId();
        } else {
            str = mTelephonyMgr.getDeviceId();
        }
        if (null == str) {
            return defaultStr;
        }
        return str;
    }

    /**
     * @param context
     * @param nx_et
     * @param isBefore 判断 true 在当前时间之后 false (生日)在当前时间之前
     * @return 2015年11月17日 qs
     */
    public static void setEditViewDate(final Context context, final EditText nx_et, final boolean isBefore,
                                       final int promptId) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth;
        if (isBefore) {
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        } else {
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        }
        DatePickerDialog begindate = new DatePickerDialog(context, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
                String birthday_item = simple.format(cal.getTime());
                // true 选择的时间在当前时间之后
                boolean te = Calendar.getInstance().before(cal);
                if (te == isBefore) {
                    nx_et.setText(birthday_item);
                } else {
                    UtilByPhoneHardware.showToast(context, promptId);
                }
            }
        }, year, monthOfYear, dayOfMonth);
        begindate.show();
    }

    /**
     * TODO 去掉字符串中间的空格和 “-”字符(此处通讯录号码去空格)
     *
     * @param str 原始字符串
     * @return 结果字符串
     */
    public static String delSpace(String str) {
        if (str == null) {
            return null;
        }
        char[] strOld = str.toCharArray();
        StringBuffer strNew = new StringBuffer();
        for (char a : strOld) {
            if (a != ' ' && a != '-') {
                strNew.append(a);
            }
        }
        return strNew.toString().trim();
    }

    /**
     * 通讯录
     *
     * @param str
     * @return String
     */
    public static String getAlpha(String str) {
        if (str == null || str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftInput(Activity activity) {
        // 隐藏软键盘
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {// 如果没有输入框就没有焦点
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 取得网页的标题,如果没用就使用自己的标题
     * return maybe null or ""
     */
    public static void getWebViewTitle(final WebView webView, final IUICallBackInterface backInterface, final String originName) {
        // 使用自定义标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String javascript = "(function() { " +
                    "var titles = document.getElementsByTagName(\"title\");" +
                    "if (titles == null || titles.length != 1) return \"\";" +
                    "return titles[0].textContent; " +
                    "})();";
            final String[] htmlTitleString = {null};
            webView.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    htmlTitleString[0] = s.replace("\"", "");
                    if (backInterface != null) {
                        String webName = webView.getTitle() == null ? "" : webView.getTitle();
//                        Log.i("====================", "getWebViewTitle webName= " + webName);
//                        Log.i("====================", "getWebViewTitle webView.getUrl()= " + webView.getUrl());
//                        Log.i("====================", "getWebViewTitle htmlTitleString[0]= " + htmlTitleString[0]);
                        if (TextUtils.isEmpty(htmlTitleString[0])) {// HTML <title></title>
                            if (TextUtils.isEmpty(webName)) {//如果取不到网页的标题,在一些SDK的版本中使用webView.getTitle()取不到标题
                                backInterface.uiCallBack(originName, 0);
                            } else {
                                if (webName.length() > 40 || webView.getUrl().contains(webName)) {
                                    backInterface.uiCallBack(originName, 0);
                                } else {
                                    backInterface.uiCallBack(webName, 0);
                                }
                            }
                        } else {
                            backInterface.uiCallBack(htmlTitleString[0], 0);
                        }
                    }
                }
            });
            return;
        } else {
            backInterface.uiCallBack(webView.getTitle() == null ? originName : webView.getTitle(), 0);
        }
    }

    /**
     * 使状态栏透明z
     * <p/>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity                  需要设置的activity
     * @param color                     状态栏颜色值
     *                                  http://jaeger.itscoder.com/android/2016/02/15/status-bar-demo.html
     *                                  在 setContentView() 之后调用 setColor(Activity activity, int color) 方法即可。
     * @param customTitleOrDrawerLayout 是否自定义标题栏
     */
    public static void setColor(Activity activity, int color, boolean customTitleOrDrawerLayout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            activity.getWindow().setStatusBarColor( activity.getResources().getColor(color));
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(color);
//            window.setNavigationBarColor(color);
//
//        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //导航栏 @ 底部
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//B
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView, 0);
            if (customTitleOrDrawerLayout) {
                // 内容布局不是 LinearLayout 时,设置padding top
//            if (!(decorView instanceof LinearLayout) && decorView.getChildAt(1) != null) {
                if (decorView.getChildAt(1) != null) {
                    decorView.getChildAt(1).setPadding(0, getStatusBarHeight(activity) + 20, 0, 0);
                }
                // 设置属性
//                ViewGroup drawer = (ViewGroup) decorView.getChildAt(1);
//                drawer.setFitsSystemWindows(false);
                decorView.setFitsSystemWindows(false);//true 有空白
                decorView.setClipToPadding(false);//
            } else {
                // 设置根布局的参数
                ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
//            ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
//            View parentView = contentFrameLayout.getChildAt(0);
//            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
//                parentView.setFitsSystemWindows(true);
//            }
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        Log.i("status_bar_height", "========== " + statusBarHeight);
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


}
