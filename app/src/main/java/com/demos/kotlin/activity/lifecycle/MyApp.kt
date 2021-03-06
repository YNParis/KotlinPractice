package com.demos.kotlin.activity.lifecycle

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer

/**
 * Created by YXN on 2018/5/22.
 */

class MyApp : Application() {

  val TAG = "App"

  object App
  override fun onCreate() {
    super.onCreate()
    Log.e(TAG, "$TAG--onCreate 程序开始")
    SDKInitializer.initialize(this)
    SDKInitializer.setCoordType(CoordType.BD09LL)
//    加载矢量图标库
  }



  override fun onLowMemory() {
    super.onLowMemory()
    Log.e(TAG, "$TAG--onLowMemory 低内存时执行")
  }

  override fun onTerminate() {
    super.onTerminate()
    //模拟一个过程环境，在真机中不会被调用
    Log.e(TAG, "$TAG--onTerminate 程序终止")
  }

  override fun onTrimMemory(level: Int) {
    super.onTrimMemory(level)
    Log.e(TAG, "$TAG--onTrimMemory 程序清理，回收内存")
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
    Log.e(TAG, "$TAG--onConfigurationChanged")
  }

}
