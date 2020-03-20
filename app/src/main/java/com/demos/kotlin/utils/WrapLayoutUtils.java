package com.demos.kotlin.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.demos.kotlin.R;
import com.demos.kotlin.views.WrapLinearLayout;

import java.util.List;
import java.util.Random;

public class WrapLayoutUtils {

    public static void setColorfulWrapLayout(final Context context, final List<String> list, WrapLinearLayout wrapLayout) {//List<LinkedTreeMap<String, Object>> list
        wrapLayout.setGrivate(1);
        wrapLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(context);
        int[] colorList = context.getResources().getIntArray(R.array.colorCardBackground);
        Random random = new Random();

        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                final View view = mInflater.inflate(R.layout.item_relation_wrap_layout, wrapLayout, false);//添加的view,这里很简单就是一个TextView
                final TextView textView = view.findViewById(R.id.tv_item_wrap_layout);
                final CardView cardView = view.findViewById(R.id.cardview_item_wrap_layout);
                cardView.setCardBackgroundColor(colorList[random.nextInt(100) % colorList.length]);
                textView.setText(list.get(i));
                textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
                wrapLayout.addView(view);
            }
        }
    }

    public static void setNormalWhiteWrap(final Context context, final List<String> list, WrapLinearLayout lay_gallery) {
        lay_gallery.removeAllViews();
        lay_gallery.setGrivate(1);
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                final View view = mInflater.inflate(R.layout.item_relation_wrap_layout, lay_gallery, false);//添加的view,这里很简单就是一个TextView
                final TextView textView = view.findViewById(R.id.tv_item_wrap_layout);
//                final CardView cardView = view.findViewById(R.id.cardview_item_wrap_layout);
//                DensityUtil.setViewMargin(cardView, false, 10, 10, 10, 10);
                textView.setText(list.get(i));
                lay_gallery.addView(view);
            }
        }
    }
}
