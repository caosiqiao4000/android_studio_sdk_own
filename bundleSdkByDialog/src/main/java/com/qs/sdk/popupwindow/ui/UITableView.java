package com.qs.sdk.popupwindow.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

import java.util.ArrayList;
import java.util.List;

public class UITableView extends LinearLayout {

    private LayoutInflater mInflater;
    private LinearLayout mListContainer;
    private List<ActionItem> mItemList;
    private ClickListener mClickListener;

    public UITableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mItemList = new ArrayList<ActionItem>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mMainContainer = (LinearLayout) mInflater.inflate(R.layout.list_container, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        addView(mMainContainer, params);
        mListContainer = (LinearLayout) mMainContainer.findViewById(R.id.buttonsContainer);
    }

    public void setActionList(List<ActionItem> actionItems) {
        this.mItemList = actionItems;
    }

    public void clearSelected() {
        for (ActionItem actionItem : this.mItemList) {
            actionItem.setSelected(false);
        }
        commit();
    }

    public void setSelected(int i) {
        this.mItemList.get(i).setSelected(true);
        commit();
    }

    /**
     * @param title
     * @param summary
     */
    public void addActionItem(int actionId, String title) {
        mItemList.add(new ActionItem(actionId, title));
    }

    /**
     * @param title
     * @param summary
     */
    public void addActionItem(int actionId, String title, Drawable drawable) {
        mItemList.add(new ActionItem(actionId, title, drawable));
    }

    /**
     * @param item
     */
    public void addActionItem(ActionItem item) {
        mItemList.add(item);
    }

    public void commit() {
        mListContainer.removeAllViews();
        int mIndexController = 0;

        if (mItemList.size() > 1) {
            // when the list has more than one item
            for (ActionItem obj : mItemList) {
                View tempItemView = mInflater.inflate(R.layout.app_item, null);
                ;
                if (mIndexController == 0) {
                    tempItemView.setBackgroundResource(R.drawable.item_bg_top_selector);
                } else if (mIndexController == mItemList.size() - 1) {
                    tempItemView.setBackgroundResource(R.drawable.item_bg_bottom_selector);
                } else {
                    tempItemView.setBackgroundResource(R.drawable.item_bg_center_selector);
                }
                if (isShowCheckBox) {
                    tempItemView.findViewById(R.id.checkbox_item).setVisibility(View.VISIBLE);
                }
                setupItem(tempItemView, obj);
                mListContainer.addView(tempItemView);
                mIndexController++;
            }
        } else if (mItemList.size() == 1) {
            // when the list has only one item
            View tempItemView = mInflater.inflate(R.layout.app_item, null);
            tempItemView.setBackgroundResource(R.drawable.item_bg_group_alone);
            ActionItem obj = mItemList.get(0);
            setupItem(tempItemView, obj);
            mListContainer.addView(tempItemView);
        }
    }

    private void setupItem(View view, ActionItem item) {
        setupActionItem(view, item);
    }

    /**
     * @param view
     * @param item
     * @param index
     */
    private void setupActionItem(View view, ActionItem item) {
        if (item.getIcon() != null) {
            ((ImageView) view.findViewById(R.id.app_img)).setBackgroundDrawable(item.getIcon());
        }
        ((TextView) view.findViewById(R.id.app_label)).setText(item.getTitle());
        view.setTag(item.getActionId());
        final CheckBox cb_item = (CheckBox) view.findViewById(R.id.checkbox_item);
        // 如果选择框是可显示状态，就设置是否被选中
        if (cb_item.getVisibility() == View.VISIBLE) {
            cb_item.setChecked(item.isSelected());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_item.setChecked(!cb_item.isChecked());
                if (mClickListener != null)
                    mClickListener.onClick((Integer) view.getTag());
            }
        });
    }

    public interface ClickListener {
        public void onClick(int actionid);
    }

    /**
     * @return
     */
    public int getCount() {
        return mItemList.size();
    }

    /**
     *
     */
    public void clear() {
        mItemList.clear();
        mListContainer.removeAllViews();
    }

    /**
     * @param listener
     */
    public void setClickListener(ClickListener listener) {
        this.mClickListener = listener;
    }

    /**
     *
     */
    public void removeClickListener() {
        this.mClickListener = null;
    }

    // 是否显示选择框
    private boolean isShowCheckBox = false;

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean isShowCheckBox) {
        this.isShowCheckBox = isShowCheckBox;
    }

}
