package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：人物造诣bean （biz_charactor_attainments）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class CharactorAttainments implements Serializable {

    private static final long serialVersionUID = -8372738545742116835L;
    private String id;
    private String charactor_id;//人物id
    private int melee_status;//拳脚造诣
    private int sword_status;//剑法造诣
    private int axe_status;//刀斧造诣
    private int javelin_status;//枪棍造诣
    private int hidden_weapons_status;//暗器造诣
    private int sorcery_status;//异功造诣
    private int dodge_skill_status;
    private int chakra_status;
    private String lrrq;//保存日期

}
