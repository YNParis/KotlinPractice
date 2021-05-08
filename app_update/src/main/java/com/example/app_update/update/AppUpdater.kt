package com.example.app_update.update;

import com.example.app_update.update.net.OkHttpNetManager;
import com.example.app_update.update.net.INetManager;

/**
 * APP更新管理类。
 */
public class AppUpdater {

    private static AppUpdater sInstance = new AppUpdater();

    public static AppUpdater getInstance() {
        return sInstance;
    }

    private INetManager netManager = new OkHttpNetManager();

    public INetManager getNetManager() {
        return netManager;
    }


}
