package com.qs.sdk.webview.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qs.sdk.common.inter.IUICallBackInterface;
import com.qs.sdk.common.other.util.SharedPreferencesUtil;
import com.qs.sdk.webview.util.SDKSettings;

import java.util.Map;

public abstract class BaseWebViewClient extends WebViewClient {
    protected IUICallBackInterface callBackInterface;
    protected final String sdkPriStr = "privateStr";
    protected SharedPreferencesUtil s_h_util;
    protected final String PREVIOU_CHECK_JSCSS_TIME = "jschecktime";
    private String aString = null;// 取后面的21位,防止重复

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        if (view instanceof CustomWebView) {
            ((CustomWebView) view).resetLoadedUrl();
        }
        if (url.startsWith("weixin:")) {
            Context context = view.getContext();
            if (aString == null) {
                aString = url.substring(url.length() - 20, url.length());
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(it);
            } else if (!url.endsWith(aString)) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(it);
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onFormResubmission is callback");
        }
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onLoadResource(WebView view, final String url) {
        // 这里保存JS css
        if (SDKSettings.DEBUG) {
            Log.w(SDKSettings.LOG_TAG, "onLoadResource url = " + url);
        }
        // if (url.endsWith(".css") || url.endsWith(".js")) {

        // File appFile = new File(context.getFilesDir().getAbsolutePath()
        // + "/htmlcache");
        // if (!appFile.exists()) {
        // appFile.mkdirs();
        // }
        // File[] files = appFile.listFiles();
        // final String aString = url.substring(url.lastIndexOf("/") + 1,
        // url.length());
        // if (files.length > 0) {
        // for (File file : files) {
        // if (file.getName().contains(aString)) {// 如果已经下载了
        // // 判断本地的与网络上的是否一样
        // // final String md5_1 = SyncHttpClient
        // // .generateMd5(file);
        // final long diffTime = System.currentTimeMillis()
        // - Long.valueOf(s_h_util.getString(
        // PREVIOU_CHECK_JSCSS_TIME, "0"));
        // if (diffTime > 36000000) {
        // if (SDKSettings.DEBUG) {
        // Log.i(SDKSettings.LOG_TAG,
        // " 上次检查JS时间为 "
        // + s_h_util
        // .getString(
        // PREVIOU_CHECK_JSCSS_TIME,
        // "0"));
        // }
        // s_h_util.saveString(PREVIOU_CHECK_JSCSS_TIME,
        // String.valueOf(System.currentTimeMillis()));
        // final DownloadAsyncTask downTask = new DownloadAsyncTask(
        // aString, url, new File(appFile + "/"
        // + aString), s_h_util);
        // downTask.execute();
        // }
        // final String fileUrl = "file://"
        // + file.getAbsolutePath();
        // super.onLoadResource(view, fileUrl);
        // }
        // }
        // } else {
        // DownloadAsyncTask downTask = new DownloadAsyncTask(url,
        // new File(appFile + "/" + aString));
        // downTask.execute();
        // super.onLoadResource(view, url);
        // super.onLoadResource(view, url);
        // }
        // }
    }

    /**
     * 子类使用这个方法 显示错误网页
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        // if (baseWebViewActivity instanceof WebViewNewsReader
        // || baseWebViewActivity instanceof PayGameActivity) {
        // view.stopLoading();
        // baseWebViewActivity.errorLoad();
        // } else {
        super.onReceivedError(view, errorCode, description, failingUrl);
        // }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (SDKSettings.DEBUG) {
            Log.e(SDKSettings.LOG_TAG, "onReceivedSslError is callback");
        }
        handler.proceed();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        WebStorage webStorage = WebStorage.getInstance();
        webStorage.getOrigins(new ValueCallback<Map>() {
            @Override
            public void onReceiveValue(Map map) {
                for (Object key : map.keySet()) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        // WebStorage.Origin origin = (WebStorage.Origin) map
                        // .get(key);
                        if (SDKSettings.DEBUG) {
                            // Log.e(SDKSettings.LOG_TAG, String.format(
                            // "Origin: %s Quota: %s Usage: %s",
                            // origin.getOrigin(), origin.getQuota(),
                            // origin.getUsage()));
                        }
                    } else {
                        Log.e(SDKSettings.LOG_TAG, "Key: " + key + " Value: " + map.get(key));
                    }
                }
            }
        });
        // webStorage.deleteOrigin(url);
        // cache js or css 1M
        webStorage.setQuotaForOrigin(url, 10);
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onPageStarted is callback");
        }
        if (view instanceof CustomWebView) {
            ((CustomWebView) view).notifyPageStarted();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onPageFinished is callback");
        }
        if (webView instanceof CustomWebView) {
            ((CustomWebView) webView).notifyPageFinished();
        }
        if (!webView.getSettings().getLoadsImagesAutomatically()) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        }
        super.onPageFinished(webView, url);
    }

}
