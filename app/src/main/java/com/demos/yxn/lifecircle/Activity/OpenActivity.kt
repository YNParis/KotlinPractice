package com.demos.yxn.lifecircle.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.demos.yxn.lifecircle.R

class OpenActivity : AppCompatActivity() {

  //功能菜单
  var btnList = listOf("1", "3")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_open)

  }
}
