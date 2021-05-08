package com.example.app_update.update.net

import java.io.File

/**
 * 下载管理接口类。
 */
interface IDownloadCallback {
    fun onSuccess(apkFile: File?)
    fun onFailed(throwable: Throwable?)
    fun onProcess(process: Int)
}