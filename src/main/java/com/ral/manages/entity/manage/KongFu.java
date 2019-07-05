package com.ral.manages.entity.manage;

import lombok.Data;

/**
 * 功夫表 dict_kongfu
 */
@Data
public class KongFu {

    private String kongfu_id;//id
    private String name;//名称
    private String info;//说明
    private int type;//武功分类：0内功1轻功2拳脚3剑术4刀斧5枪棍6暗器7异功
    private long experience_limit;//可学经验上限
    private String kongfu_attainments;//造诣，格式：[{"10":"1"},{"20":"2"},...]。说明：前面的值是武学经验，后面的值是提升的造诣值（根据武功分类提升对应的造诣）
    private int enable;//启用状态，0或null未启用，1启用
    private int cancellation;//注销状态，0或null正常，1注销
}
