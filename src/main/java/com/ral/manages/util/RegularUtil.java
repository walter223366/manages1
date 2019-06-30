package com.ral.manages.util;

import org.apache.log4j.Logger;
import java.io.Serializable;
import java.util.regex.Pattern;

public class RegularUtil implements Serializable {

    private static final long serialVersionUID = 5911455242813608853L;

    private static final Logger LOG = Logger.getLogger(RegularUtil.class);


    /**电话号码字符串匹配*/
    private static Pattern phoneNumberPattern = Pattern.compile("^(\\d{3,4}-)?\\d{7,8}$");

    /**email字符串匹配*/
    private static Pattern emailPattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

}
