package com.qs.sdk.dialog.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

import java.util.HashMap;
import java.util.Map;

/**
 * DialogFragment在android
 * 3.0时被引入。是一种特殊的Fragment，用于在Activity的内容之上展示一个模态的对话框。典型的用于：展示警告框，输入框，确认框等等。
 * 在DialogFragment产生之前，我们创建对话框：一般采用AlertDialog和Dialog。注：官方不推荐直接使用Dialog创建对话框。
 * <p>
 * 2、 好处与用法
 * 使用DialogFragment来管理对话框，当旋转屏幕和按下后退键时可以更好的管理其声明周期，它和Fragment有着基本一致的声明周期。
 * 且DialogFragment也允许开发者把Dialog作为内嵌的组件进行重用，类似Fragment（可以在大屏幕和小屏幕显示出不同的效果）。
 * 上面会通过例子展示这些好处~ 使用DialogFragment至少需要实现onCreateView或者onCreateDIalog方法。
 * onCreateView即使用定义的xml布局文件展示Dialog。
 * onCreateDialog即利用AlertDialog或者Dialog创建出Dialog。
 * </p>
 *
 * @author Administrator 2015年12月30日 qs
 */
public class MyDialogByFragment extends DialogFragment {

    private Context context;
    private Object titleText, contentText, leftText, rightText;
    private boolean force, touchOutside;
    private OnClickListener leftClickListener, rightClickListener;

    // =============
    // private Dialog dialog; // 所拥有的对话框
    // private AlertDialog.Builder builder = new
    // AlertDialog.Builder(getActivity());
    private AlertDialog alertDialog;// 所拥有的对话框
    private Map<Object, Button> btns = new HashMap<Object, Button>();

    public MyDialogByFragment() {

    }

