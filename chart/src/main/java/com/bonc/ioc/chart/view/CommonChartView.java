package com.bonc.ioc.chart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bonc.ioc.chart.R;
import com.bonc.ioc.chart.adapter.PieChartLegendAdapter;
import com.bonc.ioc.chart.bean.CheckboxDataBean;
import com.bonc.ioc.chart.utils.CustomAxisValueFormatter;
import com.bonc.ioc.chart.utils.CustomCandleRender;
import com.bonc.ioc.chart.utils.CustomPercentValueFormatter;
import com.bonc.ioc.chart.utils.CustomPieRenderer3;
import com.bonc.ioc.chart.utils.NumberUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的chartView。需要传入表格的类型。
 *
 * @author yangna
 */
public class CommonChartView extends FrameLayout implements OnChartValueSelectedListener {

    public static final String PIE_CHART = "pie_chart";//饼图
    public static final String PIE_CHART_PROFILE = "pie_chart_profile";//饼图，人员画像首页
    public static final String PIE_CHART_DRAW_CENTER = "pie_chart_draw_center";//中心有文字的环形图
    public static final String PIE_CHART_DRAW_CENTER_NO_PERCENT = "pie_chart_draw_center_no_percent";//中心有文字的环形图，不绘制百分比
    public static final String LINES_CHART = "lines_chart";//折线图
    public static final String BARS_CHART = "bars_chart";//柱状图
    public static final String BARS_CHART_MATCH_PARENT = "bars_chart_match_parent";//柱状图，不是400dp
    public static final String BARS_CHART_WITH_SEEK_BAR = "bars_chart_with_seek_bar";//柱状图，下方有进度条
    public static final String BARS_CHART_WITH_CHECKBOX = "bars_chart_with_checkbox";//柱状图，上方有多选框
    public static final String COMBINED_CHART = "combined_chart";//混合图
    public static final String STACKED_CHART = "stacked_chart";//叠加柱状图
    public static final String STACKED_CHART_CHECKBOX = "stacked_chart_with_checkbox";//叠加柱状图，上方有多选框
    public static final String STACKED_CHART_WARN_LINE = "stacked_chart_with_warn_line";//叠加柱状图，上方有警戒线
    public static final String HORIZONTAL_CHART = "horizontal_chart";//水平柱状图
    public static final String CANDLE_CHART = "candle_chart";//蜡烛图
    public static final String NEGATIVE_CHART = "negative_chart";//有负值的barchart
    public static final String BARS_CHART_MIRROR = "bars_mirror_chart";//镜像的柱状图
    private static final int PIE_CHART_VALUE_MOST_SIZE = 0;
    private float extraBottomOffset = 5;
    private MarkerView mv;
    private int seekBarRangeStart = 0;
    private int seekBarRangeEnd = 0;
    private int xAxisLabelCount = 20;

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
    private TextView xValueNameView;
    private TextView tvBottomRightUnit;
    private TextView tvLeftUnit;
    private TextView tvRightUnit;

    private ValueFormatter valueFormatter;
    private LinearLayout pieParentView;

    private int[] colors;
    private PieChartLegendAdapter leftAdapter;
    private PieChartLegendAdapter rightAdapter;
    //    private RecyclerView mCheckbox;
    private RelativeLayout seekBarView;
    private OnCheckboxItemChangedListener onCheckboxItemChangedListener;
    private LineChart lineChart;//seekbar的linechart
    private OnChartItemCheckedListener onChartItemCheckedListener;
    private List<String> leftValue = new ArrayList<>();
    private List<String> rightValue = new ArrayList<>();

    private long shownMaxLeft;
    private long shownMaxRight;
    private long shownMinLeft;
    private long shownMinRight;
    private List<Integer> selectedIndex;


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

