package com.example.app_update.update.net;

import java.io.File;

/**
 * 下载管理接口类。
 */
public interface IDownloadCallback {
    void onSuccess(File apkFile);

    void onFailed(Throwable throwable);

    void onProcess(int process);
}
