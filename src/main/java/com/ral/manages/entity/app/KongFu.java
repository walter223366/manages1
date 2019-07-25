package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：功夫管理bean （dict_kongfu）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class KongFu implements Serializable {

    private static final long serialVersionUID = -4317823737594173611L;
    private String kongfu_id;//id
    private String name;//名称
    private String info;//说明
    private int type;//武功分类：0内功1轻功2拳脚3剑术4刀斧5枪棍6暗器7异功
    private long experience_limit;//可学经验上限(默认100)
    private String kongfu_attainments;//造诣，格式：[{"10":"1"},...]。说明：前面的值是武学经验，后面的值是提升的造诣值（根据武功分类提升对应的造诣）
    private int enable;//启用状态，0或null未启用，1启用
    private int cancellation;////删除状态，0或null正常，1已删除
    private String kongfu_zhaoshi;//武学招式，格式：12,14,20。效果id被逗号隔开。
}
