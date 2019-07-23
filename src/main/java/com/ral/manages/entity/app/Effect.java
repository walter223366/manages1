package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：效果管理bean （dict_effect）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Effect implements Serializable {

    private static final long serialVersionUID = -127115926109132218L;
    private String effect_id;//效果主键
    private String info;//效果说明
    private int target;//效果执行的目标：0自己1对方 默认是0
    private long Aggressivity;//攻击力
    private long Defense;//防御力
    private long hp;//HP影响：加血，减血
    private String mp_yellow;//黄球影响，如80,80,-60（以逗号隔开，减号代表减少。可添加多个。这里有机会得到或减少80%几率80%几率60%几率，计算时分3次计算）
    private String mp_gold;//金球影响
    private String mp_green;//绿球影响
    private String mp_blue;//蓝球影响
    private String mp_purple;//紫球影响
    private long injury;//外伤
    private long internal_injury;//内伤
    private long poisoning;//毒伤
    private long stun;//击晕率%，填数值80（80%）
    private long dodge;//闪避%，填数值30（30%）
    private long hit_rate;//命中率
    private long crit_rate;//暴击率
    private long burst;//暴击力
    private long suck_HP;//吸血，输入值30（30%），吸取敌方损失hp*30%血量
    private String suck_mp_yellow;//黄球影响，如80,80,-60（以逗号隔开，减号代表减少。可添加多个。这里有机会得到或减少80%几率80%几率60%几率，计算时分3次计算）
    private String suck_mp_gold;//金球影响
    private String suck_mp_green;//绿球影响
    private String suck_mp_blue;//蓝球影响
    private String suck_mp_purple;//紫球影响
    private int cancellation;//注销标志(0或null正常，1注销)

}
