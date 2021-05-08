package com.example.app_update.update.views

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.app_update.R
import com.example.app_update.databinding.LayoutUpdateDialogBinding
import com.example.app_update.update.AppUpdater
import com.example.app_update.update.bean.VersionDataBean
import com.example.app_update.update.net.IDownloadCallback
import com.example.app_update.update.views.UpdateVersionDialog
import com.example.app_update.utils.AppUtils
import java.io.File

class UpdateVersionDialog : DialogFragment() {
    private lateinit var binding: LayoutUpdateDialogBinding
    private var dataBean: VersionDataBean? = null
    private var targetFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            dataBean = arguments.getSerializable(KEY) as VersionDataBean
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutUpdateDialogBinding.inflate(inflater, container, false)
        bindView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun bindView() {
        if (dataBean == null) return
        targetFile = File(activity!!.cacheDir, "update.apk")
        if (!targetFile!!.exists()) {
            targetFile!!.parentFile.mkdirs()
        }
        binding.dialogContent.text = dataBean!!.content
        binding.dialogTitle.text = dataBean!!.title
        binding.dialogBtn.setOnClickListener {
            it.isEnabled = false
            if (targetFile!!.exists() && AppUtils.getFileMd5(targetFile) == dataBean!!.md5) {
                //若文件存在，则不下载，直接安装
                AppUtils.install(activity!!, targetFile)
                dismiss()
                return@setOnClickListener
            }
            download(it)
        }
    }

    private fun download(view: View) {
        AppUpdater.instance.netManager.download(dataBean!!.url, targetFile, object : IDownloadCallback {
            override fun onSuccess(apkFile: File?) {
                view.isEnabled = true
                Log.e(DIALOG_TAG, apkFile!!.absolutePath)
                //MD5校验
                val fileMd5 = AppUtils.getFileMd5(apkFile)
                if (fileMd5 != null && fileMd5 == dataBean!!.md5) {
                    //安装
                    AppUtils.install(activity!!, apkFile)
                } else {
                    Toast.makeText(activity, "md5校验失败", Toast.LENGTH_SHORT).show()
                }
                dismiss()
            }

            override fun onFailed(throwable: Throwable?) {
                view.isEnabled = true
                Toast.makeText(activity, "下载失败", Toast.LENGTH_SHORT).show()
            }

            override fun onProcess(process: Int) {
                (view as TextView).text = "$process%"
                Log.e(DIALOG_TAG, "$process%")
            }
        }, this@UpdateVersionDialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //取消下载
        AppUpdater.instance.netManager.cancel(this)
    }

    companion object {
        private const val KEY = "dialog_key"
        private const val DIALOG_TAG = "update_version_dialog"

        @JvmStatic
        fun show(activity: FragmentActivity, bean: VersionDataBean?) {
            val bundle = Bundle()
            bundle.putSerializable(KEY, bean)
            val dialog = UpdateVersionDialog()
            dialog.arguments = bundle
            dialog.show(activity.supportFragmentManager, DIALOG_TAG)
        }
    }
}