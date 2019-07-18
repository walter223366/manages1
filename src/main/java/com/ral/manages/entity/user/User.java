package com.ral.manages.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 *   <p>功能描述：用户管理bean （sys_user）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 214675296997721608L;
    private String id;
    private String username;//用户名
    @JsonIgnore
    private String password;//密码
    private String mail;//邮箱
    private long phone;//手机号码
    private int cancellation;//注销状态（0:正常 1:已注销）

}
