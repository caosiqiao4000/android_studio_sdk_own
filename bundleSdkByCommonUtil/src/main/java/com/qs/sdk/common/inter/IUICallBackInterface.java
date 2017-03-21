package com.qs.sdk.common.inter;

import java.io.Serializable;

/**
 * 请求服务器得到数据的回调接口
 *
 * @author boge
 * @time 2013-3-26
 */
public interface IUICallBackInterface extends Serializable {
    public void uiCallBack(Object supportResponse, int caseKey);
}
