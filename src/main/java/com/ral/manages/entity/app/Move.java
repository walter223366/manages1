package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：招式管理bean （dict_zhaoshi）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Move implements Serializable {

    private static final long serialVersionUID = -6551161807605307530L;
    private String zhaoshi_id;
    private String kongfu_id;//功夫id
    private String name;//招式名称
    private String MP_cost;//内力花费，格式：1,3,3,0,0。黄，金，绿，蓝，紫。
    private String info;//招式说明
    private String zhaoshi_effect;//招式效果，格式：12,14,20。效果id被逗号隔开。
    private long zhaoshi_experience_cost;//招式所需武学经验
    private int deleteStatus;//删除状态，0或null正常，1已删除

}