        mLabelTextSize = a.getDimension(R.styleable.CommonChartView_labelTextSize, 11);
        mValueTextSize = a.getDimension(R.styleable.CommonChartView_valueTextSize, 11);
        mGridWidth = a.getDimension(R.styleable.CommonChartView_gridWidth, 0.5f);
        a.recycle();
        colors = getResources().getIntArray(R.array.color_charts_detail);
    }

    /**
     * 初始化加载页面，在调用页面手动调取
     */
    public void initView(String type) {
        mChartType = type;
        removeAllViews();
        switch (mChartType) {
            case PIE_CHART:
            case PIE_CHART_PROFILE:
            case PIE_CHART_DRAW_CENTER:
            case PIE_CHART_DRAW_CENTER_NO_PERCENT:
                initPieChart();
                break;
            case LINES_CHART:
                initLinesChart();
                break;
            case BARS_CHART:
            case STACKED_CHART:
            case BARS_CHART_WITH_CHECKBOX:
            case BARS_CHART_WITH_SEEK_BAR:
            case BARS_CHART_MATCH_PARENT:
            case STACKED_CHART_CHECKBOX:
            case STACKED_CHART_WARN_LINE:
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
            case NEGATIVE_CHART:
                initNegativeChart();
                break;
            case BARS_CHART_MIRROR:
                break;
        }
        if (currentChart != null) {
            currentChart.setLogEnabled(true);
            currentChart.setNoDataText("暂无数据");
        }
    }

    /**
     * 初始化蜡烛图
     */
    private void initCandleChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_candle, this, true);
        mCandleChart = fatherView.findViewById(R.id.candle_chart);
        currentChart = mCandleChart;
        /*mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv); // Set the marker to the chart*/
        mCandleChart.setOnChartValueSelectedListener(this);
        initLegend(mCandleChart.getLegend());
        initXAxis(mCandleChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        mCandleChart.getAxisRight().setEnabled(false);
        initLeftYAxis(mCandleChart.getAxisLeft());
        mCandleChart.setScaleEnabled(false);
        mCandleChart.getDescription().setEnabled(false);
        mCandleChart.setExtraBottomOffset(extraBottomOffset);

    }

    private void initHorizontalChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_bar_horizontal, this, true);
        mHorizontalChart = fatherView.findViewById(R.id.horizontal_chart);
        xValueNameView = fatherView.findViewById(R.id.x_value_name);
        currentChart = mHorizontalChart;
        /*mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv); // Set the marker to the chart*/
        initLegend(mHorizontalChart.getLegend());
        mHorizontalChart.setOnChartValueSelectedListener(this);
        currentChart.setExtraRightOffset(100);
        initXAxis(mHorizontalChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        mHorizontalChart.getAxisRight().setEnabled(false);
        initLeftYAxis(mHorizontalChart.getAxisLeft());
        mHorizontalChart.getAxisLeft().setDrawLabels(false);
        mHorizontalChart.getDescription().setEnabled(false);
        mHorizontalChart.setScaleEnabled(false);
        mHorizontalChart.setDragEnabled(false);
    }

    private void initBarChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_bar, this, true);
        mBarChart = fatherView.findViewById(R.id.chart_bar);
        tvBottomRightUnit = fatherView.findViewById(R.id.bottom_right_unit);
        tvLeftUnit = fatherView.findViewById(R.id.left_unit);
        tvRightUnit = fatherView.findViewById(R.id.right_unit);
        currentChart = mBarChart;
        mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv);
        mBarChart.setOnChartValueSelectedListener(this);
        initLeftYAxis(mBarChart.getAxisLeft());
        mBarChart.getAxisRight().setEnabled(false);
        initXAxis(mBarChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
//        mBarChart.setVisibleXRangeMaximum(20);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setScaleEnabled(false);
        mBarChart.setDragEnabled(false);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setFitBars(true);
        mBarChart.getAxisLeft().setDrawLabels(true);
        mBarChart.setExtraBottomOffset(extraBottomOffset);
        initLegend(mBarChart.getLegend());

    }

    public void setUnit(String leftUnit, String rightUnit, String bottomUnit) {
        if (tvLeftUnit != null) tvLeftUnit.setText(leftUnit);
        if (tvRightUnit != null) tvRightUnit.setText(rightUnit);
        if (tvBottomRightUnit != null) tvBottomRightUnit.setText(bottomUnit);
    }

    /**
     * 有负值的barchart
     */
    private void initNegativeChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_negatvie, this, true);
        mBarChart = fatherView.findViewById(R.id.negative_chart);
        tvBottomRightUnit = fatherView.findViewById(R.id.bottom_right_unit);
        tvLeftUnit = fatherView.findViewById(R.id.left_unit);
        tvRightUnit = fatherView.findViewById(R.id.right_unit);
        currentChart = mBarChart;
        /*mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv); // Set the marker to the chart*/
        mBarChart.setOnChartValueSelectedListener(this);
        initLeftYAxis(mBarChart.getAxisLeft());
        mBarChart.getAxisRight().setEnabled(false);
        initXAxis(mBarChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setScaleEnabled(false);
        mBarChart.setDragEnabled(false);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setFitBars(true);
        mBarChart.getAxisLeft().setDrawLabels(true);
        mBarChart.setExtraBottomOffset(extraBottomOffset);
    }

    public void setDescription(String description) {
        if (currentChart == null) return;
        Description description1 = new Description();
        description1.setTextSize(mLabelTextSize);
        description1.setTextColor(mLabelTextColor);
        description1.setPosition(0, 30);
        description1.setTextAlign(Paint.Align.LEFT);
        description1.setText(description);
        currentChart.setDescription(description1);
    }

    private void initLeftYAxis(YAxis yAxis) {
        yAxis.setGridLineWidth(0.5f);
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yAxis.setAxisMaximum(100f); // this replaces setStartAtZero(true)
        yAxis.setDrawAxisLine(false);
        yAxis.setZeroLineWidth(0.5f);
        yAxis.setGridColor(mGridColor);
        yAxis.setTextSize(mLabelTextSize);
        yAxis.setTextColor(mLabelTextColor);
        yAxis.setLabelCount(6, true);
//        yAxis.setGranularity(1f);//禁止放大后标签重绘
        yAxis.setDrawZeroLine(true);
    }

    private void initRightYAxis(YAxis yAxis) {
        yAxis.setGridLineWidth(1f);
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yAxis.setAxisMaximum(100f); // this replaces setStartAtZero(true)
        yAxis.setDrawAxisLine(false);
        yAxis.setZeroLineWidth(1f);
        yAxis.setGridColor(mGridColor);
        yAxis.setTextSize(mLabelTextSize);
        yAxis.setTextColor(mLabelTextColor);
        yAxis.setLabelCount(6, true);
        yAxis.setGranularity(1f);//禁止放大后标签重绘
        yAxis.setDrawZeroLine(true);
    }

    private void initCombinedChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_combined, this, true);
        mCombinedChart = fatherView.findViewById(R.id.combined_chart);
        tvBottomRightUnit = fatherView.findViewById(R.id.bottom_right_unit);
        tvLeftUnit = fatherView.findViewById(R.id.left_unit);
        tvRightUnit = fatherView.findViewById(R.id.right_unit);
        currentChart = mCombinedChart;
        mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv); // Set the marker to the chart
        mCombinedChart.setOnChartValueSelectedListener(this);
        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.setHighlightFullBarEnabled(false);
        mCombinedChart.setScaleEnabled(false);
        mCombinedChart.setDragEnabled(false);
        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});
        initLegend(mCombinedChart.getLegend());
        initRightYAxis(mCombinedChart.getAxisRight());
        initLeftYAxis(mCombinedChart.getAxisLeft());
        mCombinedChart.getAxisLeft().setDrawGridLines(false);
        mCombinedChart.getAxisLeft().setAxisMaximum(100f);
        initXAxis(mCombinedChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_MONTH);
        mCombinedChart.setExtraBottomOffset(extraBottomOffset);
    }

    private void initXAxis(XAxis xAxis, String formatterType) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(mLabelTextSize);
        xAxis.setTextColor(mLabelTextColor);
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
    }

    private void initLegend(Legend l) {
        l.setXEntrySpace(10);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextSize(mLabelTextSize);
        l.setTextColor(mLabelTextColor);
        l.setYOffset(20);
    }

    /**
     * 设置混合图的数据
     *
     * @param labels
     * @param lineValues 折线图数据
     * @param barValues  柱状图数据
     */
    public void setCombinedChart(List<String> xValue, double minValueFirst, double maxValueFirst, List<String> labels, List<List<Double>> barValues, double minValueSecond, double maxValueSecond, List<String> lineLabels, List<List<Double>> lineValues) {
        setCombinedChart(xValue, minValueFirst, maxValueFirst, labels, barValues, minValueSecond, maxValueSecond, lineLabels, lineValues, false);
    }

    /**
     * @param xValue     横坐标的值
     * @param labels     柱状图的label
     * @param barValues  柱状图的数据
     * @param lineLabels 折线图的label
     * @param lineValues 折线图的数据
     * @param stacked    如果柱状图需要是叠加的，传true；否则，可以用上面两个参数的方法
     */
    public void setCombinedChart(final List<String> xValue, double minValueFirst, double maxValueFirst, List<String> labels, List<List<Double>> barValues, double minValueSecond, double maxValueSecond, List<String> lineLabels, List<List<Double>> lineValues, boolean stacked) {
        if (mCombinedChart == null) return;
        if ((lineValues == null || lineValues.isEmpty()) && (barValues == null || barValues.isEmpty())) {
            dataNull();
            return;
        }
        if (NumberUtils.doubleEquals(maxValueFirst, 0)) {
            maxValueFirst = 100;
        }
        if (NumberUtils.doubleEquals(maxValueSecond, 0)) {
            maxValueSecond = 100;
        }
        CombinedData data = new CombinedData();
        if (barValues != null) {
            int[] color = new int[labels.size()];
            System.arraycopy(colors, 0, color, 0, labels.size());
            if (stacked) {
                maxValueFirst = 0;
                for (int z = 0; z < xValue.size(); z++) {
                    double m = 0;
                    for (int i = 0; i < barValues.size(); i++) {
                        m += barValues.get(i).get(z);
                    }
                    maxValueFirst = Math.max(m, maxValueFirst);
                }
                data.setData(generateStackedData(maxValueFirst, labels, barValues, color));
            } else {
                data.setData(generateBarData(minValueFirst, maxValueFirst, labels, barValues, false, color));
            }
        }
        mCombinedChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(maxValueFirst, minValueFirst, true));
        mCombinedChart.getAxisRight().setValueFormatter(getYAxisValueFormatter(maxValueSecond, minValueSecond, false));
        int size = barValues == null ? 0 : barValues.size();
        if (lineValues != null) {
            data.setData(generateLineData(minValueSecond, maxValueSecond, lineLabels, lineValues, false, size, false));
        }
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        mCombinedChart.getAxisLeft().setDrawLabels(data.getBarData() != null);
        mCombinedChart.getAxisRight().setDrawLabels(data.getLineData() != null);
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
        Log.e("tag", "currentChart.getXAxis().mLabelRotatedWidth------------" + currentChart.getXAxis().mLabelRotatedWidth);
    }

    /**
     * 必须在initView之前调用，不然不生效
     *
     * @param xValue 横坐标刻度
     */
    public void setXAxisValueFormatter(final List<String> xValue) {
        if (xValue == null) return;
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value >= 0 && value < xValue.size()) {
                    return xValue.get((((int) value)));
                }
                return "";
            }
        };
        setXAxisValueFormatter(valueFormatter);
    }

    /**
     * 必须在initView之前调用，不然不生效
     *
     * @param xValue 横坐标刻度
     */
    public void setXAxisRange(final List<String> xValue) {
        setXAxisLabelRotation(xValue);
        if (xValue == null) return;
        if (currentChart == null) return;
        currentChart.getXAxis().setAxisMinimum(-1);
        currentChart.getXAxis().setAxisMaximum(xValue.size());
        currentChart.getXAxis().setLabelCount(xValue.size() + 2, true);
    }

    /**
     * 必须在initView之前调用，不然不生效
     *
     * @param xValue 横坐标刻度
     */
    public void setXAxisRange(final List<String> xValue, Chart chart) {
        setXAxisLabelRotation(xValue);
        if (xValue == null) return;
        if (chart == null) return;
        chart.getXAxis().setAxisMinimum(-1);
        chart.getXAxis().setAxisMaximum(xValue.size());
        chart.getXAxis().setLabelCount(xValue.size() + 2, true);
    }

    public void setXAxisLabelRotation(List<String> xValue) {
        if (xValue == null || xValue.isEmpty()) return;
        if (currentChart == null) return;
        int maxLength = 0;
        boolean isNumber = true;
        for (String s : xValue) {
            maxLength = Math.max(s.length(), maxLength);
            if (isNumber) {
                try {
                    Integer.parseInt(s);
                } catch (Exception e) {
                    /*解析不成数字*/
                    e.printStackTrace();
                    isNumber = false;
                }
            }
        }
        if (isNumber) {
            return;
        }
        if (maxLength * xValue.size() > 20) {
            currentChart.getXAxis().setLabelRotationAngle(-35);
        }
    }

    /**
     * @param max 纵坐标最大值
     */
    public ValueFormatter getYAxisValueFormatter(final double max, final double min, final boolean left) {
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                long scale = getScale(min, max, left);
                long shownMin = left ? shownMinLeft : shownMinRight;
                long y = shownMin + scale * ((long) value) * 5 / 100;
                return NumberUtils.getValueByUnit(String.valueOf(y), "个");
            }

            @Override
            public String getBarLabel(BarEntry barEntry) {
                long scale = getScale(min, max, left);
                long shownMin = left ? shownMinLeft : shownMinRight;
                long y = shownMin + scale * ((long) barEntry.getY()) * 5 / 100;
                return NumberUtils.getValueByUnit(String.valueOf(y), "个");
            }
        };
    }

    public ValueFormatter getYAxisValueFormatter(final double max, final double min, final boolean left, final String unit) {
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                long scale = getScale(min, max, left);
                long shownMin = left ? shownMinLeft : shownMinRight;
                double y = shownMin + scale * value * 5 / 100;
                return NumberUtils.getValueByUnit(String.valueOf(y), "个");
            }

            @Override
            public String getBarLabel(BarEntry barEntry) {
                long scale = getScale(min, max, left);
                long shownMin = left ? shownMinLeft : shownMinRight;
                double y = shownMin + scale * barEntry.getY() * 5 / 100;
                return NumberUtils.getValueByUnit(String.valueOf(y), unit);
            }
        };
    }

    /**
     * 初始化折线图
     */
    private void initLinesChart() {
        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_lines, this, true);
        mLinesChart = fatherView.findViewById(R.id.line_chart_common);
        tvBottomRightUnit = fatherView.findViewById(R.id.bottom_right_unit);
        tvLeftUnit = fatherView.findViewById(R.id.left_unit);
        tvRightUnit = fatherView.findViewById(R.id.right_unit);
        currentChart = mLinesChart;
        mv = new CombinedMarkerView(mContext);
        mv.setChartView(currentChart); // For bounds control
        currentChart.setMarker(mv); // Set the marker to the chart
        mLinesChart.setOnChartValueSelectedListener(this);
        mLinesChart.setDrawGridBackground(false);
        mLinesChart.getDescription().setEnabled(false);
        mLinesChart.setDrawBorders(false);

        // enable touch gestures
        mLinesChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLinesChart.setDragEnabled(false);
        mLinesChart.setScaleEnabled(false);/*缩放*/

        // if disabled, scaling can be done on x- and y-axis separately

        initLegend(mLinesChart.getLegend());
        initLeftYAxis(mLinesChart.getAxisLeft());
        mLinesChart.getAxisRight().setEnabled(false);
        initXAxis(mLinesChart.getXAxis(), CustomAxisValueFormatter.X_AXIS_INT);

        if (valueFormatter != null) {
            mLinesChart.getXAxis().setValueFormatter(valueFormatter);
        }
        mLinesChart.setExtraBottomOffset(extraBottomOffset);
    }

    /**
     * 初始化饼图
     */
    private void initPieChart() {

        View fatherView = LayoutInflater.from(mContext).inflate(R.layout.layout_chart_pie, this, true);
        pieParentView = fatherView.findViewById(R.id.pie_parent_view);
        mPieChart = fatherView.findViewById(R.id.pie_chart_common);
        currentChart = mPieChart;
        mPieChart.getDescription().setEnabled(false);//表格的说明去掉
        mPieChart.setDragDecelerationFrictionCoef(0.8f);
        mPieChart.setOnChartValueSelectedListener(this);

        //设置图表的偏移量，使得图表两边的空白处增大，如果label显示不下，可以将left和right再设置大一点
        mPieChart.setExtraOffsets(0, 20.f, 0, 20.f);

        mPieChart.setDrawHoleEnabled(true);//环形
        mPieChart.setDrawCenterText(false);//中间不绘制文字
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setHoleRadius(60f);
        mPieChart.setRotationEnabled(false);
        /*设置内边的透明度效果，0为没有*/
        mPieChart.setTransparentCircleRadius(0f);
        mPieChart.setRenderer(new CustomPieRenderer3(mPieChart, mPieChart.getAnimator(), mPieChart.getViewPortHandler()));
        //设置不绘制图例
        mPieChart.getLegend().setEnabled(false);
        if (!mChartType.equals(PIE_CHART_PROFILE)) {
            mv = new PieMarkerView(mContext);
            mv.setChartView(mPieChart); // For bounds control
            mPieChart.setMarker(mv); // Set the marker to the chart
        }
    }

    public void setPieChartCenterText(SpannableString centerText) {
        if (mPieChart != null) {
            mPieChart.setDrawCenterText(true);
            mPieChart.setCenterTextColor(mContext.getResources().getColor(R.color.colorBlack));
            mPieChart.setCenterText(centerText);
            mPieChart.invalidate();
        }
    }

    /**
     * 数目少，显示在图上，折线指示
     */
    private void setFewPieChart() {
        //设置label颜色
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(mLabelTextColor);
        mPieChart.setEntryLabelTextSize(mLabelTextSize);

    }

    /**
     * 多于一定数目，需要显示两边的图例
     */
    private void setManyPieChart() {
        //设置label颜色
        mPieChart.setDrawEntryLabels(false);

    }

    /**
     * @param labels
     * @param values
     */
    public void setData(String xValueName, List<String> xValue, double min, double max, List<String> labels, List<List<Double>> values, String unit) {
        setData(xValueName, xValue, min, max, labels, values, false, unit);
    }

    /**
     * @param labels
     * @param values
     * @param extra  折线图，是否填充
     */
    public void setData(String xValueName, List<String> xValue, double min, double max, List<String> labels, List<List<Double>> values, boolean extra, String unit) {
        switch (mChartType) {
            case LINES_CHART:
                setLinesChartData(xValue, min, max, labels, values, extra);
                break;
            case BARS_CHART:
            case BARS_CHART_WITH_CHECKBOX:
            case BARS_CHART_WITH_SEEK_BAR:
            case BARS_CHART_MATCH_PARENT:
                setBarsData(xValueName, xValue, min, max, labels, values);
                break;
            case STACKED_CHART:
            case STACKED_CHART_CHECKBOX:
            case STACKED_CHART_WARN_LINE:
                setStackedData(xValue, min, max, labels, values);
                break;
            case HORIZONTAL_CHART:
                setHorizontalData(xValueName, xValue, min, max, labels, values);
                break;
            case CANDLE_CHART:
                setCandleData(xValue, min, max, labels, values);
                break;
            case NEGATIVE_CHART:
                setNegativeData(xValue, min, max, labels, values);
                break;
            case PIE_CHART:
            case PIE_CHART_PROFILE:
            case PIE_CHART_DRAW_CENTER:
            case PIE_CHART_DRAW_CENTER_NO_PERCENT:
                setPieData(xValue, labels, values.get(0), unit, false);
                break;
            default:
                dataError();
                break;
        }
    }

    private void dataError() {
        dataNull();
    }

    /*清空图表的一切数据*/
    public void dataNull() {
        if (currentChart != null) currentChart.clear();
        if (leftAdapter != null) leftAdapter.clearData();
        if (rightAdapter != null) rightAdapter.clearData();
        if (lineChart != null) lineChart.clear();
        if (seekBarView != null) seekBarView.setVisibility(GONE);
    }

    /**
     * 设置饼图数据
     *
     * @param realLabels  标签
     * @param valueString 真实的值
     * @param values      百分比的值
     * @param unit        单位
     * @param isPercent   是否以百分比形式显示数据，默认false
     */
    public void setPieData(List<String> realLabels, List<String> valueString, List<Double> values, String unit, boolean isPercent) {
        if (mPieChart == null) return;
        if ((values == null || values.isEmpty()) && (realLabels == null || realLabels.isEmpty())) {
            dataNull();
            return;
        }
        selectNothing();
        mPieChart.setUsePercentValues(isPercent);
        int count = Math.min(realLabels.size(), values.size());
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(((float) (values.get(i) / 1)), realLabels.get(i) + ";" + valueString.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        //slice之间间隔
        dataSet.setSliceSpace(2f);
        dataSet.setValueTextSize(mValueTextSize);
        dataSet.setValueTextColor(R.color.colorWhite);
        dataSet.setDrawValues(!mChartType.equals(PIE_CHART_DRAW_CENTER_NO_PERCENT));
//        dataSet.setDrawValues(false);
        //选中后放大的偏移量
        dataSet.setSelectionShift(15f);
        //图表颜色
        dataSet.setColors(colors);
        //饼图内，显示百分比
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        if (mChartType.equals(PIE_CHART_PROFILE)) {
            //设置图表的偏移量，使得图表两边的空白处增大，如果label显示不下，可以将left和right再设置大一点
            mPieChart.setExtraOffsets(0, 10.f, 0, 10.f);
            mPieChart.setDrawEntryLabels(false);
            PieChartLegendAdapter.OnItemSelectedListener onItemSelectedListener = new PieChartLegendAdapter.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int position) {
                    mPieChart.highlightValue(position, mPieChart.getData().getDataSetCount() - 1);
                }
            };
            RecyclerView rvLeft = pieParentView.findViewById(R.id.rv_legend_left);
            rvLeft.setLayoutManager(new GridLayoutManager(mContext, 2));
            leftAdapter = new PieChartLegendAdapter(mContext, realLabels, valueString, unit, colors, "radio");
            leftAdapter.setOnItemSelectedListener(onItemSelectedListener);
            rvLeft.setAdapter(leftAdapter);
        } else if (pieParentView != null && realLabels.size() > PIE_CHART_VALUE_MOST_SIZE) {
            mPieChart.setDrawEntryLabels(false);
            PieChartLegendAdapter.OnItemSelectedListener onItemSelectedListener = new PieChartLegendAdapter.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int position) {
                    mPieChart.highlightValue(position, mPieChart.getData().getDataSetCount() - 1);
                }
            };
            RecyclerView rvLeft = pieParentView.findViewById(R.id.rv_legend_left);
            rvLeft.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            leftAdapter = new PieChartLegendAdapter(mContext, realLabels, valueString, unit, colors, "left");
            leftAdapter.setOnItemSelectedListener(onItemSelectedListener);
            rvLeft.setAdapter(leftAdapter);
            RecyclerView rvRight = pieParentView.findViewById(R.id.rv_legend_right);
            rvRight.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rightAdapter = new PieChartLegendAdapter(mContext, realLabels, valueString, unit, colors, "right");
            rightAdapter.setOnItemSelectedListener(onItemSelectedListener);
            rvRight.setAdapter(rightAdapter);
        } else {
            mPieChart.setDrawEntryLabels(true);
            //设置折线指示线
            dataSet.setValueLinePart1OffsetPercentage(100.f);//设置折现离内边的距离，%，设为100表示从外边开始画
            dataSet.setValueLinePart1Length(0.5f);
            dataSet.setValueLinePart2Length(0.5f);
            dataSet.setValueLineWidth(1f);
            dataSet.setValueLineColor(mLabelTextColor);
            //饼图外，显示label
            dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            //value的样式
//            dataSet.setValueFormatter(new CustomPercentValueFormatter(mPieChart));
        }
        dataSet.setValueFormatter(new CustomPercentValueFormatter(mPieChart));
        PieData data = new PieData(dataSet);
        data.setValueTextSize(mValueTextSize);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.invalidate();
    }

    public void setLinesChartData(List<String> xValue, double max, List<String> labels, List<List<Double>> values) {
        setLinesChartData(xValue, 0, max, labels, values, false);
    }

    public void setLinesChartData(List<String> xValue, double min, double max, List<String> labels, List<List<Double>> values, boolean fill) {
        if (mLinesChart == null) return;
        if (values == null || values.isEmpty()) {
            dataNull();
            return;
        }
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        mLinesChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        mLinesChart.setData(generateLineData(min, max, labels, values, fill, labels.size(), true));
        mLinesChart.invalidate();
    }

    /**
     * @param labels
     * @param valuesList
     */
    public void setBarsData(String xvalueName, List<String> xValue, double min, double max, List<String> labels, List<List<Double>> valuesList) {
        if (mBarChart == null) return;
        if (valuesList == null || valuesList.isEmpty()) {
            dataNull();
            return;
        }
        setBarsChartLegend(min, max, labels, valuesList);
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        seekBarRangeStart = 0;
        seekBarRangeEnd = xValue.size() - 1;
        mBarChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        int[] color = new int[labels.size()];
        System.arraycopy(colors, 0, color, 0, labels.size());
        int j = xValue.size() / xAxisLabelCount;/*用来判断横坐标的label需要隔几个显示*/
        mBarChart.getXAxis().setLabelCount((xValue.size() + 2) / (j + 1), false);
        mBarChart.setData(generateBarData(min, max, labels, valuesList, false, color));
        mBarChart.invalidate();
    }

    public void setNegativeData(List<String> xValue, double min, double max, List<String> labels, List<List<Double>> valuesList) {
        if (mBarChart == null) return;
        if (valuesList == null || valuesList.isEmpty()) {
            dataNull();
            return;
        }
        setBarsChartLegend(min, max, labels, valuesList);
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
//        mBarChart.getAxisLeft().setAxisMinimum(-20f);
        mBarChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        mBarChart.getAxisLeft().setLabelCount(6, true);
        int[] color = new int[labels.size()];
        System.arraycopy(colors, 0, color, 0, labels.size());
        mBarChart.setData(generateBarData(min, max, labels, valuesList, false, color));
        mBarChart.invalidate();
    }

    /**
     * 滑动进度条，改变数据
     *
     * @param labels
     * @param valuesList
     */
    public void setBarsDataAfterRangeChanged(int left, int right, List<String> xValue, double min, double max, List<String> labels, List<List<Double>> valuesList) {
        if (mBarChart == null) return;
        int indexOfLeft = xValue.indexOf(left + "");
        seekBarRangeStart = indexOfLeft;
        int indexOfRight = xValue.indexOf(right + "");
        seekBarRangeEnd = indexOfRight;
        List<String> xValueReal = xValue.subList(indexOfLeft, indexOfRight + 1);
        setXAxisRange(xValueReal);
        setXAxisValueFormatter(xValueReal);
        int j = xValueReal.size() / xAxisLabelCount;/*用来判断横坐标的label需要隔几个显示*/
        mBarChart.getXAxis().setLabelCount((xValueReal.size() + 2) / (j + 1));
        mBarChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        List<List<Double>> subList = new ArrayList<>();
        for (List<Double> list : valuesList) {
            subList.add(list.subList(indexOfLeft, indexOfRight + 1));
        }
        int[] color = new int[labels.size()];
        System.arraycopy(colors, 0, color, 0, labels.size());
        mBarChart.setData(generateBarData(min, max, labels, subList, false, color));
        mBarChart.invalidate();
    }

    /**
     * 设置多选框的数据
     *
     * @param max
     * @param labels     多选框数据
     * @param valuesList
     */
    private void setBarsChartLegend(double min, double max, List<String> labels, List<List<Double>> valuesList) {
        if (labels.size() == 1) {
            mBarChart.getLegend().setEnabled(false);
        } else {
            mBarChart.getLegend().setEnabled(true);
            initLegend(mBarChart.getLegend());
        }
    }

    /**
     * 折线图，初始化数据
     *
     * @param labels 标签，男/女
     * @param values values.size == labels.size
     * @param fill   是否填充
     * @param size   柱状图的label数，用来设置折线图的颜色时使用
     * @return
     */
    private LineData generateLineData(double min, double max, List<String> labels, List<List<Double>> values, boolean fill, int size, boolean isLeft) {
        if (values == null || labels == null) {
            dataNull();
            return null;
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        long scale = getScale(min, max, isLeft);
        long shownMin = isLeft ? shownMinLeft : shownMinRight;
        /*labels.size条折线*/
        for (int z = 0; z < labels.size(); z++) {
            ArrayList<Entry> entry = new ArrayList<>();
            List<Double> eachLine = values.get(z);
            for (int x = 0; x < eachLine.size(); x++) {
                entry.add(new Entry(x, ((float) (eachLine.get(x) - shownMin) * 100 / (scale * 5))));
            }
            LineDataSet d = new LineDataSet(entry, labels.get(z));
            d.setLineWidth(2.5f);
            d.setDrawCircles(false);
            d.setDrawValues(false);
            d.setDrawFilled(fill);
            int color;
            if (labels.get(z).contains("预警线")) {
                color = mContext.getResources().getColor(R.color.colorError);
            } else if (mChartType.equals(COMBINED_CHART)) {
                color = colors[(z + size) % colors.length];
            } else {
                color = colors[z % colors.length];
            }
            d.setColor(color);
            d.setFillColor(color);
            d.setLabel(labels.get(z));/*图例*/
            dataSets.add(d);
        }
        return new LineData(dataSets);
    }

    /**
     * @param labels     标签，如：男/女
     * @param valuesList valuesList.size与labels.size相同，表示男的所有数据集合。
     * @param c
     * @return
     */
    private BarData generateBarData(double min, double max, List<String> labels, List<List<Double>> valuesList, boolean drawValue, int[] c) {
        return generateBarData(min, max, labels, valuesList, drawValue, c, false);
    }

    /**
     * @param labels     标签，如：男/女
     * @param valuesList valuesList.size与labels.size相同，表示男的所有数据集合。
     * @param c
     * @return
     */
    private BarData generateBarData(double min, double max, List<String> labels, List<List<Double>> valuesList, boolean drawValue, int[] c, boolean isLeft) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        long scale = getScale(min, max, isLeft);
        double showMin = isLeft ? shownMinLeft : shownMinRight;
        for (int z = 0; z < valuesList.size(); z++) {
            ArrayList<BarEntry> entry = new ArrayList<>();
            List<Double> eachBar = valuesList.get(z);
            for (int x = 0; x < eachBar.size(); x++) {
                entry.add(new BarEntry(x, (float) (eachBar.get(x) - showMin) * 100 / (scale * 5)));
            }
            BarDataSet d = new BarDataSet(entry, labels.get(z));
            d.setAxisDependency(YAxis.AxisDependency.LEFT);
            d.setDrawValues(false);
            d.setColor(c[z]);
            d.setValueTextSize(mValueTextSize);
            d.setValueTextColor(mValueTextColor);
            d.setDrawValues(drawValue);
            if (!BARS_CHART_WITH_CHECKBOX.equals(mChartType))
                d.setLabel(labels.get(z));/*图例*/
            dataSets.add(d);
        }

        BarData d = new BarData(dataSets);
        d.setValueFormatter(getYAxisValueFormatter(max, min, true, "%"));
        float groupSpace = 0.4f;
        float barSpace = 0.01f;
        float barWidth = (1 - groupSpace) / dataSets.size() - barSpace;
        if (barWidth > 0.2f) {
            barWidth = 0.2f;
            groupSpace = 1 - (barWidth + barSpace) * dataSets.size();
        }
        d.setBarWidth(barWidth);
        if (dataSets.size() > 1) {
            d.groupBars(-0.5f, groupSpace, barSpace); // start at x = -0.5，解决柱状图与横坐标刻度和折线图居中对齐的问题
        }
        return d;
    }

    /**
     * 防止数据长度不一致造成的错误
     *
     * @param labels
     * @param valuesList
     * @return 较小的长度
     */
    private int getDataSize(List<String> labels, List<List<Double>> valuesList) {
        return getDataSize(labels, valuesList, null);
    }

    private int getDataSize(List<String> labels, List<List<Double>> valuesList, List<List<Double>> valuesList2) {
        int size = labels.size();
        if (valuesList != null)
            for (int i = 0; i < valuesList.size(); i++) {
                size = Math.min(size, valuesList.get(i).size());
            }
        if (valuesList2 != null)
            for (int i = 0; i < valuesList2.size(); i++) {
                size = Math.min(size, valuesList2.get(i).size());
            }
        return size;
    }

    private CandleData generateCandleData(double max, List<String> labels, List<List<Double>> valuesList) {
        ArrayList<CandleEntry> values = new ArrayList<>();
        ArrayList<CandleEntry> values2 = new ArrayList<>();

        List<Double> val1 = new ArrayList<>();
        List<Double> val2 = new ArrayList<>();
        List<Double> base = new ArrayList<>();
        base.add(valuesList.get(0).get(0));

        for (int i = 0; i < valuesList.size(); i++) {
            val1.add(valuesList.get(i).get(0) * 100 / max);
            val2.add(valuesList.get(i).get(1) * 100 / max);
        }
        /*第一个，low=baseData，high=low+value；后面的low=high-value*/
        for (int i = 1; i < valuesList.size(); i++) {
            double high, low1, low2;
            low1 = base.get(i - 1);
            high = val1.get(i) + low1;
            low2 = high - val2.get(i);
            base.add(low2);
            values.add(new CandleEntry(i, ((float) high), ((float) low1), ((float) low1), ((float) high)));
            values2.add(new CandleEntry(i, ((float) high), ((float) low2), ((float) low2), ((float) high)));
        }

        CandleDataSet set1 = new CandleDataSet(values, labels.get(0));
        set1.setDrawValues(false);
        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setIncreasingColor(colors[0]);
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setBarSpace(0f);

        CandleDataSet set2 = new CandleDataSet(values2, labels.get(1));
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
     * @param labels     labels.size 每个色块代表什么
     * @param valuesList valuesList.size 有几个bar
     * @return
     */
    private BarData generateStackedData(double max, List<String> labels, List<List<Double>> valuesList, int[] color) {
        ArrayList<BarEntry> entry = new ArrayList<>();
        String[] label = new String[labels.size()];
        long scale = getScale(0, max, true);
        int size = 0;
        if (valuesList.size() > 0) {
            size = valuesList.get(0).size();
        }

        for (int z = 0; z < size; z++) {
            float[] eachBar = new float[labels.size()];
            for (int i = 0; i < valuesList.size(); i++) {
                eachBar[i] = (float) (valuesList.get(i).get(z) - shownMinLeft) * 100 / (scale * 5);
                label[i] = labels.get(i);
            }
            entry.add(new BarEntry(z, eachBar));
        }
        BarDataSet dataSet = new BarDataSet(entry, "");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setDrawValues(false);
        dataSet.setColors(color);
        dataSet.setStackLabels(label);
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
    public void setStackedData(List<String> xValue, double min, double max, List<String> labels, List<List<Double>> valuesList) {
        if (mBarChart == null) return;
        if (valuesList == null || valuesList.isEmpty()) {
            dataNull();
            return;
        }
       /* if (min < 0) {
            max = Math.max(max, Math.abs(min));
            mBarChart.getAxisLeft().setLabelCount(11, true);
            //最小值设置100是因为最大值设置了100，是按照比例显示的
            mBarChart.getAxisLeft().setAxisMinimum(-100);
        }*/
        setBarsChartLegend(min, max, labels, valuesList);
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        if (STACKED_CHART_WARN_LINE.equals(mChartType)) {
            LimitLine ll2 = new LimitLine(68, "预警线 680");
            ll2.setTextColor(Color.RED);
            ll2.setLineWidth(2f);
            ll2.setEnabled(true);
            ll2.setLineColor(Color.RED);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);//标签位置
            ll2.setTextSize(15f);
            mBarChart.getAxisLeft().setDrawLimitLinesBehindData(true);//设置限制线
            mBarChart.getAxisLeft().removeAllLimitLines();
            mBarChart.getAxisLeft().addLimitLine(ll2);
        }

        mBarChart.getXAxis().setAxisMinimum(-1);
        int[] color = new int[labels.size()];
        System.arraycopy(colors, 0, color, 0, labels.size());
        max = 0;
        for (int z = 0; z < xValue.size(); z++) {
            double m = 0;
            for (int i = 0; i < valuesList.size(); i++) {
                m += valuesList.get(i).get(z);
            }
            max = Math.max(m, max);
        }
        mBarChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        mBarChart.setData(generateStackedData(max, labels, valuesList, color));
        mBarChart.invalidate();
    }

    public void setHorizontalData(String xValueName, List<String> xValue, double min, double max, List<String> label, List<List<Double>> valuesList) {
        if (mHorizontalChart == null) return;
        if (valuesList == null || valuesList.isEmpty()) {
            dataNull();
            return;
        }
        if (xValueNameView != null) xValueNameView.setText(xValueName);
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        mHorizontalChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true, "%"));
        int[] color = new int[xValue.size()];
        System.arraycopy(colors, 0, color, 0, xValue.size());
        mHorizontalChart.setData(generateBarData(min, max, label, valuesList, true, color));
        mHorizontalChart.invalidate();
    }

    public void setCandleData(List<String> xValue, double min, double max, List<String> label, List<List<Double>> valuesList) {
        if (mCandleChart == null) return;
        if (valuesList == null || valuesList.isEmpty()) {
            dataNull();
            return;
        }
       /* if (min < 0) {
            max = Math.max(max, Math.abs(min));
            mCandleChart.getAxisLeft().setLabelCount(11, true);
            //最小值设置100是因为最大值设置了100，是按照比例显示的
            mCandleChart.getAxisLeft().setAxisMinimum(-100);
        }*/
        setXAxisRange(xValue);
        setXAxisValueFormatter(xValue);
        mCandleChart.getAxisLeft().setValueFormatter(getYAxisValueFormatter(max, min, true));
        mCandleChart.setData(generateCandleData(max, label, valuesList));
        mCandleChart.setRenderer(new CustomCandleRender(mCandleChart, mCandleChart.getAnimator(), mCandleChart.getViewPortHandler()));
        mCandleChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int xIndex = Math.round(h.getX());
        switch (mChartType) {
            case PIE_CHART:
            case PIE_CHART_DRAW_CENTER:
            case PIE_CHART_DRAW_CENTER_NO_PERCENT:
            case COMBINED_CHART:
            case LINES_CHART:
            case BARS_CHART:
                //如果是大于size的，要选中对应的label
                if (leftAdapter != null) {
                    leftAdapter.onItemClicked(xIndex);
                }
                if (rightAdapter != null) {
                    rightAdapter.onItemClicked(xIndex);
                }
                if (onChartItemCheckedListener != null) {
                    onChartItemCheckedListener.onChartItemChecked(xIndex, mv);
                }
                break;
            case PIE_CHART_PROFILE:
                if (leftAdapter != null) {
                    leftAdapter.onItemClicked(xIndex);
                }
                if (rightAdapter != null) {
                    rightAdapter.onItemClicked(xIndex);
                }
                break;
            case BARS_CHART_WITH_SEEK_BAR:
                if (onChartItemCheckedListener != null) {
                    onChartItemCheckedListener.onChartItemChecked(xIndex, seekBarRangeStart, seekBarRangeEnd, h.getXPx(), h.getYPx());
//                    onChartItemCheckedListener.onChartItemChecked(xIndex, seekBarRangeStart, seekBarRangeEnd, mv);
                }
                break;
            case BARS_CHART_WITH_CHECKBOX:
            case STACKED_CHART_CHECKBOX:
                if (onChartItemCheckedListener != null) {
//                    onChartItemCheckedListener.onChartItemChecked(xIndex, mv);
                    colors = getResources().getIntArray(R.array.color_charts_detail);
                    onChartItemCheckedListener.onChartItemChecked(xIndex, h.getXPx(), h.getYPx(), selectedIndex);
                }
                break;
            default:
                if (onChartItemCheckedListener != null) {
//                    onChartItemCheckedListener.onChartItemChecked(xIndex, mv);
                    onChartItemCheckedListener.onChartItemChecked(xIndex, h.getXPx(), h.getYPx());
                }
                break;
        }
    }

    @Override
    public void onNothingSelected() {
        if (currentChart instanceof PieChart) {
            if (leftAdapter != null) {
                leftAdapter.clearSelected();
            }
            if (rightAdapter != null) {
                rightAdapter.clearSelected();
            }
        }
    }

    public void selectNothing() {
        if (currentChart != null) {
            currentChart.highlightValue(-1, -1);
        }
    }

    public long getScale(double min, double max, boolean isLeft) {
        BigDecimal maxD = new BigDecimal(max - min);
        String difference = maxD.toString();
        String[] value = new String[2];
        if (difference.split("\\.") != null && difference.split("\\.").length > 0) {
            value = difference.split("\\.");
        } else {
            value[0] = difference;
        }
        int firstTwoNum;
        if (value[0].length() > 2) {
            if (Integer.parseInt(value[0].substring(0, 2)) % 4 == 0) {
                firstTwoNum = (Integer.parseInt(value[0].substring(0, 2)) + 1) / 4 + 1;
            } else {
                firstTwoNum = Integer.parseInt(value[0].substring(0, 2)) / 4 + 1;
            }
        } else {
            if (Integer.parseInt(value[0]) % 4 == 0) {
                firstTwoNum = (Integer.parseInt(value[0]) + 1) / 4 + 1;
            } else {
                firstTwoNum = Integer.parseInt(value[0]) / 4 + 1;
            }
        }
        long scale = (long) (firstTwoNum * Math.pow(10, Math.max(value[0].length() - 2, 0)));
        if (isLeft) {
            if (min != 0 && min % scale == 0) {
                shownMinLeft = ((int) Math.floor(min / scale) - 1) * scale;
            } else {
                shownMinLeft = ((int) Math.floor(min / scale)) * scale;
            }
            shownMaxLeft = scale * 5 + shownMinLeft;
        } else {
            if (min != 0 && min % scale == 0) {
                shownMinRight = ((int) Math.floor(min / scale) - 1) * scale;
            } else {
                shownMinRight = ((int) Math.floor(min / scale)) * scale;
            }
            shownMaxRight = scale * 5 + shownMinRight;
        }
        return scale;
    }

    /**
     * 判断当前是否只有一个被选中
     *
     * @param data
     * @return 只有一个被选中
     */
    private boolean checkIfOnlyOneSelected(List<CheckboxDataBean> data) {
        int i = 0;
        for (CheckboxDataBean checkBean : data) {
            if (checkBean.isSelected()) i++;
        }
        return i == 1;
    }

    public interface OnCheckboxItemChangedListener {
        void onCheckBoxChanged(List<CheckboxDataBean> data, int index);
    }

    public void setOnCheckboxItemChangedListener(OnCheckboxItemChangedListener
                                                         onCheckboxItemChangedListener) {
        this.onCheckboxItemChangedListener = onCheckboxItemChangedListener;
    }

    public interface OnChartItemCheckedListener {
        void onChartItemChecked(int index, MarkerView mv);

        void onChartItemChecked(int index, int startIndex, int endIndex, CombinedMarkerView mv);

        void onChartItemChecked(int index, int startIndex, int endIndex, float x, float y);

        void onChartItemChecked(int index, float x, float y);

        void onChartItemChecked(int index, float x, float y, List<Integer> selectedIndex);
    }

    public void setOnChartItemCheckedListener(OnChartItemCheckedListener
                                                      onChartItemCheckedListener) {
        this.onChartItemCheckedListener = onChartItemCheckedListener;
    }
}