    /**
     * 显示一个对话框
     *
     * @param titleText         标题
     * @param contentText       内容
     * @param leftText          左边的按钮
     * @param rightText         右边的按钮
     * @param force             是否要给右边的按钮设置监听器
     * @param leftClickListener View.OnClickListener 给左边的按钮设置监听器
     * @param leftClickListener View.OnClickListener 给右边的按钮设置监听器
     */
    public static  MyDialogByFragment newInstance(Context context, Object titleText, Object contentText, Object leftText, Object rightText,
                                          boolean force, OnClickListener leftClickListener, OnClickListener rightClickListener,
                                          boolean touchOutside, Bundle args) {
        MyDialogByFragment fragment = new MyDialogByFragment();
        fragment.titleText = titleText;
        fragment.contentText = contentText;
        fragment.leftText = leftText;
        fragment.rightText = rightText;
        fragment.force = force;
        fragment.leftClickListener = leftClickListener;
        fragment.rightClickListener = rightClickListener;
        // this.context = this.getActivity();
        fragment.context = context;
        fragment.touchOutside = touchOutside;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // if (null != contentText && contentText instanceof View) {
        // 去掉默认的标题
        // getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // return (View) contentText;
        // } else {
        return super.onCreateView(inflater, container, savedInstanceState);
        // }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
//			dialogView.findViewById(R.id.ll_btn_bottom).setVisibility(View.GONE);
//			View btnsLayout = (View) left.getParent();
            // 移除按钮布局
//			btnsLayout.setVisibility(View.GONE);
            dialogView.removeView(btnsLayout);
        } else {
            left.setText(parseParam(context, leftText));
            // 判断上下文是否为监听器
            if (null != leftClickListener) {
                left.setOnClickListener(leftClickListener);
            } else {
                if (context instanceof View.OnClickListener) {
                    left.setOnClickListener((OnClickListener) context);
                }
            }
            // myDialog.btns.put(leftText, left); // 放按钮
            btns.put(leftText, left); // 放按钮

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
                    if (rightClickListener instanceof View.OnClickListener) {
                        // right.setOnClickListener((OnClickListener) context);
                        right.setOnClickListener(rightClickListener);
                    }
                } else {
                    right.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            // dismiss(myDialog); // 默认关闭对话框
                            dismiss();
                        }
                    });
                }
                btns.put(rightText, right); // 放按钮
            }
        }

        // Dialog dialog = new AlertDialog.Builder(context).create();
        // Dialog dialog = new AlertDialog.Builder(context,
        // R.style.Dialog).create();
        // dialog.setCanceledOnTouchOutside(true);
        // dialog.show(); // 先显示对话框
        // // 这里得到的window其实是中间的内容
        // dialog.getWindow().setContentView(dialogView); // 设置对话框的内容
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(touchOutside);
        alertDialog.show(); // 先显示对话框
        // 这里得到的window其实是中间的内容
        alertDialog.getWindow().setContentView(dialogView); // 设置对话框的内容
        this.alertDialog = alertDialog;
        return alertDialog;
        // return onCreateDialog(savedInstanceState);
    }

    /**
     *
     * @param context
     * @param titleText
     * @param contentText
     * @param leftText
     * @param rightText
     * @param force
     * @param clickListener
     * @return 2015年12月30日 qs
     */
    // private static MyDialogByFragment showDialog(Context context, Object
    // titleText, Object contentText, Object leftText,
    // Object rightText, boolean force, OnClickListener clickListener) {
    // final MyDialogByFragment myDialog = new MyDialogByFragment();
    // // 实例化布局文件
    // LinearLayout dialogView = (LinearLayout)
    // LayoutInflater.from(context).inflate(R.layout.dialog, null);
    // // 标题
    // TextView title = (TextView) dialogView.findViewById(R.id.dialog_title);
    // title.setText(parseParam(context, titleText));
    // // 内容
    // TextView cont = (TextView) dialogView.findViewById(R.id.dialog_content);
    // if (contentText instanceof View) { // 需要移除中间的TextView
    // LinearLayout contLayout = (LinearLayout) cont.getParent();
    // contLayout.removeView(cont); // 移除TextView
    // contLayout.addView((View) contentText); // 添加新的View
    // } else { // 设置中间内容的文字
    // cont.setText(parseParam(context, contentText));
    // }
    //
    // // 左边的按钮
    // Button left = (Button) dialogView.findViewById(R.id.dialog_left);
    // if (leftText == null) { // 说明不需要按钮
    // // 得到按钮所在的布局
    // View btnsLayout = (View) left.getParent().getParent();
    // // 移除按钮布局
    // dialogView.removeView(btnsLayout);
    // } else {
    // left.setText(parseParam(context, leftText));
    // // 判断上下文是否为监听器
    // if (null != clickListener) {
    // left.setOnClickListener(clickListener);
    // } else {
    // if (context instanceof View.OnClickListener) {
    // left.setOnClickListener((OnClickListener) context);
    // }
    // }
    // // myDialog.btns.put(leftText, left); // 放按钮
    // myDialog.btns.put(leftText, left); // 放按钮
    //
    // // 右边的按钮
    // Button right = (Button) dialogView.findViewById(R.id.dialog_right);
    // if (rightText == null) { // 不需要右边的按钮
    // LinearLayout rightParent = (LinearLayout) right.getParent(); //
    // 得到右边按钮的父控件
    // LinearLayout bottomLayout = (LinearLayout) rightParent.getParent(); //
    // 得到底部最大的layout
    // bottomLayout.removeView(rightParent); // 删除右边的布局
    // } else {
    // right.setText(parseParam(context, rightText));
    //
    // if (force) { // 如果要给右边的按钮强制设定监听器
    // // 判断上下文是否为监听器
    // if (context instanceof View.OnClickListener) {
    // right.setOnClickListener((OnClickListener) context);
    // }
    // } else {
    // right.setOnClickListener(new OnClickListener() {
    // public void onClick(View v) {
    // // dismiss(myDialog); // 默认关闭对话框
    // myDialog.dismiss();
    // }
    // });
    // }
    // myDialog.btns.put(rightText, right); // 放按钮
    // }
    // }
    //
    // // Dialog dialog = new AlertDialog.Builder(context).create();
    // // Dialog dialog = new AlertDialog.Builder(context,
    // // R.style.Dialog).create();
    // // dialog.setCanceledOnTouchOutside(true);
    // // dialog.show(); // 先显示对话框
    // // // 这里得到的window其实是中间的内容
    // // dialog.getWindow().setContentView(dialogView); // 设置对话框的内容
    // AlertDialog.Builder builder = new AlertDialog.Builder(context,
    // R.style.Dialog);
    // AlertDialog alertDialog = builder.create();
    // alertDialog.setCanceledOnTouchOutside(true);
    // alertDialog.show(); // 先显示对话框
    // // 这里得到的window其实是中间的内容
    // alertDialog.getWindow().setContentView(dialogView); // 设置对话框的内容
    // myDialog.dialog = alertDialog;
    // return myDialog;
    // }

    /**
     * 显示一个对话框
     *
     * @param titleText   标题
     * @param contentText 内容
     * @param leftText    左边的按钮
     * @param rightText   右边的按钮
     */
    public static MyDialogByFragment newInstance(Context context, Object titleText, Object contentText, Object leftText,
                                          Object rightText, Bundle args) {
        MyDialogByFragment myDialogByFragment = new MyDialogByFragment();
        myDialogByFragment.titleText = titleText;
        myDialogByFragment.contentText = contentText;
        myDialogByFragment.leftText = leftText;
        myDialogByFragment.rightText = rightText;
        myDialogByFragment.context = context;
        return myDialogByFragment;
    }

    /**
     * 只显示一个按钮
     */
    public static MyDialogByFragment newInstance(Context context, Object titleText, Object contentText, Object leftText, OnClickListener leftClickListener, boolean touchOutside, Bundle args) {
        MyDialogByFragment myDialogByFragment = new MyDialogByFragment();
        myDialogByFragment.titleText = titleText;
        myDialogByFragment.contentText = contentText;
        myDialogByFragment.leftText = leftText;
        myDialogByFragment.context = context;
        myDialogByFragment.leftClickListener = leftClickListener;
        myDialogByFragment.touchOutside = touchOutside;
        return myDialogByFragment;
    }

    /**
     * 不要按钮
     */
    public static MyDialogByFragment newInstance(Context context, Object titleText, Object contentText, boolean touchOutside, Bundle args) {
        MyDialogByFragment myDialogByFragment = new MyDialogByFragment();
        myDialogByFragment.titleText = titleText;
        myDialogByFragment.contentText = contentText;
        myDialogByFragment.context = context;
        myDialogByFragment.touchOutside = touchOutside;
        return myDialogByFragment;
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
    public static Button getBtn(MyDialogByFragment myDialog, Object key) {
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
    public static void dismiss(MyDialogByFragment myDialog) {
        // 如果对话框不为null 而且正在显示
        if (myDialog != null && myDialog.alertDialog != null && myDialog.alertDialog.isShowing()) {
            myDialog.alertDialog.dismiss();
        }
    }

    public Object getContentText() {
        return contentText;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public Map<Object, Button> getBtns() {
        return btns;
    }

    public void setBtns(Map<Object, Button> btns) {
        this.btns = btns;
    }
}
