package com.demos.kotlin.activity.kotlinchart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.WindowManager
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.*

/**
 * 蜡烛图
 */
class CandleStickChartActivity : DemoBase(), OnChartValueSelectedListener {
    private lateinit var chart: CandleStickChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_candlechart)
        title = "CandleStickChartActivity"
        chart = findViewById(R.id.candle_chart)
        chart.setBackgroundColor(Color.WHITE)
        chart.getDescription().isEnabled = false
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        val xAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        val leftAxis = chart.getAxisLeft()
        //        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false)
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)
        val rightAxis = chart.getAxisRight()
        rightAxis.isEnabled = false
        chart.getLegend().isEnabled = false
        setData()
    }

    private fun setData() {
        val values = ArrayList<CandleEntry>()
        val values2 = ArrayList<CandleEntry>()
        val baseData = 15 /*上一年的结余*/
        val val1 = IntArray(12)
        val val2 = IntArray(12)
        val base = IntArray(12)
        for (i in 0..11) {
            val1[i] = Random().nextInt(100)
            val2[i] = Random().nextInt(val1[i])
        }
        /*第一个，low=baseData，high=low+value；后面的low=high-value*/for (i in 0..11) {
            var high: Int
            var low1: Int
            var low2: Int
            low1 = if (i == 0) {
                baseData
            } else {
                base[i - 1]
            }
            high = val1[i] + low1
            low2 = high - val2[i]
            base[i] = low2
            values.add(CandleEntry(i.toFloat(), high.toFloat(), low1.toFloat(), low1.toFloat(), high.toFloat()))
            values2.add(CandleEntry(i + 0.5f, high.toFloat(), low2.toFloat(), low2.toFloat(), high.toFloat()))
        }
        val set1 = CandleDataSet(values, "Data Set")
        val set2 = CandleDataSet(values2, "Data Set 2")
        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.LEFT
        set1.increasingColor = Color.BLUE
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.barSpace = 0.4f
        //set1.setHighlightLineWidth(1f);
        set2.setDrawIcons(false)
        set2.axisDependency = AxisDependency.LEFT
        set2.increasingColor = Color.RED
        set2.increasingPaintStyle = Paint.Style.FILL
        set2.barSpace = 0.8f
        val dataSets: MutableList<ICandleDataSet> = ArrayList()
        dataSets.add(set1)
        dataSets.add(set2)
        val data = CandleData(dataSets)
        val groupSpace = 0.4f
        val barSpace = 0.01f
        val barWidth = (1 - groupSpace) / dataSets.size - barSpace
        chart!!.data = data
        chart!!.invalidate()
    }

    override fun saveToGallery() {
        saveToGallery(chart, "CandleStickChartActivity")
    }

    override fun onValueSelected(e: Entry, h: Highlight) {}
    override fun onNothingSelected() {}
}