package com.ral.manages.entity.user;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5254092103419421234L;
    private String language;
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
}
