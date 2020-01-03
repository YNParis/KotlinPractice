package com.demos.kotlin.lifecycle

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.demos.kotlin.R
import kotlinx.android.synthetic.main.activity_simple.to_a
import kotlinx.android.synthetic.main.activity_simple.to_b
import kotlinx.android.synthetic.main.activity_simple.to_c
import kotlinx.android.synthetic.main.activity_simple.to_d
import org.jetbrains.anko.startActivity

/**
 * Created by YXN on 2018/6/21.
 */
open class BaseActivity : AppCompatActivity(), View.OnClickListener {

  var TAG = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple)
    to_a.setOnClickListener(this)
    to_b.setOnClickListener(this)
    to_c.setOnClickListener(this)
    to_d.setOnClickListener(this)

  }

  override fun onRestart() {
    super.onRestart()
    Log.e(TAG, "$TAG onRestart()")
  }

  override fun onStart() {
    super.onStart()
    Log.e(TAG, "$TAG onStart()")
  }

  override fun onResume() {
    super.onResume()
    Log.e(TAG, "$TAG onResume()")
  }

  override fun onPause() {
    super.onPause()
    Log.e(TAG, "$TAG onPause()")
  }

  override fun onStop() {
    super.onStop()
    Log.e(TAG, "$TAG onStop()")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.e(TAG, "$TAG onDestroy()")
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    Log.e(TAG, "$TAG onSaveInstanceState()")
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    Log.e(TAG, "$TAG onRestoreInstanceState()")
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
    Log.e(TAG, "$TAG onConfigurationChanged() 修改配置")
  }

  override fun onClick(v: View?) {
    when (v!!.id) {
      R.id.to_a -> {
        startActivity<AActivity>()
      }
      R.id.to_b -> {
        startActivity<BActivity>()
      }
      R.id.to_c -> {
        startActivity<CActivity>()
      }
      R.id.to_d -> {
        startActivity<DActivity>()
      }
    }
  }

  override fun finish() {
    Log.e(TAG, "$TAG finish")
    super.finish()
  }

}