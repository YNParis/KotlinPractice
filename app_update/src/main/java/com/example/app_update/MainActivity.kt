package com.example.app_update

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_update.update.AppUpdater.Companion.instance
import com.example.app_update.update.bean.VersionDataBean.Companion.parse
import com.example.app_update.update.net.INetCallback
import com.example.app_update.update.views.UpdateVersionDialog.Companion.show
import com.example.app_update.utils.AppUtils.getVersionCode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_update).setOnClickListener {
            instance.netManager["http://59.110.162.30/app_updater_version.json", object : INetCallback {
                override fun onSuccess(response: String?) {
                    //TODO 查询成功，解析数据
                    //{
                    //    "title":"4.5.0更新啦！",
                    //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
                    //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
                    //    "md5":"14480fc08932105d55b9217c6d2fb90b",
                    //    "versionCode":"450"
                    //}
                    //比较版本号
                    //需要更新时，弹框
                    Log.e("net", response)
                    //解析
                    val dataBean = parse(response)
                    //比较
                    try {
                        if (getVersionCode(this@MainActivity) >= dataBean!!.versionCode!!.toLong()) {
                            Toast.makeText(this@MainActivity, "当前已是最新版本", Toast.LENGTH_SHORT).show()
                            return
                        }
                        //弹框
                        show(this@MainActivity, dataBean)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@MainActivity, "版本信息数据异常", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailed(throwable: Throwable?) {
                    Toast.makeText(this@MainActivity, "查询版本信息失败", Toast.LENGTH_SHORT).show()
                }
            }, this@MainActivity]
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance.netManager.cancel(this)
    }
}