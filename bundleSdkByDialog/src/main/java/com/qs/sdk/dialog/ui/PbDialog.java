package com.qs.sdk.dialog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

/**
 * HTTP加载提示框架
 *
 * @author Administrator
 */
public class PbDialog extends Dialog {
    // 加载提示内容
    private TextView txtMsg;

    /**
     * HTTP加载提示框架
     */
    public PbDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sh_loading_layout);

        txtMsg = (TextView) findViewById(R.id.sh_left_textView);
    }

    public void setContentPrompt(String txtmsg) {
        txtMsg.setText(txtmsg);
    }
}
