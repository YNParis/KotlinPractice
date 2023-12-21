package com.bonc.ioc.chart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonc.ioc.chart.R;
import com.bonc.ioc.chart.view.ColorDotView;

import java.util.List;

/**
 * 饼图，数据大于一定数目之后，自定义的图例
 */
public class PieChartLegendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NO_DATA = 1;
    private static final int VIEW_TYPE_NORMAL = 2;
    private Context context;
    private List<String> labels;
    private int[] colors;
    private List<String> values;
    private LayoutInflater inflater;
    private boolean isLeft;
    private OnItemSelectedListener onItemSelectedListener;
    private int selectedIndex = -1;
    private boolean isRadio;
    private String unit;

    public PieChartLegendAdapter(Context context, List<String> labels, List<String> values, String unit, int[] colors, String type) {
        this.context = context;
        this.labels = labels;
        this.values = values;
        this.colors = colors;
        this.unit = unit;
        isLeft = type.equals("left");
        isRadio = type.equals("radio");
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NO_DATA) {
            return new PieChartLegendNoDataViewHolder(inflater.inflate(R.layout.item_view_no_data, parent, false));
        }
        return new PieChartLegendViewHolder(inflater.inflate(R.layout.item_pie_legend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PieChartLegendNoDataViewHolder) return;
        PieChartLegendViewHolder pieChartLegendViewHolder = ((PieChartLegendViewHolder) holder);
        pieChartLegendViewHolder.dotView.setBackgroundColor(colors[position % colors.length]);
        pieChartLegendViewHolder.label.setText(labels.get(position));
        pieChartLegendViewHolder.value.setText(values.get(position));
        if (isRadio) {
            pieChartLegendViewHolder.dotView.setOnSelected(position == selectedIndex);
            pieChartLegendViewHolder.rootView.setBackgroundResource(0);
        } else {
            pieChartLegendViewHolder.rootView.setSelected(position == selectedIndex);
            pieChartLegendViewHolder.label.setSelected(position == selectedIndex);
            pieChartLegendViewHolder.value.setSelected(position == selectedIndex);
        }
        pieChartLegendViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
//        if (isRadio || isLeft && position % 2 == 0 || (!isLeft) && position % 2 == 1) {
        if (isRadio || isLeft && position < getItemCount() / 2 || (!isLeft) && position >= getItemCount() / 2) {
            return VIEW_TYPE_NORMAL;
        }
        return VIEW_TYPE_NO_DATA;
    }

    public void onItemClicked(int position) {
        clearSelected();
        selectedIndex = position;
        notifyItemChanged(position);
    }

    public void clearSelected() {
        selectedIndex = -1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Math.min(labels.size(), values.size());
    }

    public void clearData() {
        labels.clear();
        notifyDataSetChanged();
    }

    class PieChartLegendViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private TextView value;
        private ColorDotView dotView;
        private RelativeLayout rootView;

        PieChartLegendViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.text_label);
            value = itemView.findViewById(R.id.text_value);
            dotView = itemView.findViewById(R.id.dot_view);
            rootView = itemView.findViewById(R.id.root_view_legend_item);
        }
    }

    class PieChartLegendNoDataViewHolder extends RecyclerView.ViewHolder {
        PieChartLegendNoDataViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
