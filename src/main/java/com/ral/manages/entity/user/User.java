package com.ral.manages.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 214675296997721608L;
    private String id;
    private String username;//用户名
    private String password;//密码
}
