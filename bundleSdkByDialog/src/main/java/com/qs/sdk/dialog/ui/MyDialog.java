package com.qs.sdk.dialog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

import java.util.HashMap;
import java.util.Map;

/**
 * MyDialog.showDialog(context, title, contentText, R.string.submit,<br>
 * R.string.cancel, false, new OnClickListener() {<br>
 *
 * @author Administrator
 * @Override public void onClick(View v) {<br>
 * //点击确定 } }); 提示内容后 选择同意与取消的选择dialog
 */
public class MyDialog {
    private Dialog dialog; // 所拥有的对话框
    private Map<Object, Button> btns = new HashMap<Object, Button>();
    private Object contentText;

    /**
     * 显示一个对话框
     *
     * @param titleText   标题
     * @param contentText 内容
     * @param leftText    左边的按钮
     * @param rightText   右边的按钮
     * @param force       是否要给右边的按钮设置监听器
     */
    public static MyDialog showDialog(Context context, Object titleText, Object contentText, Object leftText,
                                      Object rightText, boolean force, OnClickListener clickListener) {
        final MyDialog myDialog = new MyDialog();
        myDialog.contentText = contentText;

        // 实例化布局文件
        LinearLayout dialogView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog, null);

        // 标题
        TextView title = (TextView) dialogView.findViewById(R.id.dialog_title);
        title.setText(parseParam(context, titleText));

        // 内容
        TextView cont = (TextView) dialogView.findViewById(R.id.dialog_content);
        if (contentText instanceof View) { // 需要移除中间的TextView
            LinearLayout contLayout = (LinearLayout) cont.getParent();
            contLayout.removeView(cont); // 移除TextView
            contLayout.addView((View) contentText); // 添加新的View
        } else { // 设置中间内容的文字
            cont.setText(parseParam(context, contentText));
        }

        // 左边的按钮
        Button left = (Button) dialogView.findViewById(R.id.dialog_left);
        if (leftText == null) { // 说明不需要按钮
            // 得到按钮所在的布局
            View btnsLayout = (View) left.getParent().getParent();
            // 移除按钮布局
            dialogView.removeView(btnsLayout);
        } else {
            left.setText(parseParam(context, leftText));
            // 判断上下文是否为监听器
            if (null != clickListener) {
                left.setOnClickListener(clickListener);
            } else {
                if (context instanceof View.OnClickListener) {
                    left.setOnClickListener((OnClickListener) context);
                }
            }
            myDialog.btns.put(leftText, left); // 放按钮

            // 右边的按钮
            Button right = (Button) dialogView.findViewById(R.id.dialog_right);
            if (rightText == null) { // 不需要右边的按钮
                LinearLayout rightParent = (LinearLayout) right.getParent(); // 得到右边按钮的父控件
                LinearLayout bottomLayout = (LinearLayout) rightParent.getParent(); // 得到底部最大的layout
                bottomLayout.removeView(rightParent); // 删除右边的布局
            } else {
                right.setText(parseParam(context, rightText));

                if (force) { // 如果要给右边的按钮强制设定监听器
                    // 判断上下文是否为监听器
                    if (context instanceof View.OnClickListener) {
                        right.setOnClickListener((OnClickListener) context);
                    }
                } else {
                    right.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            dismiss(myDialog); // 默认关闭对话框
                        }
                    });
                }
                myDialog.btns.put(rightText, right); // 放按钮
            }
        }

        // Dialog dialog = new AlertDialog.Builder(context).create();
        Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show(); // 先显示对话框
        // 这里得到的window其实是中间的内容
        dialog.getWindow().setContentView(dialogView); // 设置对话框的内容
        myDialog.dialog = dialog;

        return myDialog;
    }

    /**
     * 显示一个对话框
     *
     * @param titleText   标题
     * @param contentText 内容
     * @param leftText    左边的按钮
     * @param rightText   右边的按钮
     */
    public static MyDialog showDialog(Context context, Object titleText, Object contentText, Object leftText,
                                      Object rightText) {
        return showDialog(context, titleText, contentText, leftText, rightText, false, null);
    }

    /**
     * 只显示一个按钮
     */
    public static MyDialog showDialog(Context context, Object titleText, Object contentText, Object leftText) {
        return showDialog(context, titleText, contentText, leftText, null);
    }

    /**
     * 不要按钮
     */
    public static MyDialog showDialog(Context context, Object titleText, Object contentText) {
        return showDialog(context, titleText, contentText, null, null);
    }

    /**
     * 解析参数
     *
     * @param msg
     * @return
     */
    private static String parseParam(Context context, Object msg) {
        if (msg instanceof Integer) { // 如果传的是int类型
            // R.string.sure
            int resId = Integer.parseInt(msg.toString());
            msg = context.getString(resId);
        }
        return msg.toString();
    }

    /**
     * 得到相应的按钮
     *
     * @param myDialog 所属的对话框
     * @param key      按钮的key
     * @return
     */
    public static Button getBtn(MyDialog myDialog, Object key) {
        if (myDialog == null) {
            return null;
        } else {
            return myDialog.btns.get(key);
        }
    }

    /**
     * 关闭对话框
     *
     * @param myDialog
     */
    public static void dismiss(MyDialog myDialog) {
        // 如果对话框不为null 而且正在显示
        if (myDialog != null && myDialog.dialog != null && myDialog.dialog.isShowing()) {
            myDialog.dialog.dismiss();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Map<Object, Button> getBtns() {
        return btns;
    }

    public void setBtns(Map<Object, Button> btns) {
        this.btns = btns;
    }

    public Object getContentText() {
        return contentText;
    }
}
