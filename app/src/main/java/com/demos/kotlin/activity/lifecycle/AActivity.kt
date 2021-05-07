package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log

/**
 * Created by YXN on 2018/6/20.
 */

class AActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.textView.text = "AAA singleTask"
        TAG = "A"
        Log.e(TAG, "$TAG onCreate")
    }

}
