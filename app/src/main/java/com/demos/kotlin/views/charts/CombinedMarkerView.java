
package com.demos.kotlin.views.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MarkerViewAdapter;
import com.demos.kotlin.bean.MarkerDataBean;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

/**
 * 混合图的markerview。
 * 需要实现的效果：
 * 1.点击任意一个点，显示当前x轴对应的所有数据——传入数据，或者动态设置数据；
 * 2.需要向左或向右弹出框——判断是前50%，向右弹，后50%，向左弹。
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class CombinedMarkerView extends MarkerView {

    private final TextView titleView;
    private final RecyclerView dataView;
    private final LinearLayout rootView;
    private List<MarkerDataBean> dataBeans = new ArrayList<>();
    private boolean isRight;
    private int totalSize;
    private Context context;
    private MarkerViewAdapter adapter;
    private OnEntryClickedListener onEntryClickedListener;

    public CombinedMarkerView(Context context) {
        super(context, R.layout.bar_marker_view);
        this.context = context;
        rootView = findViewById(R.id.root_view_marker_view);
        titleView = findViewById(R.id.title_marker_view);
        dataView = findViewById(R.id.rv_data_marker_view);
        dataView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        adapter = new MarkerViewAdapter(context, dataBeans);
        dataView.setAdapter(adapter);
    }

    public void setData(List<MarkerDataBean> dataBeans, String title, int totalSize) {
        this.totalSize = totalSize;
        if (!this.dataBeans.isEmpty()) {
            this.dataBeans.clear();
        }
        this.dataBeans.addAll(dataBeans);
        titleView.setText(title);
    }


    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        isRight = e.getX() < totalSize / 2;
        adapter.notifyDataSetChanged();
        super.refreshContent(e, highlight);
    }

    //前一半在右边显示，后一半在左边显示
    @Override
    public MPPointF getOffset() {
        if (isRight) {
            //在右侧弹出
            rootView.setBackground(context.getDrawable(R.mipmap.bg_left_arrow));
            return new MPPointF(0, -getHeight() / 2);
        }
        rootView.setBackground(context.getDrawable(R.mipmap.bg_right_arrow));
        return new MPPointF(-getWidth(), -getHeight() / 2);
    }

    public interface OnEntryClickedListener {
        void onEntryClicked(int position);
    }

    public void setOnEntryClickedListener(OnEntryClickedListener onEntryClickedListener) {
        this.onEntryClickedListener = onEntryClickedListener;
    }
}
