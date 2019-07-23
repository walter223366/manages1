package com.ral.manages.commom.emun;

import lombok.Data;

/**
 *   <p>功能描述: 状态表</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
public interface StateTable {

    /*账号管理状态表*/
    enum User{
        /*来源*/
        SOURCE_ZERO("微信",0),
        SOURCE_ONE("其他",1),

        /*注销状态*/
        CANCELLATION_ZERO("正常",0),
        CANCELLATION_ONE("注销",1);

        private String name;
        private int code;
        private User(String name,int code){
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

    /*门派管理状态表*/
    enum School{
        /*注销状态*/
        CANCELLATION_ZERO("正常",0),
        CANCELLATION_ONE("注销",1);

        private String name;
        private int code;
        private School(String name,int code){
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

    /*武学管理状态表*/
    enum KongFu{
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

        /*注销状态*/
        CANCELLATION_ZERO("正常",0),
        CANCELLATION_ONE("注销",1);

        private String name;
        private int code;
        private KongFu(String name,int code){
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

    /*效果管理状态表*/
    enum Effect{
        /*执行目标*/
        TARGET_ZERO("自己",0),
        TARGET_ONE("对方",1),

        /*注销状态*/
        CANCELLATION_ZERO("正常",0),
        CANCELLATION_ONE("注销",1);

        private String name;
        private int code;
        private Effect(String name,int code){
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


}
