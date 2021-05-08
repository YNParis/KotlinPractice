package com.example.app_update.update.bean

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

data class VersionDataBean(
        val title: String,
        val url: String,
        val versionCode: String,
        val md5: String,
        val content: String
) : Serializable {

    companion object {
        //TODO 解析数据 {
        //    "title":"4.5.0更新啦！",
        //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
        //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
        //    "md5":"14480fc08932105d55b9217c6d2fb90b",
        //    "versionCode":"450"
        //}
        @JvmStatic
        fun parse(result: String?): VersionDataBean? {
            try {
                val repJson = JSONObject(result)
                val title = repJson.optString("title")
                val url = repJson.optString("url")
                val content = repJson.optString("content")
                val md5 = repJson.optString("md5")
                val versionCode = repJson.optString("versionCode")
                return VersionDataBean(title, url, versionCode, md5, content)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }
    }
}