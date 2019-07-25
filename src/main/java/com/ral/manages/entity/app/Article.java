package com.ral.manages.entity.app;

import lombok.Data;
import java.io.Serializable;

/**
 *   <p>功能描述：物品管理bean （dict_article）</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = -5693159093066417810L;
    private String article_id;//物品id
    private String img;//物品图标
    private String name;//物品名称
    private int cancellation;//删除状态，0或null正常，1已删除
}
