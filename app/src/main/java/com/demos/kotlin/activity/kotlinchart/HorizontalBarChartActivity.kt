package com.demos.kotlin.activity.kotlinchart

import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import java.util.*

/**
 * 水平柱状图
 */
class HorizontalBarChartActivity : DemoBase(), OnChartValueSelectedListener {
    private lateinit var chart: HorizontalBarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_horizontalbarchart)
        title = "HorizontalBarChartActivity"
        chart = findViewById(R.id.horizontal_chart)
        chart.setOnChartValueSelectedListener(this)
        // chart.setHighlightEnabled(false);
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);
        chart.setDrawGridBackground(false)
        val xl = chart.getXAxis()
        xl.position = XAxisPosition.BOTTOM
        xl.setDrawAxisLine(true)
        xl.setDrawGridLines(false)
        xl.granularity = 10f
        val yl = chart.getAxisLeft()
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.axisMinimum = 0f // this replaces setStartAtZero(true)
        //        yl.setInverted(true);
        val yr = chart.getAxisRight()
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f // this replaces setStartAtZero(true)
        //        yr.setInverted(true);
        chart.setFitBars(true)
        val l = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.xEntrySpace = 4f
        setData(10, 90f)
    }

    private fun setData(count: Int, range: Float) {
        val barWidth = 9f
        val spaceForBar = 10f
        val values = ArrayList<BarEntry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            values.add(BarEntry(i * spaceForBar, `val`))
        }
        val set1: BarDataSet
        if (chart!!.data != null &&
                chart!!.data.dataSetCount > 0) {
            set1 = chart!!.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = barWidth
            chart!!.data = data
        }
    }

    override fun saveToGallery() {
        saveToGallery(chart, "HorizontalBarChartActivity")
    }

    private val mOnValueSelectedRectF = RectF()
    override fun onValueSelected(e: Entry, h: Highlight) {
        if (e == null) return
        val bounds = mOnValueSelectedRectF
        chart!!.getBarBounds(e as BarEntry, bounds)
        val position = chart!!.getPosition(e, chart!!.data.getDataSetByIndex(h.dataSetIndex)
                .axisDependency)
        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())
        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {}
}