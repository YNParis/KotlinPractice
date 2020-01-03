/**
 * FileName: DrawView
 * Author: Administrator
 * Date: 2020/1/3 15:35
 * Description: A custom view that a ball moving by finger.
 */
package com.demos.yxn.lifecircle.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawView : View {

  constructor(context: Context) : super(context)
  constructor(
    context: Context,
    set: AttributeSet
  ) : super(context, set)

  private var currentX = 40f
  private var currentY = 50f

  private var paint = Paint()

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    paint.color = Color.BLACK
    paint.strokeWidth = 20f
    canvas?.drawCircle(currentX, currentY, 15f, paint)
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    currentX = event?.x!!
    currentY = event.y
    invalidate()
    return true

  }
}