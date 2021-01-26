package com.demos.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demos.kotlin.R
import com.demos.kotlin.utils.ToastUtil
import com.demos.kotlin.utils.imagepicker.MultiImageSelector
import com.demos.kotlin.utils.imagepicker.MultiImageSelectorActivity
import kotlinx.android.synthetic.main.activity_image_picker.*
import java.io.File

class ImagePickerActivity : AppCompatActivity() {

    private val imageList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
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
                imageView.setImageURI(Uri.fromFile(File(imageList[0])))
        } else {
            ToastUtil.show(this, "选择照片失败")
        }

    }
}