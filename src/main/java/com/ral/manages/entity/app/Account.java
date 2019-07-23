package com.ral.manages.entity.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：账号管理bean （biz_user）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3402818979420275671L;
    private String id;
    private String account;//账号
    @JsonIgnore
    private String password;//密码
    private int source;//来源(1)
    private long tellphone;//电话
    private String lrrq;//创建日期
    private int cancellation;//注销标志(0或null正常，1注销)

}
