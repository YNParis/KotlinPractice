package com.demos.kotlin.activity.charts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;
import com.demos.kotlin.views.charts.CommonChartView;

public class BaseChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_base);
        CommonChartView chartView = findViewById(R.id.chart_view);
        chartView.initView(CommonChartView.PIE_CHART);
        chartView.setData(9, 50);
    }
}
