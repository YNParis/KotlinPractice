package com.demos.kotlin.views.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.demos.kotlin.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


/**
 * @author yangna
 * @Description 通用的chartview
 */
public class CommonChartView extends FrameLayout {

    public static final String PIE_CHART = "pie_chart";//饼图

    /**
     * 传入参数
     */
    private int mValueTextColor;
    private float mValueTextSize;

    private int mLabelTextColor;
    private float mLabelTextSize;

    private int mGridColor;
    private float mGridWidth;

    private String mChartType;//图表类型

    private Context mContext;

    private PieChart mPieChart;

    public CommonChartView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.mContext = context;
        initAttr(attrs, defStyle);
    }

    /**
     * 从配置文件读取控件初始属性
     */
    private void initAttr(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CommonChartView, defStyle, 0);
        mChartType = a.getString(R.styleable.CommonChartView_chartType);
        mValueTextColor = a.getColor(R.styleable.CommonChartView_valueTextColor,
                getResources().getColor(R.color.colorWhite));
        mLabelTextColor = a.getColor(R.styleable.CommonChartView_labelTextColor,
                getResources().getColor(R.color.colorPrimary));
        mGridColor = a.getColor(R.styleable.CommonChartView_gridColor,
                getResources().getColor(R.color.colorPrimary));

        mLabelTextSize = a.getDimension(R.styleable.CommonChartView_labelTextSize, 15);
        mValueTextSize = a.getDimension(R.styleable.CommonChartView_valueTextSize, 15);
        mGridWidth = a.getDimension(R.styleable.CommonChartView_gridWidth, 0.5f);

        a.recycle();
    }

    /**
     * 初始化加载页面，在调用页面手动调取
     */
    public void initView(String type) {
        mChartType = type;
        switch (mChartType) {
            case PIE_CHART:
                initPieChart();
                break;

        }

    }

    /**
     * 初始化饼图
     */
    private void initPieChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.chart_pie, this, true);
        mPieChart = fatherView.findViewById(R.id.pie_chart_common);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(true);
        mPieChart.setDragDecelerationFrictionCoef(0.8f);
        //设置图表的偏移量，使得图表两边的空白处增大，如果label显示不下，可以将left和right再设置大一点
        mPieChart.setExtraOffsets(30.f, 0.f, 30.f, 0.f);

        mPieChart.setDrawHoleEnabled(true);//环形
        mPieChart.setDrawCenterText(false);//中间不绘制文字
        mPieChart.setHoleColor(Color.WHITE);

       /* mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(120);*/

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);//设置可点击效果

        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(onChartValueSelectedListener);
        //设置label颜色
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(0xFF000000);
        mPieChart.setRenderer(new CustomPieRenderer(mPieChart, mPieChart.getAnimator(), mPieChart.getViewPortHandler()));

//        mPieChart.animateY(1400, Easing.EaseInOutQuad);//动画效果
        // mPieChart.spin(2000, 0, 360);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

    }

    private OnChartValueSelectedListener onChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }
    };

    public void setData(int count, float range) {
        switch (mChartType) {
            case PIE_CHART:
                setPieData(count, range);
                break;

        }
    }

    private void setPieData(int count, float range) {
        if (mPieChart == null) return;
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + range / 5;
            entries.add(new PieEntry(value, "label " + i + "," + value));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        //slice之间间隔
        dataSet.setSliceSpace(2f);
        //选中后放大的偏移量
        dataSet.setSelectionShift(15f);

        //图标颜色
        int[] colors = getResources().getIntArray(R.array.color_charts_detail);
        dataSet.setColors(colors);

        //设置折线指示线
        dataSet.setValueLinePart1OffsetPercentage(100.f);//设置折现离内边的距离，%，设为100表示从外边开始画
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setValueLineWidth(0.7f);
        dataSet.setValueLineColor(0x73000000);

        //饼图内，显示百分比
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        PieData data = new PieData(dataSet);
        //value的样式
        data.setValueFormatter(new PercentFormatter(mPieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        mPieChart.setData(data);
        //自定义X轴标签位置

        // undo all highlights
//        chart.highlightValues(null);
        mPieChart.invalidate();
    }

}
