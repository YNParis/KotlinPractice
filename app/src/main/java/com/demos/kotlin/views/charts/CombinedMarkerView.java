
package com.demos.kotlin.views.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MarkerViewAdapter;
import com.demos.kotlin.bean.ChartDataBean;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class CombinedMarkerView extends MarkerView {

    private final TextView title;
    private final RecyclerView dataView;
    private final LinearLayout rootView;
    private List<ChartDataBean> dataBeans = new ArrayList<>();
    private boolean isRight;
    private int totalSize;
    private Context context;
    private MarkerViewAdapter adapter;

    public CombinedMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        this.context = context;
        rootView = findViewById(R.id.root_view_marker_view);
        title = findViewById(R.id.title_marker_view);
        dataView = findViewById(R.id.rv_data_marker_view);
        dataView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        adapter = new MarkerViewAdapter(context, dataBeans);
        dataView.setAdapter(adapter);
    }

    public void setData(List<ChartDataBean> dataBeans, int totalSize) {
        this.totalSize = totalSize;
        if (!this.dataBeans.isEmpty()) {
            this.dataBeans.clear();
        }
        this.dataBeans.addAll(dataBeans);
    }


    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        isRight = highlight.getDataSetIndex() < totalSize / 2;
        Log.e("markerview", "markerview  refreshContent  " + isRight);

        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            title.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            title.setText(Utils.formatNumber(e.getY(), 0, true));
        }
        adapter.notifyDataSetChanged();
        super.refreshContent(e, highlight);
    }

    //前一半在右边显示，后一半在左边显示
    @Override
    public MPPointF getOffset() {
        Log.e("markerview", "markerview  getOffset " + isRight);

        if (isRight) {
            rootView.setBackground(context.getDrawable(R.mipmap.bg_right_arrow));
            return new MPPointF(20, -getHeight());
        }
        rootView.setBackground(context.getDrawable(R.mipmap.bg_left_arrow));
        return new MPPointF(-getWidth() - 20, -getHeight());
    }
}
