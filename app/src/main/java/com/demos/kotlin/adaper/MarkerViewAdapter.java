package com.demos.kotlin.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.R;
import com.demos.kotlin.bean.MarkerDataBean;

import java.util.List;

public class MarkerViewAdapter extends RecyclerView.Adapter<MarkerViewAdapter.MarkerViewHolder> {

    private Context context;
    private List<MarkerDataBean> dataBeans;
    private LayoutInflater inflater;

    public MarkerViewAdapter(Context context, List<MarkerDataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarkerViewHolder(inflater.inflate(R.layout.item_marker_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, int position) {
        holder.key.setText(dataBeans.get(position).getKey());
        holder.value.setText(dataBeans.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class MarkerViewHolder extends RecyclerView.ViewHolder {
        private TextView key;
        private TextView value;

        MarkerViewHolder(@NonNull View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.text_key);
            value = itemView.findViewById(R.id.text_value);
        }
    }
}
