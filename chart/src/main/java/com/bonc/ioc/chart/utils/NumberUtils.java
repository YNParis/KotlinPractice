package com.bonc.ioc.chart.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    /**
     * 比较两个浮点型是否相等，根据代码规范，不能直接用==比较
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < 0.0000001 ? true : false;
    }

    /**
     * 获得单位是亿、万的数值
     *
     * @return
     */
    public static String getStringWithUnit(String value) {
        if (value == null) return "";
        double v;
        try {
            v = Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }

        if (v / 100000000 > 1) {
            return ((int) Math.floor(v / 100000000)) + "亿";
        }
        if (v / 10000 > 1) {
            return ((int) Math.floor(v / 10000)) + "万";
        }

        return value;
    }

    /**
     * 统一值显示的单位
     *
     * @param value
     * @param unit
     * @return
     */
    public static String getValueWithUnit(double value, String unit) {
        if (unit.contains("万"))
            return new DecimalFormat(",###.####").format(value) + unit;
        if (unit.contains("%"))
            return new DecimalFormat(",###.##").format(value) + unit;
        if (unit.contains("人")
                || unit.contains("次")
                || unit.contains("家")
                || unit.contains("月")
                || unit.contains("元")
                || unit.contains("个"))
            return new DecimalFormat(",###").format(value) + unit;
        if (unit.contains("指数"))
            return new DecimalFormat(",###.##").format(value);
        return value + unit;
    }

    /**
     * 统一值显示的单位
     *
     * @param value
     * @param unit
     * @return
     */
    public static String getValueByUnit(double value, String unit) {
        if (unit.contains("万"))
            return new DecimalFormat(",###.####").format(value);
        if (unit.contains("%"))
            return new DecimalFormat(",###.##").format(value);
        if (unit.contains("人")
                || unit.contains("次")
                || unit.contains("家")
                || unit.contains("个")
                || unit.contains("元")
                || unit.contains("月"))
            return new DecimalFormat(",###").format(value);
        if (unit.contains("指数"))
            return new DecimalFormat(",###.##").format(value);
        return String.valueOf(value);
    }

    /**
     * 统一值显示的单位
     *
     * @param value
     * @param unit
     * @return
     */
    public static String getValueWithUnit(String value, String unit) {
        try {
            double v = Double.parseDouble(value);
            if (unit.contains("万"))
                return new DecimalFormat(",###.####").format(v) + unit;
            if (unit.contains("%"))
                return new DecimalFormat(",###.##").format(v) + unit;
            if (unit.contains("人")
                    || unit.contains("次")
                    || unit.contains("家")
                    || unit.contains("月")
                    || unit.contains("元")
                    || unit.contains("个"))
                return new DecimalFormat(",###").format(v) + unit;
            if (unit.contains("指数"))
                return new DecimalFormat(",###.##").format(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value + unit;
    }

    /**
     * 统一值显示的单位
     *
     * @param value
     * @param unit
     * @return
     */
    public static String getValueByUnit(String value, String unit) {
        if (value == null) return "";
        try {
            double v = Double.parseDouble(value);
            if (unit.contains("万"))
                return new DecimalFormat(",###.####").format(v);      //String.format("%,.4f", v);
            if (unit.contains("%"))
                return new DecimalFormat(",###.##").format(v);        //String.format("%,.2f", v);
            if (unit.contains("人")
                    || unit.contains("次")
                    || unit.contains("家")
                    || unit.contains("月")
                    || unit.contains("元")
                    || unit.contains("个"))
                return new DecimalFormat(",###").format(v);           //String.format("%,d", (int) v);
            if (unit.contains("指数"))
                return new DecimalFormat(",###.##").format(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
