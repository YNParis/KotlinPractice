package com.example.app_update.update;

import com.example.app_update.update.net.OkHttpNetManager;
import com.example.app_update.update.net.INetManager;

/**
 * APP更新管理类。
 * 需要实现的方法：
 * 1.获取最新版本信息；
 * 2.下载/取消下载
 * 3.安装
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
