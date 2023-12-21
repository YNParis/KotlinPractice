package com.bonc.ioc.chart.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 自定义formatter，横轴或者纵轴显示的刻度
 */
public class CustomAxisValueFormatter extends ValueFormatter {

    private final String[] mMonths = new String[]{
            "01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月"
    };
    private final String[] mQuarters = new String[]{
            "第一季度", "第二季度", "第三季度", "第四季度"
    };
    private String[] mYears;
    private String[] xAxisValue;
    public static final String X_AXIS_MONTH = "month";
    public static final String X_AXIS_QUARTER = "quarter";
    public static final String X_AXIS_YEAR = "year";
    public static final String X_AXIS_INT = "int";
    public static final String X_AXIS_FLOAT_2 = "float_2";

    private String dateType;

    public CustomAxisValueFormatter() {
    }

    public CustomAxisValueFormatter(String type) {
        dateType = type;
    }

    public CustomAxisValueFormatter(String[] xAxisValue) {
        this.xAxisValue = xAxisValue;
    }

    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        if (dateType != null) {
            /*横轴是时间*/
            switch (dateType) {
                case X_AXIS_MONTH:
                    return mMonths[index % mMonths.length];
                case X_AXIS_QUARTER:
                    return mQuarters[index % mQuarters.length];
                case X_AXIS_YEAR:
                    return getYear(index);
                case X_AXIS_INT:
                    return String.valueOf(index);
                case X_AXIS_FLOAT_2:
                default:
                    return getStringValueOfFloat(value);
            }
        }
        if (xAxisValue != null) {
            return xAxisValue[index % xAxisValue.length];
        }
        return getStringValueOfFloat(value);
    }

    /**
     * 返回保留两位小数
     *
     * @param value
     * @return
     */
    private String getStringValueOfFloat(float value) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(value);
    }

    /**
     * 返回整数部分
     *
     * @param value
     * @return
     */
    private String getStringValueOfInt(float value) {
        return String.valueOf(((int) value));
    }

    private String getYear(int index) {
        if (mYears == null) {
            mYears = new String[]{};
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = 0; i < 5; i++) {
                mYears[i] = (year - 5 + i) + "";
            }
        }
        return mYears[index];
    }

}
