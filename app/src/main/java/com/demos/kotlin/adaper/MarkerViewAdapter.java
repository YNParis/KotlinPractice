package com.demos.kotlin.adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.bean.ChartDataBean;

import java.util.List;

public class MarkerViewAdapter extends RecyclerView.Adapter<MarkerViewAdapter.MarkerViewHolder> {

    private Context context;
    private List<ChartDataBean> dataBeans;

    public MarkerViewAdapter(Context context, List<ChartDataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MarkerViewHolder extends RecyclerView.ViewHolder {

        public MarkerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
