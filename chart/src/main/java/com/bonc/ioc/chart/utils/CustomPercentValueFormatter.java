package com.bonc.ioc.chart.utils;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class CustomPercentValueFormatter extends ValueFormatter {
    public DecimalFormat mFormat;
    private PieChart pieChart;

    public CustomPercentValueFormatter() {
        mFormat = new DecimalFormat(",###.##");
    }

    // Can be used to remove percent signs if the chart isn't in percent mode
    public CustomPercentValueFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " %";
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
       /* if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
            // Converted to percent
            return getFormattedValue(value);
        } else {
            // raw value, skip percent sign
            return mFormat.format(value);
        }*/
        return getFormattedValue(value);
    }

}
