package com.rush.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by 17512 on 2018/6/7.
 */
public class MathUtil {

    /**
     * 计算v1/v2的百分比, 返回百分比字符串
     * @param v1
     * @param v2
     * @return
     */
    public static String getPercent (Integer v1, Integer v2) {
        if (v1 == null || v2 == null
                || v2 == 0) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        String result = b1.divide(b2, 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
        result = result + "%";
        return result;
    }

    /**
     * 两个Float相加结果, null转为defaultValue
     * @return
     */
    public static Float addFloat (Float num1, Float num2, Float defaultValue) {
        num1 = num1 == null ? defaultValue : num1;
        num2 = num2 == null ? defaultValue : num2;
        return num1 + num2;
    }
}
