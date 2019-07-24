package com.ral.manages.util;

public class FieldHandleUtil {

    /*处理查询时内力花费*/
    public static String segmentation_cost(int index,String cost){
        String[] cost_value = cost.split(",");
        int length = cost_value.length;
        if(index <= length){
            return cost_value[index];
        }
        return null;
    }
}
