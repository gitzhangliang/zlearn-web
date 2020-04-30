package com.tzxx.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author tzxx
 */
public class CalculateUtil {
    private CalculateUtil(){}

    public static BigDecimal add(List<BigDecimal> numbs){
        if(numbs == null || numbs.isEmpty()){
            return null;
        }
        BigDecimal count = new BigDecimal("0.00");
        for (BigDecimal num : numbs){
            if(num == null){
                continue;
            }
            count = count.add(num);
        }
        return count;
    }

    public static BigDecimal add(BigDecimal... numbs){
        if (numbs == null || numbs.length == 0){
            return null;
        }
        return add(Arrays.asList(numbs));
    }

    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2,int newScale, RoundingMode roundingMode){
        return num1.subtract(num2).setScale(newScale,roundingMode);
    }

    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2){
        return num1.subtract(num2);
    }

    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2,int newScale, RoundingMode roundingMode){
        return num1.multiply(num2).setScale(newScale,roundingMode);
    }

    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2){
        return num1.multiply(num2).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal num1, BigDecimal num2,int newScale, RoundingMode roundingMode){
        return num1.divide(num2,newScale,roundingMode);
    }

    public static BigDecimal divide(BigDecimal num1, BigDecimal num2){
        return num1.divide(num2, 2, BigDecimal.ROUND_HALF_UP);
    }
}
