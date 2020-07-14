package com.demos.kotlin.activity.charts;

import android.graphics.RectF;
import android.os.Bundle;
import android.view.WindowManager;

import com.demos.kotlin.R;
import com.demos.kotlin.activity.DemoBase;
import com.demos.kotlin.bean.MarkerDataBean;
import com.demos.kotlin.views.charts.CombinedMarkerView;
import com.demos.kotlin.views.charts.DayAxisValueFormatter;
import com.demos.kotlin.views.charts.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图
 */
public class BarChartActivity extends DemoBase implements OnChartValueSelectedListener {

    private BarChart chart;
    private CombinedMarkerView mv;
    private List<MarkerDataBean> markerDataBeans;
    private BarDataSet set1;
    private ArrayList<BarEntry> values;//柱状图，填充横竖坐标值，两个属性

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bar_chart);

        setTitle("BarChartActivity");

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        setData();

        chart.getLegend().setEnabled(true);
    }

    //TODO 网络请求之后，设置图表，或者是刷新数据
    private void setData() {
        //填数据
        float start = 1f;
        if (values == null) {
            values = new ArrayList<>();
        } else {
            values.clear();
        }
        for (int i = (int) start; i < start + 10; i++) {
            float val = (float) (Math.random() * (20 + 1));
            values.add(new BarEntry(i, val));
        }
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "The year 2017");
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            final BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
            data.setBarWidth(0.4f);
            chart.setData(data);
            mv = new CombinedMarkerView(this);
            markerDataBeans = new ArrayList<>();//
            mv.setChartView(chart); // For bounds control
            chart.setMarker(mv); // Set the marker to the chart
        }
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivity");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        //刷新markerView的数据
        markerDataBeans.clear();
        markerDataBeans.add(new MarkerDataBean(set1.getLabel(), String.valueOf(values.get((int) (h.getX() - 1)).getY())));
        mv.setData(markerDataBeans, "marker的title", values.size());
        chart.getBarBounds((BarEntry) e, new RectF());
        MPPointF position = chart.getPosition(e, AxisDependency.LEFT);
        mv.refreshContent(e, h);
        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }
}
