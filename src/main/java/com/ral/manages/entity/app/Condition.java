package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：物品管理bean （dict_condition）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Condition implements Serializable {

    private static final long serialVersionUID = 5541106448331378834L;
    private String condition_id;//物品id
    private String name;//物品名称
    private String info;//物品说明
    private String condition_buff;//状态buff，格式：10,20,33。buff_id被逗号隔开
    private int deleteStatus;//删除状态，0或null正常，1已删除

}
