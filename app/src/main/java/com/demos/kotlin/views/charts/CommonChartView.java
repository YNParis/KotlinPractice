package com.demos.kotlin.views.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.demos.kotlin.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yangna
 * @Description 通用的chartView。需要传入表格的类型。
 */
public class CommonChartView extends FrameLayout {

    public static final String PIE_CHART = "pie_chart";//饼图
    public static final String LINES_CHART = "lines_chart";//折线图
    public static final String BARS_CHART = "bars_chart";//柱状图
    public static final String COMBINED_CHART = "combined_chart";//混合图
    public static final String STACKED_CHART = "stacked_chart";//叠加柱状图
    public static final String HORIZONTAL_CHART = "horizontal_chart";//水平柱状图
    public static final String CANDLE_CHART = "candle_chart";//蜡烛图

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

    private Chart currentChart;

    private PieChart mPieChart;
    private LineChart mLinesChart;
    private BarChart mBarChart;
    private CombinedChart mCombinedChart;
    private HorizontalBarChart mHorizontalChart;
    private CandleStickChart mCandleChart;

    private ValueFormatter valueFormatter;

    private int[] colors;


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
                getResources().getColor(R.color.chart_value_color));
        mLabelTextColor = a.getColor(R.styleable.CommonChartView_labelTextColor,
                getResources().getColor(R.color.chart_value_color));
        mGridColor = a.getColor(R.styleable.CommonChartView_gridColor,
                getResources().getColor(R.color.chart_grid_color));

        mLabelTextSize = a.getDimension(R.styleable.CommonChartView_labelTextSize, 15);
        mValueTextSize = a.getDimension(R.styleable.CommonChartView_valueTextSize, 15);
        mGridWidth = a.getDimension(R.styleable.CommonChartView_gridWidth, 0.5f);
        a.recycle();
        colors = getResources().getIntArray(R.array.color_charts_detail);
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
            case LINES_CHART:
                initLinesChart();
                break;
            case BARS_CHART:
            case STACKED_CHART:
                initBarChart();
                break;
            case COMBINED_CHART:
                initCombinedChart();
                break;
            case HORIZONTAL_CHART:
                initHorizontalChart();
                break;
            case CANDLE_CHART:
                initCandleChart();
                break;

        }
    }

    /**
     * 初始化蜡烛图
     */
    private void initCandleChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.activity_candlechart, this, true);
        mCandleChart = fatherView.findViewById(R.id.candle_chart);
        currentChart = mCandleChart;
        initLegend(mCandleChart.getLegend());
        initXAis(mCandleChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        mCandleChart.getAxisRight().setEnabled(false);
        initLeftYAxis(mCandleChart.getAxisLeft());
        mCandleChart.getDescription().setEnabled(false);

    }

    private void initHorizontalChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.activity_horizontalbarchart, this, true);
        mHorizontalChart = fatherView.findViewById(R.id.horizontal_chart);
        currentChart = mHorizontalChart;
        initLegend(mHorizontalChart.getLegend());
        initXAis(mHorizontalChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        mHorizontalChart.getAxisRight().setEnabled(false);
        initLeftYAxis(mHorizontalChart.getAxisLeft());
        mHorizontalChart.getAxisLeft().setDrawLabels(false);
        mHorizontalChart.getDescription().setEnabled(false);
    }

    private void initBarChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.activity_bar_chart, this, true);
        mBarChart = fatherView.findViewById(R.id.chart1);
        currentChart = mBarChart;
        mBarChart.getDescription().setEnabled(false);
        initLeftYAxis(mBarChart.getAxisLeft());
        mBarChart.getAxisRight().setEnabled(false);
        initXAis(mBarChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);

        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setFitBars(true);

    }

    private void initLeftYAxis(YAxis yAxis) {
        yAxis.setGridLineWidth(0.5f);
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yAxis.setDrawAxisLine(false);
        yAxis.setGridColor(mGridColor);
    }

    private void initRightYAxis(YAxis yAxis) {
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yAxis.setAxisMaximum(100f); // this replaces setStartAtZero(true)
        yAxis.setDrawAxisLine(false);
    }

    private void initBarStyle() {

    }

    private void initCombinedChart() {

        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.activity_combined, this, true);
        mCombinedChart = fatherView.findViewById(R.id.combined_chart);
        currentChart = mCombinedChart;
        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.setHighlightFullBarEnabled(false);
        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});
        initLegend(mCombinedChart.getLegend());
        initRightYAxis(mCombinedChart.getAxisRight());
        initLeftYAxis(mCombinedChart.getAxisLeft());
        mCombinedChart.getAxisLeft().setDrawGridLines(false);
        mCombinedChart.getAxisLeft().setAxisMaximum(100f);
        initXAis(mCombinedChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_MONTH);
    }

    private void initXAis(XAxis xAxis, String formatterType) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new CustomAxisValueFormatter(formatterType));
    }

    private void initLegend(Legend l) {
        l.setXEntrySpace(20);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
    }

    /**
     * 设置混合图的数据
     *
     * @param labels
     * @param values
     */
    public void setCombinedChart(String[] labels, List<float[]> values) {
        setCombinedChart(labels, values, false);
    }

    /**
     * 设置混合图数据
     *
     * @param labels
     * @param values
     * @param stacked 如果柱状图需要是叠加的，传true；否则，可以用上面两个参数的方法
     */
    public void setCombinedChart(String[] labels, List<float[]> values, boolean stacked) {
        if (mCombinedChart == null) return;
        CombinedData data = new CombinedData();
        data.setData(generateLineData(labels, values, false));
        if (stacked) {
            data.setData(generateStackedData(labels, values));
        } else {
            data.setData(generateBarData(labels, values));
        }
        mCombinedChart.setData(data);
        mCombinedChart.invalidate();
    }

    /**
     * 必须在initView之前调用，不然不生效
     *
     * @param valueFormatter
     */
    public void setXAxisValueFormatter(ValueFormatter valueFormatter) {
        this.valueFormatter = valueFormatter;
        if (currentChart == null) return;
        currentChart.getXAxis().setValueFormatter(valueFormatter);
    }

    /**
     * 初始化折线图
     */
    private void initLinesChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.chart_lines, this, true);
        mLinesChart = fatherView.findViewById(R.id.line_chart_common);
        currentChart = mLinesChart;
        mLinesChart.setOnChartValueSelectedListener(onChartValueSelectedListener);

        mLinesChart.setDrawGridBackground(false);
        mLinesChart.getDescription().setEnabled(false);
        mLinesChart.setDrawBorders(false);

        mLinesChart.setExtraLeftOffset(30);
        mLinesChart.setExtraRightOffset(30);

        // enable touch gestures
        mLinesChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLinesChart.setDragEnabled(true);
        mLinesChart.setScaleEnabled(true);/*缩放*/

        // if disabled, scaling can be done on x- and y-axis separately
        mLinesChart.setPinchZoom(false);

        initLegend(mLinesChart.getLegend());
        initLeftYAxis(mLinesChart.getAxisLeft());
        mLinesChart.getAxisRight().setEnabled(false);
        initXAis(mLinesChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);

        if (valueFormatter != null) {
            mLinesChart.getXAxis().setValueFormatter(valueFormatter);
        }

    }

    /**
     * 初始化饼图
     */
    private void initPieChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.chart_pie, this, true);
        mPieChart = fatherView.findViewById(R.id.pie_chart_common);
        currentChart = mPieChart;
        mPieChart.setUsePercentValues(true);//显示百分比
        mPieChart.getDescription().setEnabled(false);//表格的说明去掉
        mPieChart.setDragDecelerationFrictionCoef(0.8f);

        //设置图表的偏移量，使得图表两边的空白处增大，如果label显示不下，可以将left和right再设置大一点
        mPieChart.setExtraOffsets(30.f, 0.f, 30.f, 0.f);

        mPieChart.setDrawHoleEnabled(true);//环形
        mPieChart.setDrawCenterText(false);//中间不绘制文字
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setHoleRadius(60f);
        /*设置内边的透明度效果，0为没有*/
        mPieChart.setTransparentCircleRadius(0f);
        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
