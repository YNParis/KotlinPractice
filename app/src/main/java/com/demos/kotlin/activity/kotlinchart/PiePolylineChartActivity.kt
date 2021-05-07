package com.demos.kotlin.activity.kotlinchart

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import com.demos.kotlin.R
import com.demos.kotlin.activity.DemoBase
import com.demos.kotlin.databinding.ActivityPieChartBinding
import com.demos.kotlin.views.charts.CustomPieRenderer
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.*

/**
 * 饼图
 */
class PiePolylineChartActivity : DemoBase(), OnChartValueSelectedListener {
    private lateinit var binding: ActivityPieChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPieChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "PiePolylineChartActivity"
        initChart()
        setData(10, 20f)
    }

    private fun initChart() {

        binding.chart1.setUsePercentValues(true)
        binding.chart1.getDescription().isEnabled = true
        binding.chart1.setDragDecelerationFrictionCoef(0.8f)
        //设置图表的偏移量，使得图表两边的空白处增大，如果label显示不下，可以将left和right再设置大一点
        binding.chart1.setExtraOffsets(30f, 0f, 30f, 0f)
        binding.chart1.setDrawHoleEnabled(true) //环形
        binding.chart1.setDrawCenterText(false) //中间不绘制文字
        binding.chart1.setHoleColor(Color.WHITE)

        /* binding.chart1.setTransparentCircleColor(Color.WHITE);
        binding.chart1.setTransparentCircleAlpha(120);*/
        binding.chart1.setHoleRadius(58f)
        binding.chart1.setTransparentCircleRadius(61f)
        binding.chart1.setRotationAngle(0f)
        // enable rotation of the chart by touch
        binding.chart1.setRotationEnabled(true)
        binding.chart1.setHighlightPerTapEnabled(true) //设置可点击效果

        // add a selection listener
        binding.chart1.setOnChartValueSelectedListener(this)
        //设置label颜色
        binding.chart1.setDrawEntryLabels(true)
        binding.chart1.setEntryLabelColor(-0x1000000)
        binding.chart1.setRenderer(CustomPieRenderer(binding.chart1, binding.chart1.getAnimator(), binding.chart1.getViewPortHandler()))

//        binding.chart1.animateY(1400, Easing.EaseInOutQuad);//动画效果
        // binding.chart1.spin(2000, 0, 360);
        val l = binding.chart1.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            val value = (Math.random() * range).toFloat() + range / 5
            entries.add(PieEntry(value, parties[i % parties.size].toString() + "," + value))
        }
        val dataSet = PieDataSet(entries, "Election Results")
        //slice之间间隔
        dataSet.sliceSpace = 2f
        //选中后放大的偏移量
        dataSet.selectionShift = 15f

        //图标颜色
        val colors = resources.getIntArray(R.array.color_charts_detail)
        dataSet.setColors(*colors)

        //设置折线指示线
        dataSet.valueLinePart1OffsetPercentage = 100f //设置折现离内边的距离，%，设为100表示从外边开始画
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.5f
        dataSet.valueLineWidth = 0.7f
        dataSet.valueLineColor = 0x73000000

        //饼图内，显示百分比
        dataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data = PieData(dataSet)
        //value的样式
        data.setValueFormatter(PercentFormatter(binding.chart1))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        binding.chart1!!.data = data
        //自定义X轴标签位置

        // undo all highlights
//        binding.chart1.highlightValues(null);
        binding.chart1!!.invalidate()
    }

    override fun saveToGallery() {
        saveToGallery(binding.chart1, "PiePolylineChartActivity")
    }

    private fun generateLabelSpannableText(value: String, label: String): SpannableString {
        val s = SpannableString("""
    $label
    $value
    """.trimIndent())
        s.setSpan(ForegroundColorSpan(-0x1000000), 0, s.length, 0)
        s.setSpan(StyleSpan(Typeface.BOLD), label.length, s.length, 0)
        return s
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        if (e == null) return
        Log.i("VAL SELECTED",
                "Value: " + e.y + ", xIndex: " + e.x
                        + ", DataSet index: " + h.dataSetIndex)
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }
}