package com.ral.manages.entity.user;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：权限bean （sys_role）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -3674406756249684684L;
    private String id;
    private String name;
    private String mothed;

}
