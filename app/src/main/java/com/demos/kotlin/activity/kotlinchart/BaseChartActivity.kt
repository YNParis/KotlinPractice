package com.demos.kotlin.activity.kotlinchart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demos.kotlin.R
import com.demos.kotlin.views.charts.CommonChartView
import com.demos.kotlin.views.charts.CustomAxisValueFormatter
import java.util.*

class BaseChartActivity : AppCompatActivity() {
    var labels = arrayOfNulls<String>(10)
    var labels2 = arrayOfNulls<String>(5)
    var values = FloatArray(10)
    var values2 = FloatArray(5)
    var valuesList: MutableList<FloatArray> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_base)
        initData()
        initView()
    }

    private fun initData() {
        for (i in 0..9) {
            labels[i] = "标签 $i"
            values[i] = Random().nextFloat() * 100
        }
        for (i in 0..2) {
            val list = FloatArray(labels.size)
            for (j in labels.indices) {
                list[j] = Random().nextFloat() * 100
            }
            valuesList.add(list)
        }
        System.arraycopy(labels, 0, labels2, 0, 5)
        System.arraycopy(values, 0, values2, 0, 5)
    }

    private fun initView() {

        /* 饼图*/
        val fewChartView = findViewById<CommonChartView>(R.id.pie_view)
        fewChartView.initView(CommonChartView.PIE_CHART)
        fewChartView.setPieData(labels2, values2)

        /* 饼图，数量多*/
        val manyChartView = findViewById<CommonChartView>(R.id.pie_many_view)
        manyChartView.initView(CommonChartView.PIE_CHART)
        manyChartView.setPieData(labels, values)

        /* 折线图*/
        val lineView = findViewById<CommonChartView>(R.id.lines_view)
        lineView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        lineView.initView(CommonChartView.LINES_CHART)
        lineView.setLinesChartData(labels, valuesList, true)

        /* 柱状图*/
        val barsView = findViewById<CommonChartView>(R.id.bars_view)
        barsView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_QUARTER))
        barsView.initView(CommonChartView.BARS_CHART)
        barsView.setBarsData(labels, valuesList)

        /* 混合图*/
        val combinedView = findViewById<CommonChartView>(R.id.combined_view)
        combinedView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        combinedView.initView(CommonChartView.COMBINED_CHART)
        combinedView.setCombinedChart(labels, valuesList)

        /* 叠加柱状图*/
        val stackedView = findViewById<CommonChartView>(R.id.stacked_view)
        stackedView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        stackedView.initView(CommonChartView.STACKED_CHART)
        stackedView.setStackedData(labels, valuesList) /*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 叠加柱状图*/
        val combinedStackedView = findViewById<CommonChartView>(R.id.combined_stacked_view)
        combinedStackedView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        combinedStackedView.initView(CommonChartView.COMBINED_CHART)
        combinedStackedView.setCombinedChart(labels, valuesList, true) /*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 横向柱状图*/
        val horizontalView = findViewById<CommonChartView>(R.id.horizontal_view)
        horizontalView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        horizontalView.initView(CommonChartView.HORIZONTAL_CHART)
        horizontalView.setHorizontalData(labels, valuesList) /*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/

        /* 蜡烛图*/
        val candleView = findViewById<CommonChartView>(R.id.candle_view)
        candleView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT))
        candleView.initView(CommonChartView.CANDLE_CHART)
        candleView.setCandleData(labels, valuesList) /*label长度，是每个柱的块数目，valuesList长度，是x轴总共有几个柱*/
    }

    /**
     * 切换统计维度
     *
     * @param chartView
     */
    private fun switchUnit(chartView: CommonChartView) {
        chartView.setXAxisValueFormatter(CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_YEAR))
    }
}