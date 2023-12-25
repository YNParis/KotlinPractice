package com.bonc.ioc.chart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonc.ioc.chart.R;
import com.bonc.ioc.chart.bean.CommonRadioBean;
import com.bonc.ioc.chart.view.ColorDotView;

import java.util.List;

/**
 * 点击图表弹出的window信息adapter
 */
public class ChartWindowInfoAdapter extends RecyclerView.Adapter<ChartWindowInfoAdapter.ChartWindowInfoViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<CommonRadioBean> data;
    private int[] color;

    public ChartWindowInfoAdapter(Context context, List<CommonRadioBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        color = context.getResources().getIntArray(R.array.color_charts_detail);
    }

    @NonNull
    @Override
    public ChartWindowInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChartWindowInfoViewHolder(inflater.inflate(R.layout.item_chart_window_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChartWindowInfoViewHolder holder, final int position) {
        if (data.get(position).getText().contains("预警线")) {
            holder.dot.setBackgroundColor(context.getResources().getColor(R.color.colorError));
        } else {
            holder.dot.setBackgroundColor(color[position]);
        }
        holder.key.setText(data.get(position).getText());
        holder.value.setText(data.get(position).getId());
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public void setColor(List<Integer> selectedIndex) {
        color = context.getResources().getIntArray(R.array.color_charts_detail);
        for (int index = 0; index < selectedIndex.size(); index++) {
            color[index] = color[selectedIndex.get(index)];
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChartWindowInfoViewHolder extends RecyclerView.ViewHolder {
        private ColorDotView dot;
        private TextView key;
        private TextView value;

        ChartWindowInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            dot = itemView.findViewById(R.id.dot_chart_info);
            key = itemView.findViewById(R.id.key_chart_info);
            value = itemView.findViewById(R.id.value_chart_info);
        }
    }

}
