package com.demos.yxn.lifecircle.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.demos.yxn.lifecircle.R
import countA
import kotlinx.android.synthetic.main.activity_main3.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class Main3Activity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {


    //lateinit关键字修饰，表示之后再初始化赋值。但基本数据类型不可用该关键字修饰
    //lateinit修饰符只能修饰不可空类型，并且不允许修饰基础类型


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        println(cook("青椒", { name, time ->
            println("将${name}放入锅内")
            println("翻炒${time}分钟")
            true
        }))

        println("I have a dream.Are you have one too?".countA())

        btn_1.setOnClickListener(this)

        btn_2.setOnLongClickListener(this)

        checkBox.isChecked = false
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            toast("您点击了${if (isChecked) "勾选" else "取消"}按钮")
        }

        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            toast("${when (checkedId) {
                R.id.male -> "male"
                R.id.female -> "female"
                else -> ""
            }
            }")
        }

        tv_marquee.isSelected = true//必须加这一句，不然不滚

        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

    }

    override fun onLongClick(v: View?): Boolean {
        when (v) {
            btn_1 ->
                longToast("长按了${btn_1.text}")
            btn_2 ->
                toast("长按了${btn_2.text}")
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_1 ->
                toast("点击了第1个按钮")
            btn_2 ->
                toast("点击了第2个按钮")
        }
    }

}
