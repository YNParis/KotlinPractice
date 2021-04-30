package com.demos.kotlin.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demos.kotlin.R;
import com.demos.kotlin.utils.BarUtils;
import com.demos.kotlin.utils.DetailRecommendAdapter;
import com.demos.kotlin.utils.GlobalUtils;
import com.demos.kotlin.utils.GridItemDecoration;
import com.demos.kotlin.utils.RecommendBean;
import com.demos.kotlin.utils.SizeUtils;
import com.demos.kotlin.views.TabWithScrollView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品详情页。
 * 实现tab和内容联动
 */
public class CommodityDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ImageView ivBanner;
    RecyclerView detailRecRecycler;
    LinearLayout llCommodity;
    CardView cardEvaluate;
    LinearLayout llDetails;
    LinearLayout llRecommend;
    TabWithScrollView scrollView;

    String[] tabList = {"商品", "评价", "详情", "推荐"};
    List<RecommendBean> mList = new ArrayList<>();
    DetailRecommendAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        updateStatus(GlobalUtils.getColor(this, R.color.colorBlack50));
        for (int i = 0; i < 20; i++) {
            mList.add(new RecommendBean());
        }
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_commodity);
        tabLayout = findViewById(R.id.tabLayout);
        ivBanner = findViewById(R.id.iv_banner);
        scrollView = findViewById(R.id.scrollView);
        detailRecRecycler = findViewById(R.id.detail_rec_commodity);
        llCommodity = findViewById(R.id.ll_commodity);
        cardEvaluate = findViewById(R.id.card_evaluate);
        llDetails = findViewById(R.id.ll_details);
        llRecommend = findViewById(R.id.ll_recommend);
        findViewById(R.id.ll_active_desc).setOnClickListener(this);
        findViewById(R.id.ll_coupon).setOnClickListener(this);
        findViewById(R.id.ll_select_sku).setOnClickListener(this);

        setToolbar();
        tabLayout.setAlpha(0);
        toolbar.setBackgroundColor(GlobalUtils.setAlpha(R.color.colorWhite, 0));

        for (String tab : tabList) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }

        detailRecRecycler.setNestedScrollingEnabled(false);
        detailRecRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        detailRecRecycler.addItemDecoration(new GridItemDecoration(2,
                SizeUtils.dp2px(8), true));
        mAdapter = new DetailRecommendAdapter(this, mList);
        detailRecRecycler.setAdapter(mAdapter);

        List<View> views = new LinkedList<>();
        views.add(llCommodity);
        views.add(cardEvaluate);
        views.add(llDetails);
        views.add(llRecommend);

        scrollView.setAnchorList(views); // 设置视图集合
        scrollView.setupWithTabLayout(tabLayout); // 设置绑定的tabLayout
        // 设置距离顶部的偏差值
        scrollView.setTranslationY(80 + BarUtils.getStatusBarHeight());
        scrollView.setTranslationY(SizeUtils.getMeasuredHeight(toolbar) + BarUtils.getStatusBarHeight());
        final float height = SizeUtils.getMeasuredHeight(ivBanner);
        scrollView.setOnScrollCallback(new TabWithScrollView.OnScrollCallback() {
            @Override
            public void onScrollCallback(int l, int t, int oldl, int oldt) {
                setBgAlphaChange(t, height);
            }
        });

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

    protected void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }
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

    /**
     * 标题栏渐变
     */
    private void setBgAlphaChange(int t, float height) {
        assert toolbar != null;
        if (t <= 0) {
            tabLayout.setAlpha(0);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setBackgroundColor(GlobalUtils.setAlpha(R.color.colorWhite, 0));
        } else if (t < height) {
            float scale = (float) t / height;
            if (scale > 0.2f) {
                tabLayout.setVisibility(View.VISIBLE);
            } else {
                tabLayout.setVisibility(View.GONE);
            }
            if (scale > 0.7f) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            } else {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            }
            tabLayout.setAlpha(scale);
            toolbar.setBackgroundColor(GlobalUtils.setAlpha(R.color.colorWhite, (int) (255 * scale)));
        } else {
            tabLayout.setAlpha(1);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setBackgroundColor(GlobalUtils.setAlpha(R.color.colorWhite, 255));
        }
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
}
