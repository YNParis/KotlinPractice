/**
 * FileName: SignView
 * Author: Administrator
 * Date: 2020/1/6 15:20
 * Description: A sign view, the result can be saved as a image file.
 */
package com.demos.kotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SignView : View {

  var paint = Paint()

  constructor(context: Context?) : super(context)
  constructor(
    context: Context?,
    attrs: AttributeSet?
  ) : super(context, attrs)

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    return super.onTouchEvent(event)
  }

}