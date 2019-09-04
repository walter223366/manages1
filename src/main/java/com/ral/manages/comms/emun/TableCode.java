package com.ral.manages.comms.emun;

/**
 *   状态表
 *
 *   @author Double
 *   @since  2019-07-18
 */
public enum TableCode {

        LANDED("登陆",0),
        REGISTERED("注册",1),

        DELETE_ZERO("正常",0),
        DELETE_ONE("已删除",1),

        SOURCE_ZERO("微信",0),
        SOURCE_ONE("其他",1),

        /*注销状态*/
        CANCELLATION_ZERO("正常",0),
        CANCELLATION_ONE("注销",1),

        /*功夫分类*/
        TYPE_ZERO("内功",0),
        TYPE_ONE("轻功",1),
        TYPE_TWO("拳脚",2),
        TYPE_THREE("剑术",3),
        TYPE_FOUR("刀斧",4),
        TYPE_FIVES("枪棍",5),
        TYPE_SIX("暗器",6),
        TYPE_SEVEN("异功",7),

        /*启动状态*/
        ENABLE_ZERO("未启动",0),
        ENABLE_ONE("已启动",1),

        /*执行目标*/
        TARGET_ZERO("自己",0),
        TARGET_ONE("对方",1);

        private String name;
        private int code;
        private TableCode(String name,int code){
            this.name = name;
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public int getCode() {
            return code;
        }
}
