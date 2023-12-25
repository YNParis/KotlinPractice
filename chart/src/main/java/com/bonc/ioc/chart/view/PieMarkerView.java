
package com.bonc.ioc.chart.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bonc.ioc.chart.R;
import com.bonc.ioc.chart.bean.CommonRadioBean;
import com.github.mikephil.charting.components.MarkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼图的markerView
 * 需要实现的效果：
 */
@SuppressLint("ViewConstructor")
public class PieMarkerView extends MarkerView {

    private TextView valueView;
    private final LinearLayout rootView;
    private final List<CommonRadioBean> data = new ArrayList<>();
    private String title;
    private boolean isRight;
    private int totalSize;
    private Context context;
    private OnEntryClickedListener onEntryClickedListener;

    public PieMarkerView(Context context) {
        super(context, R.layout.layout_marker_view_pie);
        this.context = context;
        rootView = findViewById(R.id.root_view_marker_view);
        valueView = findViewById(R.id.value_marker_view_pie);
    }

    public void setData(String value) {
        valueView.setText(value);
    }

    public interface OnEntryClickedListener {
        void onEntryClicked(int position);
    }

    public void setOnEntryClickedListener(OnEntryClickedListener onEntryClickedListener) {
        this.onEntryClickedListener = onEntryClickedListener;
    }
}
