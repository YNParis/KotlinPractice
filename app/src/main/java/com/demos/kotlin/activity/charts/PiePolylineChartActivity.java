
package com.demos.kotlin.activity.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.demos.kotlin.R;
import com.demos.kotlin.activity.DemoBase;
import com.demos.kotlin.views.charts.CustomPieRenderer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * 饼图
 */
public class PiePolylineChartActivity extends DemoBase implements OnChartValueSelectedListener {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        setTitle("PiePolylineChartActivity");
        initChart();
        setData(10, 20);
    }

    private void initChart() {
        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(true);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.8f);
//        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(true);//环形
        chart.setDrawCenterText(false);//中间不绘制文字
        chart.setHoleColor(Color.WHITE);

       /* chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(120);*/

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);//设置可点击效果

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);
        //设置label颜色
        chart.setDrawEntryLabels(true);
        chart.setEntryLabelColor(0xFF000000);
        chart.setRenderer(new CustomPieRenderer(chart, chart.getAnimator(), chart.getViewPortHandler()));

//        chart.animateY(1400, Easing.EaseInOutQuad);//动画效果
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + range / 5;
            entries.add(new PieEntry(value, parties[i % parties.length] + "," + value));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        //slice之间间隔
        dataSet.setSliceSpace(2f);
        //选中后放大的偏移量
        dataSet.setSelectionShift(15f);

        //图标颜色
        int[] colors = getResources().getIntArray(R.array.color_charts_detail);
        dataSet.setColors(colors);

        //设置折线指示线
        dataSet.setValueLinePart1OffsetPercentage(100.f);//设置折现离内边的距离，%，设为100表示从外边开始画
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setValueLineWidth(0.7f);
        dataSet.setValueLineColor(0x73000000);

        //饼图内，显示百分比
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        PieData data = new PieData(dataSet);
        //value的样式
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        //自定义X轴标签位置

        // undo all highlights
//        chart.highlightValues(null);
        chart.invalidate();
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "PiePolylineChartActivity");
    }

    private SpannableString generateLabelSpannableText(String value, String label) {

        SpannableString s = new SpannableString(label + "\n" + value);
        s.setSpan(new ForegroundColorSpan(0xFF000000), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), label.length(), s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


}
