package com.demos.kotlin.kotlinsyntax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.InflateException
import android.view.View
import com.demos.kotlin.Constants
import com.demos.kotlin.R
import com.demos.kotlin.databinding.ActivityKotlinBinding
import com.demos.kotlin.utils.LOG_TAG_PACKAGE_B

class KotlinActivity : AppCompatActivity() {

    lateinit var view: View
    lateinit var binding: ActivityKotlinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view = binding.testTextview
    }

    override fun onResume() {
        super.onResume()
        printId(view)
    }

    private fun printId(view: View?) {
        Log.d(Constants.lOG_TAG, "view id:" + view?.id)
        Log.d(Constants.lOG_TAG, name)
        Log.d(Constants.AConstants.lOG_TAG, name)
        Log.d(Constants.BConstants.lOG_TAG, name)
        Log.d(LOG_TAG_PACKAGE_B, name)
    }

    val name: String = "kotlin"
        get() {
            return field + "甚至可以毫不相干？不可以"
        }

    val final = "2"
}