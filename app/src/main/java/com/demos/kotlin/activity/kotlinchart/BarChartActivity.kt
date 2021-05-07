package com.demos.kotlin.activity.kotlinchart

import android.graphics.RectF
import android.os.Bundle
import android.view.WindowManager
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.demos.kotlin.bean.MarkerDataBean
import com.demos.kotlin.views.charts.CombinedMarkerView
import com.demos.kotlin.views.charts.DayAxisValueFormatter
import com.demos.kotlin.views.charts.MyValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import java.util.*

/**
 * 柱状图
 */
class BarChartActivity : DemoBase(), OnChartValueSelectedListener {
    private lateinit var chart: BarChart
    private lateinit var mv: CombinedMarkerView
    private var markerDataBeans: MutableList<MarkerDataBean>? = null
    private var set1: BarDataSet? = null
    private var values //柱状图，填充横竖坐标值，两个属性
            : ArrayList<BarEntry>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_bar_chart)
        title = "BarChartActivity"
        chart = findViewById(R.id.chart1)
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        // chart.setDrawYLabels(false);
        val xAxisFormatter: ValueFormatter = DayAxisValueFormatter(chart)
        val xAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter
        val custom: ValueFormatter = MyValueFormatter("$")
        val leftAxis = chart.getAxisLeft()
        leftAxis.setLabelCount(8, false)
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val rightAxis = chart.getAxisRight()
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.valueFormatter = custom
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val l = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f
        setData()
        chart.getLegend().isEnabled = true
    }

    //TODO 网络请求之后，设置图表，或者是刷新数据
    private fun setData() {
        //填数据
        val start = 1f
        if (values == null) {
            values = ArrayList()
        } else {
            values!!.clear()
        }
        var i = start.toInt()
        while (i < start + 10) {
            val value = (Math.random() * (20 + 1)).toFloat()
            values!!.add(BarEntry(i.toFloat(), value))
            i++
        }
        if (chart!!.data != null && chart!!.data.dataSetCount > 0) {
            set1 = chart!!.data.getDataSetByIndex(0) as BarDataSet
            set1!!.values = values
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1!!.setDrawIcons(false)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1!!)
            val data = BarData(dataSets)
            //            data.setValueTextSize(10f);
            data.barWidth = 0.4f
            chart!!.data = data
            mv = CombinedMarkerView(this)
            markerDataBeans = ArrayList() //
            mv!!.chartView = chart // For bounds control
            chart!!.marker = mv // Set the marker to the chart
        }
    }

    override fun saveToGallery() {
        saveToGallery(chart, "BarChartActivity")
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        if (e == null) return
        //刷新markerView的数据
        markerDataBeans!!.clear()
        markerDataBeans!!.add(MarkerDataBean(set1!!.label, values!![(h.x - 1).toInt()].y.toString()))
        mv!!.setData(markerDataBeans, "marker的title", values!!.size)
        chart!!.getBarBounds(e as BarEntry, RectF())
        val position = chart!!.getPosition(e, AxisDependency.LEFT)
        mv!!.refreshContent(e, h)
        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {}
}