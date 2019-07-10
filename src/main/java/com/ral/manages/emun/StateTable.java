package com.ral.manages.emun;


/**
 * 状态表
 * @author Double
 */
public enum StateTable {

    /*返回值状态*/
    RETURNCODE_ONE("成功","1"),
    RETURNCODE_ZERO("失败","0"),

    /*来源*/
    USER_SOURCE("来源",1),

    /*注销状态*/
    CANCELLATION_ZERO("正常",0),
    CANCELLATION_ONE("注销",1),

    /*功夫分类*/
    KONGFU_TYPE_ZERO("内功",0),
    KONGFU_TYPE_ONE("轻功",1),
    KONGFU_TYPE_TWO("拳脚",2),
    KONGFU_TYPE_THREE("剑术",3),
    KONGFU_TYPE_FOUR("刀斧",4),
    KONGFU_TYPE_FIVES("枪棍",5),
    KONGFU_TYPE_SIX("暗器",6),
    KONGFU_TYPE_SEVEN("异功",7),

    /*启动状态*/
    KONGFU_ENABLE_ZERO("未启动",0),
    KONGFU_ENABLE_ONE("启动",1);


    private String name;
    private Object code;
    private StateTable(String name,Object code){
        this.name = name;
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public Object getCode() {
        return code;
    }
}
