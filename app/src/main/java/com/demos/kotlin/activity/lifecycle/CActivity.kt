package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log
import com.demos.kotlin.R.string

/**
 * Created by YXN on 2018/6/20.
 */

class CActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    TAG = "C"
    binding.textView.text = getString(string.c_name)
    Log.e(TAG, "$TAG onCreate")
  }
}
