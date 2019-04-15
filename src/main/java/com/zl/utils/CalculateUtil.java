package com.zl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculateUtil {


    public static double add(List<Double> numbs){
        if(numbs == null || numbs.size() == 0){
            return 0.00d;
        }
        BigDecimal count = new BigDecimal("0.00");
        for (Double num : numbs){
            if(num == null){
                continue;
            }
            count = count.add(new BigDecimal(Double.toString(num)));
        }
        return count.doubleValue();
    }
    public static double add(Double... numbs){
        if (numbs == null || numbs.length == 0){
            return 0.00d;
        }
        return add(Arrays.asList(numbs));
    }

    public static double addForString(List<String> numbs){
        if(numbs == null || numbs.size() == 0){
            return 0.00d;
        }
        BigDecimal count = new BigDecimal("0.00");
        for (String num : numbs){
            if(num == null || "".equals(num)){
                continue;
            }
            count = count.add(new BigDecimal(num));
        }
        return count.doubleValue();
    }

    public static double addForString(String... numbs){
        if (numbs == null || numbs.length == 0){
            return 0.00d;
        }
        return addForString(Arrays.asList(numbs));
    }
    public static double subtract(double num1, double num2,int newScale, RoundingMode roundingMode){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.subtract(bdNum2).setScale(newScale,roundingMode);
        return result.doubleValue();
    }
    public static double subtract(double num1, double num2){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.subtract(bdNum2);
        return result.doubleValue();
    }

    public static double subtractForString(List<String> numbs){
        if(numbs == null || numbs.size() == 0){
            return 0.00d;
        }
        BigDecimal count = new BigDecimal(numbs.get(0));
        numbs = numbs.subList(1,numbs.size());
        for (String num : numbs){
            if(num == null || "".equals(num)){
                continue;
            }
            count = count.subtract(new BigDecimal(num));
        }
        return count.doubleValue();
    }

    public static double subtractForString(String... numbs){
        if (numbs == null || numbs.length == 0){
            return 0.00d;
        }
        return subtractForString(Arrays.asList(numbs));
    }
    public static double multiply(double num1, double num2,int newScale, RoundingMode roundingMode){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.multiply(bdNum2).setScale(newScale,roundingMode);
        return result.doubleValue();
    }

    public static double multiply(double num1, double num2){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.multiply(bdNum2);
        return result.doubleValue();
    }
    public static double divide(double num1, double num2,int newScale, RoundingMode roundingMode){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.divide(bdNum2).setScale(newScale,roundingMode);
        return result.doubleValue();
    }
    public static double divide(double num1, double num2){
        BigDecimal bdNum1 = new BigDecimal(Double.toString(num1));
        BigDecimal bdNum2 = new BigDecimal(Double.toString(num2));
        BigDecimal result = bdNum1.divide(bdNum2, 4, BigDecimal.ROUND_HALF_EVEN);
        return result.doubleValue();
    }
}
