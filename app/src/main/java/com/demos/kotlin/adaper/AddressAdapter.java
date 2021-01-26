package com.demos.kotlin.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.demos.kotlin.R;

import java.util.List;


public class AddressAdapter extends BaseAdapter {

    private List<PoiInfo> list = null;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public AddressAdapter(Context mContext, List<PoiInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final PoiInfo contact = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_address, null);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.address = (TextView) view.findViewById(R.id.address);
            viewHolder.location = (TextView) view.findViewById(R.id.location);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) onItemClickListener.onItemClick(position);
                }
            });
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(this.list.get(position).getName());
        viewHolder.address.setText(this.list.get(position).getAddress());
        viewHolder.location.setText("经纬度：" + this.list.get(position).getLocation().toString());
        return view;

    }

    final static class ViewHolder {
        TextView name;
        TextView address;
        TextView location;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}