
package com.demos.kotlin.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_polyline);
        setTitle("PieChartActivity");
        chart = findViewById(R.id.pie_chart);
        initChart(300, 0, R.color.colorAccent);
    }

    private void initChart(int count, int drawableCenter, int color) {
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setHoleRadius(65f);
        chart.getLegend().setEnabled(false);
        chart.setRotationAngle(-90);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        setData(count, color);
    }

    private void setData(int count, int color) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(count, ""));
        entries.add(new PieEntry(100 - count, ""));
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        int[] colors = {color, 0x355AD8A6};
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.TRANSPARENT);
        chart.setData(data);
//        chart.highlightValues(null);
        chart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
