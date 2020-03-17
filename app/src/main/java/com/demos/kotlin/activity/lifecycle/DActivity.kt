package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_simple.textView

/**
 * Created by YXN on 2018/6/20.
 */

class DActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = "D"
        textView.text = "DDD standard"
        Log.e(TAG, TAG + " onCreate")
    }

}
