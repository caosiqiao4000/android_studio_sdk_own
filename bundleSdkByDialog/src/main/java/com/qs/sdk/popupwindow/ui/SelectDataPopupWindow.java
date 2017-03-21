package com.qs.sdk.popupwindow.ui;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.qs.sdk.dialog.R;

/**
 * 选择日期对话框
 */

public class SelectDataPopupWindow extends PopupWindow {

    private Button sq_data_btn_sure, sq_data_btn_cancle;
    private View mMenuView;
    private DatePicker datePicker;

    public SelectDataPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.data_select_popup, null);
//		btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
//		btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        datePicker = (DatePicker) mMenuView.findViewById(R.id.sq_data_dp);
        sq_data_btn_sure = (Button) mMenuView.findViewById(R.id.sq_data_btn_sure);
        sq_data_btn_cancle = (Button) mMenuView.findViewById(R.id.sq_data_btn_cancle);
        // 取消按钮
        sq_data_btn_cancle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        sq_data_btn_sure.setOnClickListener(itemsOnClick);
//		 设置按钮监听
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(anim.fade_in);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.setOutsideTouchable(false);
//		mMenuView.setOnTouchListener(new OnTouchListener() {
//
//			public boolean onTouch(View v, MotionEvent event) {
//
//				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//				int y = (int) event.getY();
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (y < height) {
//						dismiss();
//					}
//				}
//				return true;
//			}
//		});
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}