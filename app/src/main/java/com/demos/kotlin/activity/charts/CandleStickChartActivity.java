
package com.demos.kotlin.activity.charts;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.WindowManager;

import com.demos.kotlin.R;
import com.demos.kotlin.activity.DemoBase;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 蜡烛图
 */
public class CandleStickChartActivity extends DemoBase implements OnChartValueSelectedListener {

    private CandleStickChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_candlechart);

        setTitle("CandleStickChartActivity");

        chart = findViewById(R.id.candle_chart);
        chart.setBackgroundColor(Color.WHITE);

        chart.getDescription().setEnabled(false);

        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getLegend().setEnabled(false);

        setData();
    }

    private void setData() {
        ArrayList<CandleEntry> values = new ArrayList<>();
        ArrayList<CandleEntry> values2 = new ArrayList<>();

        int baseData = 15;/*上一年的结余*/

        int[] val1 = new int[12];
        int[] val2 = new int[12];
        int[] base = new int[12];

        for (int i = 0; i < 12; i++) {
            val1[i] = new Random().nextInt(100);
            val2[i] = new Random().nextInt(val1[i]);
        }
        /*第一个，low=baseData，high=low+value；后面的low=high-value*/
        for (int i = 0; i < 12; i++) {
            int high, low1, low2;
            if (i == 0) {
                low1 = baseData;
            } else {
                low1 = base[i - 1];
            }
            high = val1[i] + low1;
            low2 = high - val2[i];
            base[i] = low2;
            values.add(new CandleEntry(i, high, low1, low1, high));
            values2.add(new CandleEntry(i + 0.5f, high, low2, low2, high));

        }

        CandleDataSet set1 = new CandleDataSet(values, "Data Set");
        CandleDataSet set2 = new CandleDataSet(values2, "Data Set 2");

        set1.setDrawIcons(false);
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setIncreasingColor(Color.BLUE);
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setBarSpace(0.4f);
        //set1.setHighlightLineWidth(1f);

        set2.setDrawIcons(false);
        set2.setAxisDependency(AxisDependency.LEFT);
        set2.setIncreasingColor(Color.RED);
        set2.setIncreasingPaintStyle(Paint.Style.FILL);
        set2.setBarSpace(0.8f);

        List<ICandleDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        CandleData data = new CandleData(dataSets);

        float groupSpace = 0.4f;
        float barSpace = 0.01f;
        float barWidth = (1 - groupSpace) / dataSets.size() - barSpace;

        chart.setData(data);
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "CandleStickChartActivity");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
