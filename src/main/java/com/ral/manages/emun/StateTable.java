package com.ral.manages.emun;

/**
 * 状态表
 * @author Double
 */
public interface StateTable {

    /*返回值状态*/
    public final static String RETURNCODE_ONE = "1"; //成功
    public final static String RETURNCODE_ZERO = "0"; //失败

    /*来源*/
    public final static int USER_SOURCE = 1;//暂时为

    /*注销状态*/
    public final static int CANCELLATION_ZERO = 0; //正常
    public final static int CANCELLATION_ONE = 1; //注销

    /*功夫分类*/
    public final static int KONGFU_TYPE_ZERO = 0;//内功
    public final static int KONGFU_TYPE_ONE = 1;//轻功
    public final static int KONGFU_TYPE_TWO = 2;//拳脚
    public final static int KONGFU_TYPE_THREE = 3;//剑术
    public final static int KONGFU_TYPE_FOUR = 4;//刀斧
    public final static int KONGFU_TYPE_FIVES = 5;//枪棍
    public final static int KONGFU_TYPE_SIX = 6;//暗器
    public final static int KONGFU_TYPE_SEVEN = 7;//异功

    /*启动状态*/
    public final static int KONGFU_ENABLE_ZERO = 0;//未启动
    public final static int KONGFU_ENABLE_ONE = 1;//启动



}
