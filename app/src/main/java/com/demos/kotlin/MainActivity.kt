package com.demos.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demos.kotlin.adaper.MySimpleAdapter
import com.demos.kotlin.adaper.MySimpleAdapter.MyOnItemOnClickListener
import com.demos.kotlin.ijk.PlayerTest
import com.demos.kotlin.kotlinsyntax.KotlinSyntaxActivity
import com.demos.kotlin.lifecycle.FirstActivity
import kotlinx.android.synthetic.main.activity_open.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    //功能菜单
    private var list = listOf(
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
            "Widgets"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
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
    }

    fun onClick(position: Int) {
        when (position) {
            0 ->
                startActivity<TouchableBallActivity>()
            1 ->
                startActivity<PlayerTest>()
            2 ->
                startActivity<NdkDemoActivity>()
            3 ->
                startActivity<FirstActivity>()
            5 ->
                startActivity<WebViewActivity>()
            6 ->
                startActivity<KotlinSyntaxActivity>()
            7 ->
                startActivity<WaterMarkActivity>()
            8 ->
                startActivity<PictureActivity>()
            9 ->
                startActivity<CameraActivity>()
            10 ->
                startActivity<WeChatMainActivity>()
            11 ->
                startActivity<WidgetsActivity>()

            else -> return
        }
    }

}
