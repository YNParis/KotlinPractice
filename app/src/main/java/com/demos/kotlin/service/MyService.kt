package com.demos.kotlin.service

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.os.Message
import android.util.Log

/**
 * Created by YXN on 2018/5/22.
 */
class MyService : Service() {

    val TAG = "MyService"

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//返回一个Binder对象
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, TAG + " onCreate()")
        val msg=Message()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.e(TAG, TAG + " onConfigurationChanged()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, TAG + " onDestroy")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.e(TAG, TAG + " onLowMemory")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.e(TAG, TAG + " onRebind")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.e(TAG, TAG + " onStartCommand")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.e(TAG, TAG + " onTaskRemoved")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e(TAG, TAG + " onTrimMemory")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Log.e(TAG, TAG + " onUnbind")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.e(TAG, TAG + " onStart")
    }


}