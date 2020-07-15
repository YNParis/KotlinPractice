package com.demos.kotlin.activity.charts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.views.charts.CommonChartView;
import com.demos.kotlin.views.charts.CustomAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseChartActivity extends AppCompatActivity {

    List<String> labels = new ArrayList<>();
    List<Float> values = new ArrayList<>();
    List<List<Float>> valuesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_base);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            labels.add("标签 " + i);
            values.add(new Random().nextFloat() * 10);
        }
        for (int i = 0; i < 3; i++) {
            List<Float> list = new ArrayList<>();
            for (int j = 0; j < labels.size(); j++) {
                list.add(new Random().nextFloat() * 10);
            }
            valuesList.add(list);
        }

    }

    private void initView() {

        /* 饼图*/
        CommonChartView chartView = findViewById(R.id.chart_view);
        chartView.initView(CommonChartView.PIE_CHART);
        chartView.setPieData(labels, values);

        /* 折线图*/
        CommonChartView lineView = findViewById(R.id.line_view);
        lineView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_QUARTER));
        lineView.initView(CommonChartView.LINES_CHART);
        lineView.setLinesChartData(labels, valuesList);

        /* 柱状图*/
        CommonChartView barsView = findViewById(R.id.bars_view);
        barsView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_QUARTER));
        barsView.initView(CommonChartView.BARS_CHART);
        barsView.setBarsData(labels, valuesList);

    }

    /**
     * 切换统计维度
     *
     * @param chartView
     */
    private void switchUnit(CommonChartView chartView) {
        chartView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_YEAR));
    }
}
