package com.demos.kotlin.kotlinsyntax

/**
 * Created by YXN on 2018/10/30.
 */
fun add(a: Int, b: Int): Int {
    return a + b
}

//single
fun add3(a: Int, b: Int, c: Int): Int = a + b + c

fun add4(a: Int, b: Int = 1, c: Int = 1): Int {
    return a + b + c
}

fun whenLoop(int: Int) {
    var a = int
    when (a) {
        in 2..10 -> print("$a 大于等于2，小于等于10")
        in 11 until 90 -> print("$a 大于等于11，小于90")
        else -> print(a)
    }
}

fun forLoop1() {
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    for (a in array) {
        println(a)
    }
}

fun forLoop2() {
    for (a in 1..20) {
        println(a)
    }
}

fun forLoopWithIndex() {
    val array = arrayOf(7, 6, 5, 4, 3)
    for ((i, j) in array.withIndex()) {
        println("index is $i and value is $j ")
    }
}

fun for_break() {
    out@ for (x in 1..5) {
        println("x =$x")
        for (i in 1..5) {
            println("i = $i")
            if (i == 3) {
                break@out
            }
        }
    }
}

fun while_loop() {
    var a = 10
    do {
        println(a)
        a--
    } while (a > 0)
}