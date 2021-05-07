package com.demos.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.demos.kotlin.R
import com.demos.kotlin.databinding.ActivityImagePickerBinding
import com.demos.kotlin.utils.ToastUtil
import com.demos.kotlin.utils.imagepicker.MultiImageSelector
import com.demos.kotlin.utils.imagepicker.MultiImageSelectorActivity
import java.io.File

class ImagePickerActivity : AppCompatActivity() {

    private lateinit var activityImagePickerBinding: ActivityImagePickerBinding
    private val imageList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityImagePickerBinding = ActivityImagePickerBinding.inflate(layoutInflater)
        setContentView(activityImagePickerBinding.root)
    }

    fun chooseImage(view: View) {
        MultiImageSelector.create().count(1).single().showCamera(true).origin(imageList).start(this, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            123 ->
                showImage(resultCode, data)
        }
    }

    private fun showImage(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            imageList.clear()
            imageList.addAll(data!!.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT))
            if (imageList.isNotEmpty())
                activityImagePickerBinding.imageView.setImageURI(Uri.fromFile(File(imageList[0])))
        } else {
            ToastUtil.show(this, "选择照片失败")
        }

    }
}