package com.demos.kotlin.kotlinsyntax

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.demos.kotlin.R
import com.demos.kotlin.algorithm.champagneTower
import com.demos.kotlin.algorithm.threeSum

class KotlinSyntaxActivity : AppCompatActivity() {

  //lateinit关键字修饰，表示之后再初始化赋值。但基本数据类型不可用该关键字修饰
  //lateinit修饰符只能修饰不可空类型，并且不允许修饰基础类型
  private lateinit var textView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main3)

    println(cook("青椒") { name, time ->
      println("将${name}放入锅内")
      println("翻炒${time}分钟")
      true
    })
    println(threeSum(intArrayOf(-2, 0, 1, 1, 2)).toString())
    println("香槟塔结果：${champagneTower(4, 0, 0)}")
    println("香槟塔结果：${champagneTower(1, 1, 1)}")
    println("香槟塔结果：${champagneTower(2, 1, 1)}")
    println("香槟塔结果：${champagneTower(0, 1, 1)}")
    println("香槟塔结果：${champagneTower(9999, 70, 70)}")
    println("香槟塔结果：${champagneTower(1000000000, 99, 99)}")
    println("香槟塔结果：${champagneTower(10000, 20, 15)}")
  }

}
