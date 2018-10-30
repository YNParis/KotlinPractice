package com.demos.yxn.lifecircle.kotlin

/**
 * Created by YXN on 2018/10/30.
 */

/**
 * 给定一个整型，输出该整形2进制表达式中1的位数
 */
fun getCount(n: Int): Int {
    var num = n
    var array = arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    for (i in 7..0) {
        array[i] = num % 2
        num = num / 2
        if (num < 2 && i > 0) {
            array[i - 1] = num
            break
        }
    }
    var sum = 0
    for (j in array) {
        sum = sum + array[j]
    }
    return sum
}