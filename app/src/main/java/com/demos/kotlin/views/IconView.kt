/**
 * FileName: IconView
 * Author: Administrator
 * Date: 2020/1/6 16:54
 * Description: IconView using Alibaba iconfonts.
 */
package com.demos.kotlin.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class IconView : TextView {
  constructor(context: Context?) : super(context)
  constructor(
    context: Context?,
    attrs: AttributeSet?
  ) : super(context, attrs)

  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr)

  fun init() {
    //TODO 设置文字typeFace，引用ttf图标文件
//    typeface=MyApp.get
  }
}