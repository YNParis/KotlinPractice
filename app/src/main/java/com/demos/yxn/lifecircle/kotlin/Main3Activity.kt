package com.demos.yxn.lifecircle.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.demos.yxn.lifecircle.R
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import java.util.concurrent.Executors

class Main3Activity : AppCompatActivity() {

    //lateinit关键字修饰，表示之后再初始化赋值。但基本数据类型不可用该关键字修饰
    //lateinit修饰符只能修饰不可空类型，并且不允许修饰基础类型
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        println(cook("青椒", { name, time ->
            println("将${name}放入锅内")
            println("翻炒${time}分钟")
            true
        }))

    }

    fun thread(view: View) {
        val pool = Executors.newFixedThreadPool(10)//线程池中最大并发数
        for (i in 0..1000) {
            pool.execute {
                write_sq("thread", i.toString(), i)
            }
        }

    }

    fun coroutines(view: View) {
        val pool = newFixedThreadPoolContext(10, "coroutine")
        for (i in 0..1000) {
            pool.run {
                write_sq("coroutine", i.toString(), i)
            }
        }
    }

    fun write_sq(way: String, key: String, value: Int) {
        val sp = getSharedPreferences("jikexueyuan", Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).apply()
        val value = sp.getInt(key, 0)
        println("调用方式为${way},key的值为${key},取出的值为${value}")
    }

}
