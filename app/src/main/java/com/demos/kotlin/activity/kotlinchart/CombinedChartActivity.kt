package com.demos.kotlin.activity.kotlinchart

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.demos.kotlin.databinding.ActivityCombinedBinding
import com.demos.kotlin.views.charts.CustomAxisValueFormatter
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

/**
 * 混合图
 */
class CombinedChartActivity : DemoBase() {
    private val count = 12
    private lateinit var colors: IntArray
    private lateinit var binding: ActivityCombinedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityCombinedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "CombinedChartActivity"
        colors = resources.getIntArray(R.array.color_charts_detail)
        binding.combinedChart.getDescription().isEnabled = false
        binding.combinedChart.setBackgroundColor(Color.WHITE)
        binding.combinedChart.setDrawBarShadow(false)
        binding.combinedChart.setHighlightFullBarEnabled(false)

        // draw bars behind lines
        binding.combinedChart.setDrawOrder(arrayOf(
                DrawOrder.BAR, DrawOrder.LINE
        ))
        val l = binding.combinedChart.getLegend()
        l.isWordWrapEnabled = true
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        val rightAxis = binding.combinedChart.getAxisRight()
        rightAxis.setDrawGridLines(true)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.axisMaximum = 100f // this replaces setStartAtZero(true)
        rightAxis.setDrawAxisLine(false)
        val leftAxis = binding.combinedChart.getAxisLeft()
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        leftAxis.axisMaximum = 100f // this replaces setStartAtZero(true)
        leftAxis.setDrawAxisLine(false)
        val xAxis = binding.combinedChart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.setDrawGridLines(false)
        //        xAxis.setGranularity(1f);
        xAxis.valueFormatter = CustomAxisValueFormatter(CustomAxisValueFormatter.X_AXIS_MONTH)
        val data = CombinedData()
        data.setData(generateLineData())
        data.setData(generateBarData())
        xAxis.axisMaximum = data.xMax + 0.25f
        binding.combinedChart.setData(data)
        binding.combinedChart.invalidate()
    }

    /**
     * 折线图，初始化数据
     *
     * @return
     */
    private fun generateLineData(): LineData {
        val d = LineData()
        for (i in 0..2) {
            val entries = ArrayList<Entry>()
            for (index in 0 until count) entries.add(Entry(index + 0.5f, getRandom(100f, 0f)))
            val set = LineDataSet(entries, "Line DataSet $i")
            set.color = colors[i]
            set.lineWidth = 2.5f
            set.setDrawCircles(false)
            set.axisDependency = YAxis.AxisDependency.RIGHT
            d.addDataSet(set)
        }
        return d
    }

    private fun generateBarData(): BarData {
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        val entries3 = ArrayList<BarEntry>()
        for (index in 0 until count) {
            entries1.add(BarEntry(0f, getRandom(100f, 0f)))
            entries3.add(BarEntry(0f, getRandom(100f, 0f)))

            // stacked
            entries2.add(BarEntry(0f, floatArrayOf(getRandom(40f, 0f), getRandom(30f, 0f), getRandom(30f, 0f))))
        }
        val set1 = BarDataSet(entries1, "Bar 1")
        set1.color = colors[0]
        set1.axisDependency = YAxis.AxisDependency.LEFT
        val set2 = BarDataSet(entries2, "")
        set2.stackLabels = arrayOf("Stack 1", "Stack 2", "Stack 3")
        set2.setColors(colors[1], colors[2], colors[3])
        set2.axisDependency = YAxis.AxisDependency.LEFT
        val set3 = BarDataSet(entries3, "Bar 3")
        set3.color = colors[4]
        set3.axisDependency = YAxis.AxisDependency.LEFT
        val groupSpace = 0.4f
        val barSpace = 0.01f // x2 dataset
        val barWidth = 0.19f // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
        val d = BarData(set1, set2, set3)
        d.barWidth = barWidth
        // make this BarData object grouped
        d.groupBars(0f, groupSpace, barSpace) // start at x = 0
        return d
    }

    private fun generateScatterData(): ScatterData {
        val d = ScatterData()
        val entries = ArrayList<Entry>()
        var index = 0f
        while (index < count) {
            entries.add(Entry(index + 0.25f, getRandom(10f, 55f)))
            index += 0.5f
        }
        val set = ScatterDataSet(entries, "Scatter DataSet")
        set.setColors(*ColorTemplate.MATERIAL_COLORS)
        set.scatterShapeSize = 7.5f
        set.setDrawValues(false)
        set.valueTextSize = 10f
        d.addDataSet(set)
        return d
    }

    private fun generateCandleData(): CandleData {
        val d = CandleData()
        val entries = ArrayList<CandleEntry>()
        var index = 0
        while (index < count) {
            entries.add(CandleEntry(index + 1f, 90f, 70f, 85f, 75f))
            index += 2
        }
        val set = CandleDataSet(entries, "Candle DataSet")
        set.decreasingColor = Color.rgb(142, 150, 175)
        set.shadowColor = Color.DKGRAY
        set.barSpace = 0.3f
        set.valueTextSize = 10f
        set.setDrawValues(false)
        d.addDataSet(set)
        return d
    }

    private fun generateBubbleData(): BubbleData {
        val bd = BubbleData()
        val entries = ArrayList<BubbleEntry>()
        for (index in 0 until count) {
            val y = getRandom(10f, 105f)
            val size = getRandom(100f, 105f)
            entries.add(BubbleEntry(index + 0.5f, y, size))
        }
        val set = BubbleDataSet(entries, "Bubble DataSet")
        set.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        set.valueTextSize = 10f
        set.valueTextColor = Color.WHITE
        set.highlightCircleWidth = 1.5f
        set.setDrawValues(true)
        bd.addDataSet(set)
        return bd
    }

    public override fun saveToGallery() { /* Intentionally left empty */
    }
}