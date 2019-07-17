package com.ral.manages.entity.manage;

import lombok.Data;

/**
 * 门派表 dict_school
 */
@Data
public class School {

    private String school_id;//门派id
    private String name;//名称
    private String info;//说明
    private int influence;//影响力
    private int del_status;//删除状态

}
