package com.demos.kotlin.activity.kotlinchart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.demos.kotlin.views.charts.CustomAxisValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.*

/**
 * 多个的柱状图
 */
class BarChartActivityMultiDataset : DemoBase(), OnChartValueSelectedListener {
    private lateinit var chart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barchart)
        title = "BarChartActivityMultiDataset"
        chart = findViewById(R.id.chart1)
        chart.setOnChartValueSelectedListener(this)
        chart.getDescription().isEnabled = false
        chart.setDrawGridBackground(false)
        val textColor = -0x737374
        val textSize = 18f
        chart.setPinchZoom(false)
        //        chart.setDrawBarShadow(false);

        /* 点击条目的弹出窗口 */
        /*  MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart*/
        val l = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 15f //图例的色块大小
        l.yOffset = 0f
        l.xOffset = 20f
        l.textColor = textColor
        l.xEntrySpace = 20f
        l.textSize = textSize
        val xAxis = chart.getXAxis()
        xAxis.granularity = 1f
        xAxis.axisLineWidth = 1.5f
        xAxis.setDrawGridLines(false)
        xAxis.setCenterAxisLabels(true)
        xAxis.textColor = textColor
        xAxis.textSize = textSize
        xAxis.axisLineColor = textColor
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_INT)
        val leftAxis = chart.getAxisLeft()
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = textColor
        leftAxis.gridLineWidth = 0.5f
        leftAxis.spaceBottom = 100f
        leftAxis.setDrawAxisLine(false)
        leftAxis.textColor = textColor
        leftAxis.textSize = textSize
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        chart.getAxisRight().isEnabled = false
        setData()
    }

    private fun setData() {
        val groupSpace = 0.4f
        val barSpace = 0.01f // x4 DataSet
        val barWidth = 0.11f // x4 DataSet
        // (0.11f + 0.01f) * 5 + 0.4f = 1.00 -> interval per "group"
        val endYear = Calendar.getInstance()[Calendar.YEAR]
        val startYear = endYear - 5
        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()
        val values3 = ArrayList<BarEntry>()
        val values4 = ArrayList<BarEntry>()
        val values5 = ArrayList<BarEntry>()
        for (i in startYear until endYear) {
            values1.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
            values2.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
            values3.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
            values4.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
            values5.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
        }
        val set1: BarDataSet
        val set2: BarDataSet
        val set3: BarDataSet
        val set4: BarDataSet
        val set5: BarDataSet
        // create 4 DataSets
        val colors = resources.getIntArray(R.array.color_charts)
        set1 = BarDataSet(values1, "养老保险")
        set2 = BarDataSet(values2, "医疗保险")
        set3 = BarDataSet(values3, "生育保险")
        set4 = BarDataSet(values4, "失业保险")
        set5 = BarDataSet(values5, "工伤保险")
        set1.color = colors[0]
        set2.color = colors[1]
        set3.color = colors[2]
        set4.color = colors[3]
        set5.color = colors[4]
        val data = BarData(set1, set2, set3, set4, set5)
        data.setValueFormatter(LargeValueFormatter())
        data.setValueTextColor(Color.TRANSPARENT)
        chart!!.data = data
        chart!!.barData.barWidth = barWidth
        chart!!.xAxis.axisMinimum = startYear.toFloat()
        chart!!.xAxis.axisMaximum = startYear + chart!!.barData.getGroupWidth(groupSpace, barSpace) * 5
        chart!!.groupBars(startYear.toFloat(), groupSpace, barSpace)
        chart!!.invalidate()
    }

    override fun saveToGallery() {}
    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.dataSetIndex)
    }

    override fun onNothingSelected() {
        Log.i("Activity", "Nothing selected.")
    }
}