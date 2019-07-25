package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;


/**
 *   <p>功能描述：人物体质bean （biz_charactor_battle_ext）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class CharactorBattleExt implements Serializable {

    private static final long serialVersionUID = 5279301686590171236L;
    private String id;
    private int physique;//体质
    private int force;//臂力
    private int muscles;//筋骨
    private int chakra;//内修
    private int sensitivity;//灵活
    private int willpower;//意志
    private int knowledge;//学识
    private int lucky;//运气
    private String charactor_id;//关联t_charactor_base_info的id

}
