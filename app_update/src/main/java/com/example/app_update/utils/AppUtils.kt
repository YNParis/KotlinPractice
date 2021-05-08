package com.example.app_update.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

object AppUtils {
    /**
     * 获取版本号
     */
    @JvmStatic
    fun getVersionCode(context: Context): Long {
        val packageManager = context.packageManager
        try {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val versionCode: Long
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
            return versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 安装文件
     * 需要适配 N fileprovider；O install permission
     *
     * @param activity
     * @param apkFile
     */
    fun install(activity: FragmentActivity, apkFile: File?) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        var uri: Uri? = null

        //适配N，fileprovider
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(apkFile)
        } else {
            uri = FileProvider.getUriForFile(activity, activity.packageName + ".fileprovider", apkFile!!)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        activity.startActivity(intent)
    }

    /**
     * 获取文件的md5值
     *
     * @return 16进制的md5值
     */
    fun getFileMd5(file: File?): String? {
        if (file == null || !file.isFile) return null
        var digest: MessageDigest? = null
        var `in`: InputStream? = null
        val buffer = ByteArray(1024)
        var len = 0
        try {
            digest = MessageDigest.getInstance("MD5")
            `in` = FileInputStream(file)
            while (`in`.read(buffer).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        val result = digest!!.digest()
        val bigInteger = BigInteger(1, result)
        return bigInteger.toString(16)
    }
}