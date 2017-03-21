package com.qs.sdk.popupwindow.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

import java.util.Arrays;
import java.util.List;

public class PopMenuDialog extends Dialog {

    private TextView txtTitle;
    private UITableView tableView;
    private UITableView.ClickListener listener;

    public PopMenuDialog(Context context) {
        super(context, R.style.sh_MyDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO Auto-generated constructor stub
        init();
    }

    public PopMenuDialog(Context context, int attrs) {
        super(context, attrs);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        setContentView(R.layout.txt_dialog);
        // 系统中关机对话框就是这个属性
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        window.setAttributes(lp);
        // window.addFlags(WindowManager.LayoutParams.FLAGS_CHANGED);
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setCanceledOnTouchOutside(true);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        tableView = (UITableView) findViewById(R.id.tableView);
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public void setTitle(int resid) {
        txtTitle.setText(resid);
    }

    public void setMenus(List<ActionItem> actionItems) {
        tableView.setClickListener(listener);
        tableView.setActionList(actionItems);
        tableView.commit();
    }

    public void setMenus(ActionItem[] actionItems) {
        List<ActionItem> actionItemslList = Arrays.asList(actionItems);
        setMenus(actionItemslList);
    }

    public void setMenuChoose(List<ActionItem> actionItems, int choose) {
        // TODO Auto-generated method stub
        actionItems.get(choose).setSelected(true);
        tableView.setShowCheckBox(true);
        setMenus(actionItems);
    }

    public void setChoosed(int choose) {
        // TODO Auto-generated method stub
        tableView.setSelected(choose);
    }

    public void clearChoosed() {
        tableView.clearSelected();
    }

    /**
     * void TODO
     *
     * @param actionItems
     * @param choose      是否显示CheckBox选择框
     */
    public void setMenuChoose(ActionItem[] actionItems, boolean choose) {
        // TODO Auto-generated method stub
        List<ActionItem> actionItemslList = Arrays.asList(actionItems);
        tableView.setShowCheckBox(choose);
        setMenus(actionItemslList);
    }

    public void isShowCheckbox(boolean isShow) {
        tableView.setShowCheckBox(isShow);
    }

    public void setListener(UITableView.ClickListener listener) {
        this.listener = listener;
    }

}
