package com.demos.kotlin.activity.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.demos.kotlin.R;
import com.demos.kotlin.activity.DemoBase;
import com.demos.kotlin.views.charts.CustomAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 多个的柱状图
 */
public class BarChartActivityMultiDataset extends DemoBase implements OnChartValueSelectedListener {

    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        setTitle("BarChartActivityMultiDataset");
        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        int textColor = 0xFF8C8C8C;
        float textSize = 18f;
        chart.setPinchZoom(false);
//        chart.setDrawBarShadow(false);

        /* 点击条目的弹出窗口 */
      /*  MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart*/

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(15);//图例的色块大小
        l.setYOffset(0f);
        l.setXOffset(20f);
        l.setTextColor(textColor);
        l.setXEntrySpace(20f);
        l.setTextSize(textSize);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setTextColor(textColor);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(textColor);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(textColor);
        leftAxis.setGridLineWidth(0.5f);
        leftAxis.setSpaceBottom(100f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(textColor);
        leftAxis.setTextSize(textSize);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        setData();
    }

    private void setData() {
        float groupSpace = 0.4f;
        float barSpace = 0.01f; // x4 DataSet
        float barWidth = 0.11f; // x4 DataSet
        // (0.11f + 0.01f) * 5 + 0.4f = 1.00 -> interval per "group"

        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = endYear - 5;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();
        ArrayList<BarEntry> values5 = new ArrayList<>();

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * 100)));
            values2.add(new BarEntry(i, (float) (Math.random() * 100)));
            values3.add(new BarEntry(i, (float) (Math.random() * 100)));
            values4.add(new BarEntry(i, (float) (Math.random() * 100)));
            values5.add(new BarEntry(i, (float) (Math.random() * 100)));
        }

        BarDataSet set1, set2, set3, set4, set5;
        // create 4 DataSets
        int[] colors = getResources().getIntArray(R.array.color_charts);
        set1 = new BarDataSet(values1, "养老保险");
        set2 = new BarDataSet(values2, "医疗保险");
        set3 = new BarDataSet(values3, "生育保险");
        set4 = new BarDataSet(values4, "失业保险");
        set5 = new BarDataSet(values5, "工伤保险");

        set1.setColor(colors[0]);
        set2.setColor(colors[1]);
        set3.setColor(colors[2]);
        set4.setColor(colors[3]);
        set5.setColor(colors[4]);

        BarData data = new BarData(set1, set2, set3, set4, set5);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextColor(Color.TRANSPARENT);
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startYear);
        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }
}
