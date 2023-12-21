package com.demos.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demos.kotlin.R
import com.demos.kotlin.activity.kotlinchart.*
import com.demos.kotlin.activity.lifecycle.FirstActivity
import com.demos.kotlin.adaper.MySimpleAdapter
import com.demos.kotlin.adaper.MySimpleAdapter.MyOnItemOnClickListener
import com.demos.kotlin.algorithm.KotlinSyntaxActivity
import com.demos.kotlin.databinding.ActivityOpenBinding
import com.demos.kotlin.ijk.PlayerActivity
import com.demos.kotlin.utils.ToastUtil
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var list: List<String>
    private lateinit var binding: ActivityOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
    }

    private fun initData() {
        list = listOf(
            "ImagePicker",
            "BaiduMap",
            "SeekBar",
            "BaseChart",
            "Candle",
            "Pie",
            "Combined",
            "Stacked",
            "Horizontal",
            "BarChart",
            "PieCharts",
            "MultiBarCharts",
            "PopWindow",
            "CustomMapView",
            "TripleViewPager",
            "CardViewPager",
            "DrawerView",
            "ChromeStyleTab",
            "Banner",
            "CustomView-TouchableBall",
            "ijk",
            "NDK",
            "LifeCycle",
            "Database",
            "WebView",
            "KotlinSyntax",
            "waterMark",
            "SystemCamera",
            "CustomCamera",
            "WeChatCamera",
            "Widgets",
            "TabScrollView",
            "CommodityDetail",
            "UpdateAppVersion",
            "Json"
        )

    }

    private fun initView() {
        val adapter = MySimpleAdapter(list, this)
        adapter.setMyOnItemClickListener(object : MyOnItemOnClickListener {
            override fun onItemClick(position: Int) {
                onClick(position)
            }
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        // layoutManager
        binding.listFunctions.layoutManager = layoutManager

        // itemDecoration
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.list_divider_bg))

        binding.listFunctions.addItemDecoration(itemDecoration)
        binding.listFunctions.adapter = adapter

    }


    fun onClick(position: Int) {
        when (list[position]) {
            "ImagePicker" ->
                startActivity<ImagePickerActivity>()
            "BaiduMap" ->
                startActivity<AddressChooseActivity>()
            "SeekBar" ->
                startActivity<SeekBarActivity>()
            "CustomView-TouchableBall" ->
                startActivity<TouchableBallActivity>()
            "ijk" ->
                startActivity<PlayerActivity>()
            "NDK" ->
                startActivity<NdkDemoActivity>()
            "LifeCycle" ->
                startActivity<FirstActivity>()
            "WebView" ->
                startActivity<WebViewActivity>()
            "KotlinSyntax" ->
                startActivity<KotlinSyntaxActivity>()
            "waterMark" ->
                startActivity<WaterMarkActivity>()
            "SystemCamera" ->
                startActivity<PictureActivity>()
            "CustomCamera" ->
                startActivity<CameraActivity>()
            "WeChatCamera" ->
                startActivity<WeChatMainActivity>()
            "Widgets" ->
                startActivity<WidgetsActivity>()
            "TabScrollView" ->
                startActivity<TabScrollViewActivity>()
            "CommodityDetail" ->
                startActivity<CommodityDetailActivity>()
            "UpdateAppVersion" ->
                startActivity<UpdateAppActivity>()
            "Json" ->
                startActivity<JsonParseActivity>()
            "Banner" ->
                startActivity<BannerActivity>()
            "ChromeStyleTab" ->
                startActivity<ChromeTabActivity>()
            "DrawerView" ->
                startActivity<DrawerActivity>()
            "CardViewPager" ->
                startActivity<CardViewPagerActivity>()
            "TripleViewPager" ->
                startActivity<TripleViewPagerActivity>()
            "CustomMapView" ->
                startActivity<CustomMapActivity>()
            "PopWindow" ->
                startActivity<PopwindowActivity>()
            "MultiBarCharts" ->
                startActivity<BarChartActivityMultiDataset>()
            "PieCharts" ->
                startActivity<PieChartActivity>()
            "BarChart" ->
                startActivity<BarChartActivity>()
            "Combined" ->
                startActivity<CombinedChartActivity>()
            "Stacked" ->
                startActivity<StackedBarActivity>()
            "Horizontal" ->
                startActivity<HorizontalBarChartActivity>()
            "Candle" ->
                startActivity<CandleStickChartActivity>()
            "Pie" ->
                startActivity<PiePolylineChartActivity>()
            "BaseChart" ->
                startActivity<BaseChartActivity>()
            else ->
                ToastUtil.show(this, "没有对应的页面")
        }
    }

}
