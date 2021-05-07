package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log

/**
 * Created by YXN on 2018/5/21.
 */
class BActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = "B"
        binding.textView.text = "BBB singleInstance"
        Log.e(TAG, "$TAG onCreate")
    }

}