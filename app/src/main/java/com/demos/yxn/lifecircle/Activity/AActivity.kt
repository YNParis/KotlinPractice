package com.demos.yxn.lifecircle.Activity

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_simple.*

/**
 * Created by YXN on 2018/6/20.
 */

class AActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text = "AAA singleTask"
        TAG = "A"
        Log.e(TAG, TAG + " onCreate")
    }
    
}
