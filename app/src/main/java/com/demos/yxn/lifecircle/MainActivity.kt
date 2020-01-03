package com.demos.yxn.lifecircle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.demos.yxn.lifecircle.liftcycle.FirstActivity
import com.demos.yxn.lifecircle.ndk.NdkDemoActivity
import com.demos.yxn.lifecircle.views.TouchableBallActivity
import com.demos.yxn.lifecircle.webview.WebViewActivity
import com.demos.yxn.lifecircle.adaper.MySimpleAdapter
import com.demos.yxn.lifecircle.adaper.MySimpleAdapter.MyOnItemOnClickListener
import com.demos.yxn.lifecircle.ijk.PlayerTest
import com.demos.yxn.lifecircle.kotlinsyntax.KotlinSyntaxActivity
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
