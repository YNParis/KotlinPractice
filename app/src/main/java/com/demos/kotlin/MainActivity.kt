package com.demos.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.demos.kotlin.adaper.MySimpleAdapter
import com.demos.kotlin.adaper.MySimpleAdapter.MyOnItemOnClickListener
import com.demos.kotlin.ijk.PlayerTest
import com.demos.kotlin.kotlinsyntax.KotlinSyntaxActivity
import com.demos.kotlin.lifecycle.FirstActivity
import com.demos.kotlin.ndk.NdkDemoActivity
import com.demos.kotlin.views.TouchableBallActivity
import com.demos.kotlin.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_open.list_functions
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

  //功能菜单
  private var list = listOf(
      "CustomView-TouchableBall", "ijk", "NDK", "LifeCycle", "database", "webview", "KotlinSyntax"
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
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    // layoutManager
    list_functions.layoutManager = layoutManager

    // itemDecoration
    val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    itemDecoration.setDrawable(resources.getDrawable(R.drawable.list_divider_bg))
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

      else -> return
    }
  }

}
