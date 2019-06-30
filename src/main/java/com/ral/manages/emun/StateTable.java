package com.ral.manages.emun;

/**
 * 状态表
 * @author Double
 */
public interface StateTable {

    /*返回值状态*/
    public final static String RETURNCODE_ONE = "1"; //成功
    public final static String RETURNCODE_ZERO = "0"; //失败

    /* biz_game_user */
    public final static int USER_SOURCE = 1; // 来源->

    public final static int CANCELLATION_ZERO = 0; //注销状态->正常
    public final static int CANCELLATION_ONE = 1; //注销状态->注销


}
