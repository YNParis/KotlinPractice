package com.example.app_update.update.net

import android.os.Handler
import android.os.Looper
import okhttp3.*
import java.io.*
import java.util.concurrent.TimeUnit

class OkHttpNetManager : INetManager {
    companion object {
        private var sOkHttpClient: OkHttpClient? = null
        private val sHandler = Handler(Looper.getMainLooper())

        init {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(10, TimeUnit.SECONDS)
            sOkHttpClient = builder.build()
            //若访问的链接时https自签名形式的话，可能会出现连接问题。可以在builder中设置。
        }
    }

    override fun get(url: String?, callback: INetCallback, tag: Any?) {
        val builder = Request.Builder()
        val request = builder.get().url(url).tag(tag).build()
        val call = sOkHttpClient!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //非UI线程
                sHandler.post { callback.onFailed(e) }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val r = response.body()!!.string()
                    sHandler.post { callback.onSuccess(r) }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback.onFailed(e)
                }
            }
        })
    }

    override fun download(url: String?, targetFile: File?, callback: IDownloadCallback, tag: Any?) {
        if (!targetFile!!.exists()) {
            targetFile.parentFile.mkdirs()
        }
        val builder = Request.Builder()
        val request = builder.url(url).get().tag(tag).build()
        val call = sOkHttpClient!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                sHandler.post { callback.onFailed(e) }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var `is`: InputStream? = null
                var os: OutputStream? = null
                if (call.isCanceled) return
                try {
                    `is` = response.body()!!.byteStream()
                    os = FileOutputStream(targetFile)
                    val buffer = ByteArray(8 * 1024)
                    var curLen: Long = 0
                    var bufferLen = 0
                    val totalLen = response.body()!!.contentLength()
                    while (!call.isCanceled && `is`.read(buffer).also { bufferLen = it } != -1) {
                        os.write(buffer, 0, bufferLen)
                        os.flush()
                        curLen += bufferLen.toLong()
                        val finalCurLen = curLen
                        sHandler.post { callback.onProcess((finalCurLen * 1.0f / totalLen * 100).toInt()) }
                    }
                    if (call.isCanceled) return
                    try {
                        targetFile.setExecutable(true, false)
                        targetFile.setReadable(true, false)
                        targetFile.setWritable(true, false)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    sHandler.post { callback.onSuccess(targetFile) }
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (call.isCanceled) return
                    sHandler.post { callback.onFailed(e) }
                } finally {
                    `is`?.close()
                    os?.close()
                }
            }
        })
    }

    override fun cancel(tag: Any) {
        val queuedCalls = sOkHttpClient!!.dispatcher().queuedCalls()
        for (call in queuedCalls) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
        val runningCalls = sOkHttpClient!!.dispatcher().runningCalls()
        for (call in runningCalls) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
    }
}