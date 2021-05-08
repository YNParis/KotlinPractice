package com.example.app_update.update.net

import java.io.File

/**
 * 网络管理类。
 * 实现功能：
 * 1.请求
 * 2.下载
 */
interface INetManager {
    /**
     * 请求最新版本信息
     *
     * @param url      url
     * @param callback 回调
     */
    operator fun get(url: String?, callback: INetCallback, tag: Any?)

    /**
     * 下载文件
     *
     * @param url      文件地址
     * @param callback 回调
     */
    fun download(url: String?, targetFile: File?, callback: IDownloadCallback, tag: Any?)

    /**
     * 取消请求，中断请求
     * @param tag
     */
    fun cancel(tag: Any)
}