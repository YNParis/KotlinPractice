package com.demos.kotlin.activity.kotlinchart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.demos.kotlin.views.charts.MyValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

/**
 * 叠加的柱状图
 */
class StackedBarActivity : DemoBase(), OnChartValueSelectedListener {
    private lateinit var chart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_barchart)
        title = "StackedBarActivity"
        chart = findViewById(R.id.chart1)
        chart.setOnChartValueSelectedListener(this)
        chart.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(40)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(false)
        chart.setHighlightFullBarEnabled(false)

        // change the position of the y-labels
        val leftAxis = chart.getAxisLeft()
        leftAxis.valueFormatter = MyValueFormatter("K")
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        chart.getAxisRight().isEnabled = false
        val xLabels = chart.getXAxis()
        xLabels.position = XAxisPosition.TOP

        // chart.setDrawXLabels(false);
        // chart.setDrawYLabels(false);

        // setting data
        val l = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.formToTextSpace = 4f
        l.xEntrySpace = 6f

        // chart.setDrawLegend(false);
        setData()
    }

    private fun setData() {
        val values = ArrayList<BarEntry>()
        for (i in 0..9) {
            val mul = i + 1.toFloat()
            val val1 = (Math.random() * mul).toFloat() + mul / 3
            val val2 = (Math.random() * mul).toFloat() + mul / 3
            val val3 = (Math.random() * mul).toFloat() + mul / 3
            values.add(BarEntry(
                    i.toFloat(), floatArrayOf(val1, val2, val3)))
        }
        val set1: BarDataSet
        if (chart!!.data != null &&
                chart!!.data.dataSetCount > 0) {
            set1 = chart!!.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Statistics Vienna 2014")
            set1.setDrawIcons(false)
            set1.setColors(*colors)
            set1.stackLabels = arrayOf("Births", "Divorces", "Marriages")
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueFormatter(StackedValueFormatter(false, "", 1))
            data.setValueTextColor(Color.WHITE)
            chart!!.data = data
        }
        chart!!.setFitBars(true)
        chart!!.invalidate()
    }

    override fun saveToGallery() {
        saveToGallery(chart, "StackedBarActivity")
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        val entry = e as BarEntry
        if (entry.yVals != null) Log.i("VAL SELECTED", "Value: " + entry.yVals[h.stackIndex]) else Log.i("VAL SELECTED", "Value: " + entry.y)
    }

    override fun onNothingSelected() {}

    // have as many colors as stack-values per entry
    private val colors: IntArray
        private get() {

            // have as many colors as stack-values per entry
            val colors = IntArray(3)
            System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3)
            return colors
        }
}