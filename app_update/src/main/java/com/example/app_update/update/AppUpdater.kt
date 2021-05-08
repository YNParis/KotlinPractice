package com.example.app_update.update

import com.example.app_update.update.net.INetManager
import com.example.app_update.update.net.OkHttpNetManager

/**
 * APP更新管理类。
 */
class AppUpdater {
    val netManager: INetManager = OkHttpNetManager()

    companion object {
        val instance = AppUpdater()
    }
}