package com.demos.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demos.kotlin.R
import com.demos.kotlin.activity.lifecycle.FirstActivity
import com.demos.kotlin.adaper.MySimpleAdapter
import com.demos.kotlin.adaper.MySimpleAdapter.MyOnItemOnClickListener
import com.demos.kotlin.algorithm.AlgorithmInJava
import com.demos.kotlin.algorithm.KotlinSyntaxActivity
import com.demos.kotlin.ijk.PlayerActivity
import com.demos.kotlin.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_open.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var list = listOf<String>()
    private var activityList = mutableMapOf<String, Class<AppCompatActivity>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
        initData()
        initView()
    }

    private fun initData() {
        list = listOf(
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
        list_functions.layoutManager = layoutManager

        // itemDecoration
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.list_divider_bg))

        list_functions.addItemDecoration(itemDecoration)
        list_functions.adapter = adapter
        AlgorithmInJava.getMonthPayEqual(120000.0, 10, 0.06)
        AlgorithmInJava.getMonthPayEqualPrincipal(120000.0, 10, 0.06)

    }


    fun onClick(position: Int) {
        when (list.get(position)) {
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
            else -> {
                ToastUtil.show(this, "没有对应的页面")
                return
            }
        }
    }

}
