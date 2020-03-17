package com.demos.kotlin.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.R;

import java.util.List;

/**
 * Created by Hao on 2019/7/23.
 * Describe
 */
public class DetailRecommendAdapter extends RecyclerView.Adapter<DetailRecommendAdapter.ViewHolder> {

    private List<RecommendBean> mList;
    private Context mContext;

    public DetailRecommendAdapter(Context context, List<RecommendBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recommend, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ViewGroup.LayoutParams layoutParams = holder.ivCommodityImg.getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(8)) / 2;
        layoutParams.height = layoutParams.width;
        holder.ivCommodityImg.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCommodityImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCommodityImg = itemView.findViewById(R.id.iv_commodity_img);
        }
    }
}
