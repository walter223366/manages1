package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：人物学识bean （biz_charactor_potential）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class CharactorPotential implements Serializable {

    private static final long serialVersionUID = -7362396749485866523L;
    private String id;
    private int wisdom1;//智慧-见多识广
    private int wisdom2;//智慧-嗜练烈毒
    private int wisdom3;//智慧-遍尝百草
    private int healthy1;//强壮-生命旺盛
    private int healthy2;//强壮-抗晕体质
    private int healthy3;//强壮-再生体质
    private int mellow1;//醇厚-浑厚力道
    private int mellow2;//醇厚-极速御气
    private int mellow3;//醇厚-凝聚真气
    private int mellow4;//醇厚-周转御气
    private int mellow5;//醇厚-逆行真气
    private int fineness1;//技巧-行云流水
    private int fineness2;//技巧-模糊身形
    private int burst1;//爆发-致命打击
    private int burst2;//爆发-招招毙命
    private int sharp1;//犀利-重创攻击
    private int sharp2;//犀利-撕裂攻击
    private int tenacity1;//坚韧-坚强意志
    private int tenacity2;//坚韧-钢筋铁骨
    private int tenacity3;//坚韧-凝气守神
    private String charactor_id;//关联t_charactor_base_info的id

}
