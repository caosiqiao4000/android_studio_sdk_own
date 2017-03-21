package com.qs.sdk.dialog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

/**
 * 等待进度提示
 *
 * @author wangxin
 */
public class WaitingDialog extends Dialog {

    private TextView mTextView;

    public WaitingDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);
        mTextView = (TextView) findViewById(R.id.left_textView);
        mTextView.setText(R.string.loading);
    }

    public WaitingDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }

    protected WaitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    public void setWaitMessage(String message) {
        mTextView.setText(message);
    }

    public void setWaitMessage(int resid) {
        mTextView.setText(resid);
    }
}
