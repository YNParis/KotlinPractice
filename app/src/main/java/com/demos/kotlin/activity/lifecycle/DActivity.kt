package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log

/**
 * Created by YXN on 2018/6/20.
 */

class DActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = "D"
        binding.textView.text = "DDD standard"
        Log.e(TAG, TAG + " onCreate")
    }

}
