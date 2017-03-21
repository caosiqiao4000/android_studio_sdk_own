package com.qs.sdk.webview.ui;

import android.content.Context;
import android.view.MotionEvent;

/**
 * 使用ViewPage加载多个 webview时专用
 * http://www.pedant.cn/2014/09/23/webview-touch-conflict/
 *
 * @author Administrator
 */
public class ViewPageCustomWebView extends CustomWebView {

    public ViewPageCustomWebView(Context context) {
        super(context);
    }

    boolean mIgnoreTouchCancel;

    public void ignoreTouchCancel(boolean val) {
        mIgnoreTouchCancel = val;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return ev.getAction() == MotionEvent.ACTION_CANCEL && mIgnoreTouchCancel || super.onTouchEvent(ev);
    }
}
