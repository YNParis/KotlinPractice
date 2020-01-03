package com.demos.kotlin.RecyclereViewDemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demos.kotlin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YXN on 2018/7/13.
 */

public class MyRcviewAdapter extends RecyclerView.Adapter {

    private List<String> list = new ArrayList<>();
    private LayoutInflater inflater;

    public MyRcviewAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_rcview1, parent, false);
                view.setBackgroundColor(Color.BLUE);
                break;
            case 1:
                view = inflater.inflate(R.layout.item_rcview2, parent, false);
                view.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                view = inflater.inflate(R.layout.item_rcview3, parent, false);
                view.setBackgroundColor(Color.YELLOW);
                break;
            default:
                break;
        }
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_item_rcview);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = position % 3;
        return type;
    }

}
