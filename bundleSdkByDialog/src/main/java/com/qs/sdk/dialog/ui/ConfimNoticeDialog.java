package com.qs.sdk.dialog.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qs.sdk.dialog.R;

/**
 * 通知信息对话框
 *
 * @author Administrator
 */
public class ConfimNoticeDialog extends Dialog {
    private TextView mDialogTitle;// >>>>>>对话框标题
    private TextView mTitle;// >>>>>子标题
    private TextView mContent;// >>>>内容
    private Button mBtnConfim;
    private Button mBtnCancel;

    public ConfimNoticeDialog(Context context) {
        super(context, R.style.sh_MyDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sh_system_dialog);
        // 系统中关机对话框就是这个属性
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        window.setAttributes(lp);
        // window.addFlags(WindowManager.LayoutParams.FLAGS_CHANGED);
        // window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        mTitle = (TextView) findViewById(R.id.tv_notice_title);
        mContent = (TextView) findViewById(R.id.tv_notice_content);
        mContent.setVisibility(View.GONE);
        mBtnConfim = (Button) findViewById(R.id.sh_btn_ensure);
        mBtnCancel = (Button) findViewById(R.id.sh_btn_cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                closeDialog();
            }
        });
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title) && title.trim().length() > 0) {
            this.mTitle.setVisibility(View.VISIBLE);
            this.mTitle.setText(title);
        } else
            this.mTitle.setVisibility(View.GONE);
    }

    public void setTitleVisibility(int v) {
        this.mTitle.setVisibility(v);
    }

    public void setTitle(int id) {
        if (id > 0) {
            this.mTitle.setVisibility(View.VISIBLE);
            this.mTitle.setText(id);
        } else
            this.mTitle.setVisibility(View.GONE);
    }

    public void setContent(String title) {
        mContent.setVisibility(View.VISIBLE);
        this.mContent.setText(title);
    }

    public void setContent(int id) {
        if (id > 0) {
            mContent.setVisibility(View.VISIBLE);
            this.mContent.setText(id);
        }
    }

    public void setDialogTitle(int id) {
        this.mDialogTitle.setText(id);
    }

    public void setDialogTitle(String mDialogTitle) {
        this.mDialogTitle.setText(mDialogTitle);
    }

    public void setConfimOnClickListener(View.OnClickListener clickListener) {
        this.mBtnConfim.setVisibility(View.VISIBLE);
        this.mBtnConfim.setOnClickListener(clickListener);
    }

    public void setCancelOnClickListener(View.OnClickListener clickListener) {
        this.mBtnCancel.setVisibility(View.VISIBLE);
        this.mBtnCancel.setOnClickListener(clickListener);
    }

    public void setConfimOnClickListener(String postiveTitle,
                                         View.OnClickListener clickListener) {
        this.mBtnConfim.setVisibility(View.VISIBLE);
        this.mBtnConfim.setText(postiveTitle);
        this.mBtnConfim.setOnClickListener(clickListener);
    }

    public void setCancelOnClickListener(String navgetiveTitle,
                                         View.OnClickListener clickListener) {
        this.mBtnCancel.setVisibility(View.VISIBLE);
        this.mBtnCancel.setText(navgetiveTitle);
        this.mBtnCancel.setOnClickListener(clickListener);
    }

    protected void showToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    public void setTextSize(int titleSize, int contentSize) {
        mTitle.setTextSize(titleSize);
        mContent.setTextSize(contentSize);
    }

    public void setButtonName(int mBtnConfimName, int mBtnCancelName) {
        mBtnConfim.setText(mBtnConfimName);
        mBtnCancel.setText(mBtnCancelName);
    }

    public void setButtonName(String mBtnConfimName, String mBtnCancelName) {
        mBtnConfim.setText(mBtnConfimName);
        mBtnCancel.setText(mBtnCancelName);
    }

    public void closeDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
}
