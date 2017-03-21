package com.qs.sdk.webview.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.qs.sdk.web.R;
import com.qs.sdk.webview.util.SDKSettings;

/**
 * 内置网页弹框 主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
 *
 * @author boge
 * @time 2013-3-26
 */
public class CustomWebChromeClient extends WebChromeClient {

    private Animation animal;
    private ProgressBar mProgressBar;
    private CustomWebView mCurrentWebView;
    private Activity context;
//	private BaseWebViewDao webViewDao;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onProgressChanged newProgress = "
                    + newProgress);
        }
        ((CustomWebView) view).setProgress(newProgress);
        if (newProgress < 100) {
            mProgressBar.setProgress(mCurrentWebView.getProgress());
            if (mProgressBar.getVisibility() == View.GONE)
                mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setProgress(mCurrentWebView.getProgress());
            // 运行动画
            mProgressBar.startAnimation(animal);
        }
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onProgressChanged newProgress = "
                    + newProgress);
        }
        // super.onProgressChanged(view, newProgress);
    }

    //	public CustomWebChromeClient(Activity context, BaseWebViewDao webViewDao,
    public CustomWebChromeClient(Activity context,
                                 ProgressBar pb, CustomWebView mCurrentWebView) {
        pb.setMax(100);
        this.mProgressBar = pb;
        this.mCurrentWebView = mCurrentWebView;
//		this.webViewDao = webViewDao;
        this.context = context;
        animal = AnimationUtils
                .loadAnimation(this.context, R.anim.sh_anim_progress);
        animal.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 将 spinner 的可见性设置为不可见状态
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCloseWindow(WebView window) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onCloseWindow 调用");
        }
        super.onCloseWindow(window);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean dialog,
                                  boolean userGesture, Message resultMsg) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        //
//		webViewDao.addTab(false, mViewFlipper.getDisplayedChild());
        //
        transport.setWebView(mCurrentWebView);
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onCreateWindow 调用");
        }
        resultMsg.sendToTarget();

        return true;
        // return super.onCreateWindow(view, dialog, userGesture,
        // resultMsg);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG,
                    "onConsoleMessage " + consoleMessage.message() + " "
                            + consoleMessage.sourceId());
        }
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (SDKSettings.DEBUG) {
            Log.w(SDKSettings.LOG_TAG, "onShowCustomView 调用");
        }
//		webViewDao.showCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        if (SDKSettings.DEBUG) {
            Log.w(SDKSettings.LOG_TAG, "onHideCustomView 调用");
        }
//		webViewDao.hideCustomView();
    }

    /**
     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onJsAlert  is callback");
        }
        new AlertDialog.Builder(view.getContext())
                .setTitle("消息")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();

        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message,
                                    JsResult result) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onJsBeforeUnload  is callback");
        }
        return super.onJsBeforeUnload(view, url, message, result);
    }

    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               final JsResult result) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, " onJsConfirm  is callback");
        }
        new AlertDialog.Builder(view.getContext())
                .setTitle("消息")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result.confirm();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result.cancel();
                            }
                        }).create().show();

        return true;
    }

    /**
     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
     * window.prompt('请输入您的域名地址', '618119.com');
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, final JsPromptResult result) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, " onJsPrompt  is callback");
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                view.getContext());
        builder.setTitle("消息").setMessage(message);
        final EditText et = new EditText(view.getContext());
        et.setSingleLine();
        et.setText(defaultValue);
        builder.setView(et);
        builder.setPositiveButton(android.R.string.ok,
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm(et.getText().toString());
                    }
                }).setNeutralButton(android.R.string.cancel,
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        // 禁止响应按back键的事件
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier,
                                        long quota, long estimatedDatabaseSize, long totalQuota,
                                        QuotaUpdater quotaUpdater) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onExceededDatabaseQuota 调用");
        }
        // 扩充缓存的容量
        quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
    }

    @Override
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
                                         QuotaUpdater quotaUpdater) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onReachedMaxAppCacheSize 调用");
        }
        // 扩充缓存的容量
        quotaUpdater.updateQuota(requiredStorage * 2);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onReceivedIcon 调用");
        }
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onReceivedTitle 调用");
        }
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onRequestFocus(WebView view) {
        if (SDKSettings.DEBUG) {
            Log.i(SDKSettings.LOG_TAG, "onRequestFocus 调用");
        }
        super.onRequestFocus(view);
    }
}
