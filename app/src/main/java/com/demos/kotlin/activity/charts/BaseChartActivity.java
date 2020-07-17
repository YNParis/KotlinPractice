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

    String[] labels = new String[10];
    List<Float> values = new ArrayList<>();
    List<float[]> valuesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_base);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            labels[i] = "标签 " + i;
            values.add(new Random().nextFloat() * 100);
        }
        for (int i = 0; i < 3; i++) {
            float[] list = new float[labels.length];
            for (int j = 0; j < labels.length; j++) {
                list[j] = new Random().nextFloat() * 100;
            }
            valuesList.add(list);
        }

    }

    private void initView() {

        /* 饼图*/
        CommonChartView chartView = findViewById(R.id.pie_view);
        chartView.initView(CommonChartView.PIE_CHART);
        chartView.setPieData(labels, values);

        /* 折线图*/
        CommonChartView lineView = findViewById(R.id.lines_view);
        lineView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        lineView.initView(CommonChartView.LINES_CHART);
        lineView.setLinesChartData(labels, valuesList, true);

        /* 柱状图*/
        CommonChartView barsView = findViewById(R.id.bars_view);
        barsView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_QUARTER));
        barsView.initView(CommonChartView.BARS_CHART);
        barsView.setBarsData(labels, valuesList);

        /* 混合图*/
        CommonChartView combinedView = findViewById(R.id.combined_view);
        combinedView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        combinedView.initView(CommonChartView.COMBINED_CHART);
        combinedView.setCombinedChart(labels, valuesList);

        /* 叠加柱状图*/
        CommonChartView stackedView = findViewById(R.id.stacked_view);
        stackedView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        stackedView.initView(CommonChartView.STACKED_CHART);
        stackedView.setStackedData(labels, valuesList);/*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 叠加柱状图*/
        CommonChartView combinedStackedView = findViewById(R.id.combined_stacked_view);
        combinedStackedView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        combinedStackedView.initView(CommonChartView.COMBINED_CHART);
        combinedStackedView.setCombinedChart(labels, valuesList, true);/*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 横向柱状图*/
        CommonChartView horizontalView = findViewById(R.id.horizontal_view);
        horizontalView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        horizontalView.initView(CommonChartView.HORIZONTAL_CHART);
        horizontalView.setHorizontalData(labels, valuesList);/*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 蜡烛图*/
        CommonChartView candleView = findViewById(R.id.candle_view);
        candleView.setXAxisValueFormatter(new CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT));
        candleView.initView(CommonChartView.CANDLE_CHART);
        candleView.setCandleData(labels, valuesList);/*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/
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
