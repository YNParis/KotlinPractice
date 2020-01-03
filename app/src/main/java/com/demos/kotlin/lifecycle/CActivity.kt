package com.demos.kotlin.lifecycle

import android.os.Bundle
import android.util.Log
import com.demos.kotlin.R.string
import kotlinx.android.synthetic.main.activity_simple.textView

/**
 * Created by YXN on 2018/6/20.
 */

class CActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    TAG = "C"
    textView.text = getString(string.c_name)
    Log.e(TAG, "$TAG onCreate")
  }
}
