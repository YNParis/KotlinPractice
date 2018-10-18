package com.demos.yxn.lifecircle.Activity

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.demos.yxn.lifecircle.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, TAG + " onCreate() is invoked.")
        button.setOnClickListener {
            Log.e(TAG, "点击按钮")
            startActivity<BActivity>()//直接跳转
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, TAG + " onRestart() is invoked.")
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, TAG + " onStart() is invoked.")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, TAG + " onResume() is invoked.")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, TAG + " onPause() is invoked.")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, TAG + " onStop() is invoked.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, TAG + " onDestroy() is invoked.")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, TAG + " onSaveInstanceState() is invoked.")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG, TAG + " onRestoreInstanceState() is invoked.")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.e(TAG, TAG + " onConfigurationChanged() 修改配置")
    }
}
