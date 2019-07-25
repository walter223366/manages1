package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：人物管理bean （biz_charactor_base_info）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class CharactorBaseInfo implements Serializable {

    private static final long serialVersionUID = -4309582961171562243L;
    private String id;//人物id
    private String user_id;//账号id
    private String school_id;//门派id
    private String member_id;//会员id
    private String nickname;//角色昵称
    private int sex;//性别
    private int level;//等级
    private int attitude;//态度
    private int character;//性格，-1000到1000
    private int popularity;//声望，0-MAX
    private long coin;//铜钱，0-MAX
    private long gold;//元宝，0-MAX
    private long experience;//经验值
    private long school_contribution;//门派贡献值
    private String pic;
    private int enable;//启用状态，0或null未启用，1启用
    private int cancellation;//注销状态，0或null正常，1注销
}
