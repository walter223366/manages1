package com.ral.manages.entity.manage;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：门派管理bean （dict_school）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class School implements Serializable {

    private static final long serialVersionUID = 4642402463029147327L;
    private String school_id;//门派id
    private String name;//名称
    private String info;//说明
    private int influence;//影响力（默认是0）
    private int del_status;//删除状态（0：未删除（默认） 1：已删除）

}
