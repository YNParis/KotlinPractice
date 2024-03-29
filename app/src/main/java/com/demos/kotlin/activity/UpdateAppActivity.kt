package com.demos.kotlin.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.demos.kotlin.Constants
import com.demos.kotlin.R
import com.demos.kotlin.utils.ToastUtil
import com.example.app_update.update.AppUpdater
import com.example.app_update.update.bean.VersionDataBean
import com.example.app_update.update.net.INetCallback
import com.example.app_update.update.views.UpdateVersionDialog
import com.example.app_update.utils.AppUtils

/**
 * 版本更新
 */
class UpdateAppActivity : AppCompatActivity(), INetCallback {

    private lateinit var progressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        progressBar = ProgressDialog(this)
    }

    fun checkVersion(view: View) {
        progressBar.show()
        progressBar.setContentView(R.layout.layout_progress_bar)
        AppUpdater.instance.netManager[Constants.NEW_VERSION_URL, this, this]
    }

    override fun onSuccess(response: String?) {
        progressBar.dismiss()
        val dataBean = VersionDataBean.parse(response)
        if (dataBean?.versionCode == null || dataBean.versionCode.toLong() <= AppUtils.getVersionCode(this)) return
        UpdateVersionDialog.show(this, dataBean)
    }

    override fun onFailed(throwable: Throwable?) {
        progressBar.dismiss()
        ToastUtil.show(this, "获取版本信息失败")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppUpdater.instance.netManager.cancel(this)
    }
}