//        mPieChart.setHighlightPerTapEnabled(true);//设置可点击效果

        //设置label颜色
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(mLabelTextColor);
        mPieChart.setRenderer(new CustomPieRenderer(mPieChart, mPieChart.getAnimator(), mPieChart.getViewPortHandler()));

        //设置不绘制图例
        Legend l = mPieChart.getLegend();
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
//                setPieData(count, range);
                break;
            case LINES_CHART:
//                setLinesChartData(count, range);
                break;

        }
    }

    /**
     * 设置饼图数据
     *
     * @param labels 标签
     * @param values 值
     */
    public void setPieData(String[] labels, List<Float> values) {
        if (mPieChart == null) return;
        int count = Math.min(labels.length, values.size());
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(values.get(i), labels[i] + "," + values.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        //slice之间间隔
        dataSet.setSliceSpace(2f);
        //选中后放大的偏移量
        dataSet.setSelectionShift(15f);

        //图表颜色
        dataSet.setColors(colors);

        //设置折线指示线
        dataSet.setValueLinePart1OffsetPercentage(100.f);//设置折现离内边的距离，%，设为100表示从外边开始画
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setValueLineWidth(0.7f);
        dataSet.setValueLineColor(mLabelTextColor);

        //饼图内，显示百分比
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        //饼图外，显示label
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        //value的样式
        data.setValueFormatter(new PercentFormatter(mPieChart));//显示百分数
        data.setValueTextSize(mValueTextSize);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.invalidate();
    }

    public void setLinesChartData(String[] labels, List<float[]> values) {
        setLinesChartData(labels, values, false);
    }

    public void setLinesChartData(String[] labels, List<float[]> values, boolean fill) {
        if (mLinesChart == null) return;
        mLinesChart.setData(generateLineData(labels, values, fill));
        mLinesChart.invalidate();
    }

    /**
     * @param labels
     * @param valuesList
     */
    public void setBarsData(String[] labels, List<float[]> valuesList) {
        if (mBarChart == null) return;
        mBarChart.setData(generateBarData(labels, valuesList));
        mBarChart.invalidate();
    }

    /**
     * 折线图，初始化数据
     *
     * @return
     */
    private LineData generateLineData(String[] labels, List<float[]> values, boolean fill) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        /*values.size条折线*/
        for (int z = 0; z < values.size(); z++) {
            ArrayList<Entry> entry = new ArrayList<>();
            float[] each = values.get(z);
            for (int x = 0; x < each.length; x++) {
                entry.add(new Entry(x, each[x]));
            }
            LineDataSet d = new LineDataSet(entry, labels[z]);
            d.setLineWidth(2.5f);
            d.setDrawCircles(false);
            d.setDrawValues(false);
            d.setDrawFilled(fill);
            int color = colors[z % colors.length];
            d.setColor(color);
            d.setFillColor(color);
            d.setLabel(labels[z]);/*图例*/
            dataSets.add(d);
        }
        return new LineData(dataSets);
    }

    private BarData generateBarData(String[] labels, List<float[]> valuesList) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        /*values.size条折线*/
        for (int z = 0; z < valuesList.size(); z++) {
            ArrayList<BarEntry> entry = new ArrayList<>();
            float[] eachBar = valuesList.get(z);
            for (int x = 0; x < eachBar.length; x++) {
                entry.add(new BarEntry(x, eachBar[x]));
            }
            BarDataSet d = new BarDataSet(entry, labels[z]);
            d.setAxisDependency(YAxis.AxisDependency.LEFT);
            d.setDrawValues(false);
            d.setColor(colors[z % colors.length]);
            d.setLabel(labels[z]);/*图例*/
            dataSets.add(d);
        }

        BarData d = new BarData(dataSets);

        float groupSpace = 0.4f;
        float barSpace = 0.01f;
        float barWidth = (1 - groupSpace) / valuesList.size() - barSpace;

        d.setBarWidth(barWidth);
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    private CandleData generateCandleData(String[] labels, List<float[]> valuesList) {
        ArrayList<CandleEntry> values = new ArrayList<>();
        ArrayList<CandleEntry> values2 = new ArrayList<>();

        int baseData = 15;/*上一年的结余*/

        int[] val1 = new int[12];
        int[] val2 = new int[12];
        int[] base = new int[12];

        for (int i = 0; i < 12; i++) {
            val1[i] = new Random().nextInt(100);
            val2[i] = new Random().nextInt(100);
        }
        /*第一个，low=baseData，high=low+value；后面的low=high-value*/
        for (int i = 0; i < 12; i++) {
            int high, low1, low2;
            if (i == 0) {
                low1 = baseData;
            } else {
                low1 = base[i - 1];
            }
            high = val1[i] + low1;
            low2 = high - val2[i];
            base[i] = low2;
            values.add(new CandleEntry(i, high, low1, low1, high));
            values2.add(new CandleEntry(i, high, low2, low2, high));
        }

        CandleDataSet set1 = new CandleDataSet(values, "Data Set");
        CandleDataSet set2 = new CandleDataSet(values2, "Data Set 2");
        set1.setDrawValues(false);
        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setIncreasingColor(colors[0]);
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setBarSpace(0f);
        //set1.setHighlightLineWidth(1f);

        set2.setDrawIcons(false);
        set2.setDrawValues(false);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setIncreasingColor(colors[1]);
        set2.setIncreasingPaintStyle(Paint.Style.FILL);
        set2.setBarSpace(0.25f);

        List<ICandleDataSet> dataSets = new ArrayList<>();
        dataSets.add(set2);
        dataSets.add(set1);

        return new CandleData(dataSets);
    }

    /**
     * 叠加的数据
     *
     * @param labels     每个色块代表什么
     * @param valuesList 有几个bar
     * @return
     */
    private BarData generateStackedData(String[] labels, List<float[]> valuesList) {
        ArrayList<BarEntry> entry = new ArrayList<>();
        for (int z = 0; z < labels.length; z++) {
            float[] eachBar = new float[valuesList.size()];
            for (int i = 0; i < valuesList.size(); i++) {
                eachBar[i] = valuesList.get(i)[z];
            }
            entry.add(new BarEntry(z, eachBar));
        }
        BarDataSet dataSet = new BarDataSet(entry, "");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setDrawValues(false);
        int[] color = new int[valuesList.size()];
        System.arraycopy(colors, 0, color, 0, valuesList.size());
        dataSet.setColors(color);
        dataSet.setStackLabels(labels);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.5f);
        return data;
    }

    /**
     * @param labels
     * @param valuesList
     */
    public void setStackedData(String[] labels, List<float[]> valuesList) {
        if (mBarChart == null) return;
        initLegend(mBarChart.getLegend());
        mBarChart.getXAxis().setAxisMinimum(-1);
        mBarChart.setData(generateStackedData(labels, valuesList));
        mBarChart.invalidate();
    }


    public void setHorizontalData(String[] label, List<float[]> valuesList) {
        if (mHorizontalChart == null) return;
        mHorizontalChart.setData(generateBarData(label, valuesList));
        mHorizontalChart.invalidate();
    }

    public void setCandleData(String[] label, List<float[]> valuesList) {
        if (mCandleChart == null) return;
        mCandleChart.setData(generateCandleData(label, valuesList));
        mCandleChart.setRenderer(new CustomCandleRender(mCandleChart, mCandleChart.getAnimator(), mCandleChart.getViewPortHandler()));
        mCandleChart.invalidate();
    }
}
