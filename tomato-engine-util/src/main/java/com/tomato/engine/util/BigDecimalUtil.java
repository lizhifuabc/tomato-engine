package com.tomato.engine.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 工具类
 *
 * @author lizhifu
 * @since 2024/10/25
 */
public class BigDecimalUtil {
    /**
     * 金额 保留小数点 2
     */
    public static final int AMOUNT_DECIMAL_POINT = 2;

    /**
     * 百分位
     */
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 金额相关计算方法：四舍五入 保留2位小数点
     */
    public static class Amount {

        /**
         * 加法运算，保留 2 位小数，四舍五入。
         *
         * @param num1 第一个加数
         * @param num2 第二个加数
         * @return 两个加数之和，保留指定的小数位数
         */
        public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
            return setScale(num1.add(num2), AMOUNT_DECIMAL_POINT);
        }

        /**
         * 对两个BigDecimal类型的数进行乘法运算，保留 2 位小数，四舍五入。
         *
         * @param num1 第一个乘数
         * @param num2 第二个乘数
         * @return 两个乘数之积，保留指定的小数位数
         */
        public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
            return setScale(num1.multiply(num2), AMOUNT_DECIMAL_POINT);
        }

        /**
         * 对两个BigDecimal类型的数进行减法运算，保留 2 位小数，四舍五入。
         *
         * @param num1 被减数
         * @param num2 减数
         * @return 减法运算的结果，保留指定的小数位数
         */
        public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
            return setScale(num1.subtract(num2), AMOUNT_DECIMAL_POINT);
        }

        /**
         * 将两个BigDecimal类型的数进行除法运算，保留 2 位小数，四舍五入。
         *
         * @param num1 被除数，不能为null
         * @param num2 除数，不能为null且不能为0
         * @return 除法运算的结果，保留指定的小数位数
         */
        public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
            return setScale(num1.divide(num2, RoundingMode.HALF_UP), AMOUNT_DECIMAL_POINT);
        }
    }


    /**
     * BigDecimal 加法 num1 + num2 四舍五入
     *
     * @param num1 num1
     * @param num2 num2
     * @param point 保留小数位数
     * @return BigDecimal
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.add(num2), point);
    }


    /**
     * 将多个BigDecimal相加，并保留指定的小数位数，四舍五入
     *
     * @param point 需要保留的小数位数
     * @param array 需要相加的BigDecimal数组
     * @return 相加后的结果，保留指定的小数位数
     */
    public static BigDecimal add(int point, BigDecimal... array) {
        BigDecimal res = new BigDecimal(0);
        for (BigDecimal item : array) {
            if (item == null) {
                res = res.add(BigDecimal.ZERO);
            } else {
                res = res.add(item);
            }
        }
        return setScale(res, point);
    }


    /**
     * 对两个BigDecimal类型的数进行乘法运算，并保留指定的小数位数。四舍五入
     *
     * @param num1 第一个乘数
     * @param num2 第二个乘数
     * @param point 需要保留的小数位数
     * @return 两个乘数之积，保留指定的小数位数
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.multiply(num2), point);
    }


    /**
     * 对两个BigDecimal类型的数进行减法运算，并保留指定的小数位数。四舍五入
     *
     * @param num1 被减数
     * @param num2 减数
     * @param point 需要保留的小数位数
     * @return 减法运算的结果，保留指定的小数位数
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.subtract(num2), point);
    }


    /**
     * 对两个BigDecimal类型的数进行除法运算，并保留指定的小数位数。四舍五入
     *
     * @param num1 被除数
     * @param num2 除数，不能为0
     * @param point 需要保留的小数位数
     * @return 除法运算的结果
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int point) {
        return num1.divide(num2, point, RoundingMode.HALF_UP);
    }


    /**
     * 设置BigDecimal对象的小数位数，使用四舍五入的方式。
     *
     * @param num 需要设置小数位数的BigDecimal对象
     * @param point 需要设置的小数位数
     * @return 设置小数位数后的BigDecimal对象
     */
    public static BigDecimal setScale(BigDecimal num, int point) {
        return num.setScale(point, RoundingMode.HALF_UP);
    }

    /**
     * 比较 num1 是否大于 num2
     *
     * @param num1 num1
     * @param num2 num2
     * @return boolean
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    /**
     * 比较 num1 是否大于等于 num2
     *
     * @param num1 num1
     * @param num2 num2
     * @return boolean
     */
    public static boolean isGreaterOrEqual(BigDecimal num1, BigDecimal num2) {
        return isGreaterThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否小于 num2
     *
     * @param num1 num1
     * @param num2 num2
     * @return boolean
     */
    public static boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0;
    }

    /**
     * 比较 num1 是否小于等于 num2
     *
     * @param num1 num1
     * @param num2 num2
     * @return boolean
     */
    public static boolean isLessOrEqual(BigDecimal num1, BigDecimal num2) {
        return isLessThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否等于 num2
     *
     * @param num1 num1
     * @param num2 num2
     * @return boolean
     */
    public static boolean equals(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }


    /**
     * 计算两个整数的百分比 四舍五入
     *
     * @param num1 分子
     * @param num2 分母
     * @param point 结果保留的小数位数
     * @return 两个整数的百分比，类型为BigDecimal
     */
    public static BigDecimal percent(Integer num1, Integer num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (num2.equals(0)) {
            return BigDecimal.ZERO;
        }
        return percent(new BigDecimal(num1), new BigDecimal(num2), point);
    }


    /**
     * 计算 num1 / num2 的百分比 四舍五入
     *
     * @param num1 分子
     * @param num2 分母
     * @param point 结果保留的小数位数
     * @return 百分比结果，类型为BigDecimal
     */
    public static BigDecimal percent(BigDecimal num1, BigDecimal num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (equals(BigDecimal.ZERO, num2)) {
            return BigDecimal.ZERO;
        }
        BigDecimal percent = num1.divide(num2, point + 2, RoundingMode.HALF_UP);
        // 设置小数点后两位，并四舍五入
        return percent.multiply(ONE_HUNDRED).setScale(point,RoundingMode.HALF_UP);
    }


    /**
     * 返回两个BigDecimal中的最大值
     *
     * @param num1 第一个BigDecimal值
     * @param num2 第二个BigDecimal值
     * @return 两个BigDecimal中的最大值
     */
    public static BigDecimal max(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0 ? num1 : num2;
    }


    /**
     * 返回两个BigDecimal中的最小值
     *
     * @param num1 第一个BigDecimal值
     * @param num2 第二个BigDecimal值
     * @return 两个BigDecimal中的最小值
     */
    public static BigDecimal min(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0 ? num1 : num2;
    }

    public static void main(String[] args) {
        Amount.add(new BigDecimal("3"), new BigDecimal("11"));

        System.out.println(percent(new BigDecimal("3"), new BigDecimal("11"), 2));
        System.out.println(percent(new BigDecimal("8"), new BigDecimal("11"), 2));
    }
}
