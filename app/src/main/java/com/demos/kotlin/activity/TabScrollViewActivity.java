package com.demos.kotlin.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.demos.kotlin.R;
import com.demos.kotlin.utils.GlobalUtils;
import com.demos.kotlin.utils.RecommendBean;
import com.demos.kotlin.utils.SizeUtils;
import com.demos.kotlin.views.TabWithScrollView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hao on 2019/7/23.
 * Describe 商品详情页
 */
public class TabScrollViewActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ImageView ivBanner;
    LinearLayout llCommodity;
    CardView cardEvaluate;
    LinearLayout llDetails;
    LinearLayout llRecommend;
    TabWithScrollView scrollView;

    String[] tabList = {"商品", "评价", "详情", "推荐"};
    List<RecommendBean> mList = new ArrayList<>();
    private TextView tv;
    private boolean canMove = false;
    private List<TabViewBean> tabViewBeans = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_scroll_view);
        updateStatus(GlobalUtils.getColor(this, R.color.colorBlack50));
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            mList.add(new RecommendBean());
        }
    }

    private void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        ivBanner = findViewById(R.id.iv_banner);
        scrollView = findViewById(R.id.scrollView);
        llCommodity = findViewById(R.id.ll_commodity);
        cardEvaluate = findViewById(R.id.card_evaluate);
        llDetails = findViewById(R.id.ll_details);
        llRecommend = findViewById(R.id.ll_recommend);
        findViewById(R.id.ll_active_desc).setOnClickListener(this);
        findViewById(R.id.ll_coupon).setOnClickListener(this);
        findViewById(R.id.ll_select_sku).setOnClickListener(this);
        for (String tab : tabList) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }

        List<View> views = new LinkedList<>();
        views.add(llCommodity);
        views.add(cardEvaluate);
        views.add(llDetails);
        views.add(llRecommend);

        scrollView.setAnchorList(views); // 设置视图集合
        scrollView.setupWithTabLayout(tabLayout); // 设置绑定的tabLayout
        // 设置距离顶部的偏差值
        tv = findViewById(R.id.tv_title);
//        scrollView.setTranslationY(-(SizeUtils.getMeasuredHeight(tv) + SizeUtils.getMeasuredHeight(tabLayout) + BarUtils.getStatusBarHeight()));
        //头部的高度，滑到一定程度消失
        final float height = SizeUtils.getMeasuredHeight(tv);
        scrollView.setOnScrollCallback(new TabWithScrollView.OnScrollCallback() {
            @Override
            public void onScrollCallback(int l, int t, int oldl, int oldt) {
                Log.e("tag", "----onScrollCallback:" + t + " " + height);
                setTitleVisibility(t, height);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.e("tag", "----onScrollChange:" + scrollY + " " + oldScrollY + " " + height);
                    canMove = Math.abs(scrollY - oldScrollY) <= 1;
                }
            });
        }

    }

    /**
     * 更新状态栏颜色
     *
     * @param color
     */
    protected void updateStatus(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_active_desc:
                break;
            case R.id.ll_coupon:
                break;
            case R.id.ll_select_sku:
                break;
        }
    }

    /**
     * 设置滑动到一定为止，上面的布局消失/显示
     */
    private void setTitleVisibility(int t, float height) {
        Log.e("tag", "----setBgAlphaChange:" + t + " " + height);
        if (canMove)
            if (height > t) {
                showView(tv);
            } else {
                hideView(tv);
            }
    }

    private void showView(View view) {
        if (view.getVisibility() != View.VISIBLE) view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        if (view.getVisibility() != View.GONE) view.setVisibility(View.GONE);
    }

}
