package com.demos.kotlin.activity.lifecycle

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_simple.textView

/**
 * Created by YXN on 2018/5/21.
 */
class BActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = "B"
        textView.text = "BBB singleInstance"
        Log.e(TAG, "$TAG onCreate")
    }

}