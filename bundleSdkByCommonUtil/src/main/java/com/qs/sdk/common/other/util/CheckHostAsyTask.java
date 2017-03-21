package com.qs.sdk.common.other.util;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.qs.sdk.common.inter.IUICallBackInterface;
import com.qs.sdk.dialog.ui.PbDialog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 异步检测域名
 *
 * @author Administrator 2014年1月2日上午11:51:38
 */
public class CheckHostAsyTask extends AsyncTask<Void, Integer, Boolean> {
    private int timeOut = 8000; // 超时应该在3钞以上
    private String hostAdd;
    private String pdMsg;
    private boolean pdShow;
    private PbDialog popupDialog;
    private Context context;
    private IUICallBackInterface iuiCallBackInterface;
    private int caseKey;

    /**
     * @param context
     * @param hostAdd             域名
     * @param pdShow              是否显示提示框
     * @param pdMsg
     * @param serverInterfaceName // 接口名称
     */
    public CheckHostAsyTask(Context context, IUICallBackInterface iuiCallBackInterface, int caseKey, String hostAdd,
                            boolean pdShow, String pdMsg) {
        this.context = context;
        this.hostAdd = hostAdd;
        this.pdMsg = pdMsg;
        this.pdShow = pdShow;
        this.iuiCallBackInterface = iuiCallBackInterface;
        this.caseKey = caseKey;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Log.i("CheckHostAsyTask", "check domain is " + hostAdd);
            InetAddress address;
            address = InetAddress.getByName(hostAdd);
            return address.isReachable(8000);
            // URL url = new URL(ConstantFieldUtil.S_H_HTTP_PREFIX + hostAdd
            // + serverInterfaceName);
            // if (SDKSettings.DEBUG) {
            // Log.w(SDKSettings.LOG_TAG, "检查的域名为 "
            // + ConstantFieldUtil.S_H_HTTP_PREFIX + hostAdd
            // + serverInterfaceName);
            // }
            // // open a connection to that source
            // HttpURLConnection urlConnect = (HttpURLConnection) url
            // .openConnection();
            //
            // // trying to retrieve data from the source. If there
            // // is no connection, this line will fail
            // urlConnect.setConnectTimeout(timeOut);
            // Map<String, List<String>> heads = urlConnect.getHeaderFields();
            // // Object objData = urlConnect.getContent();
            // if (null == heads) {
            // return false;
            // }
            // return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPreExecute() {
        if (pdShow) {
            // popupDialog = new Dialog(context,
            // android.R.style.Theme_Translucent_NoTitleBar);
            // popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // popupDialog.setContentView(RResouceByWebUtil
            // .getIdFromR_LayoutClass(R.layout.sh_loading_layout));
            // TextView txtMsg = (TextView) popupDialog
            // .findViewById(RResouceByWebUtil
            // .getIdFromR_IdClass(R.id.sh_left_textView));
            // txtMsg.setText(pdMsg);
            popupDialog = new PbDialog(context);
            popupDialog.setContentPrompt(pdMsg);
            popupDialog.show();
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (pdShow) {
            pdShow = false;
            popupDialog.dismiss();
        }
        if (null != iuiCallBackInterface) {
            iuiCallBackInterface.uiCallBack(result, caseKey);
        }
    }

    public boolean isPdShow() {
        return pdShow;
    }

    public void setPdShow(boolean pdShow) {
        this.pdShow = pdShow;
    }

    public Dialog getPopupDialog() {
        return popupDialog;
    }
}
