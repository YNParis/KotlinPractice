package com.demos.yxn.lifecircle.kotlin

/**
 * Created by YXN on 2018/10/30.
 */

/**
 * 做饭的流程，返回值为空
 * 参数中方法的返回值类型不能为空
 * lambda中，实现方法的最后一行默认返回
 */
fun cook(name: String, action: (name: String, time: Int) -> Boolean) {

    println("开始做炒$name")
    println("冷锅热油")
    println("炝炒葱姜蒜")
    action("青椒", 3)
    println("关火")
    println("装盘")

}

