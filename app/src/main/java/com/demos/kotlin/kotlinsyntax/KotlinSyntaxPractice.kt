package com.demos.kotlin.kotlinsyntax

import android.util.Log
import com.demos.kotlin.lOG_TAG


/**
 * 集合操作，执行集合的forEach，map，flatMap，filter
 */
fun collectionOperation() {
    val intArray = intArrayOf(4, 3, 2, 1)

    intArray.forEach { Log.e(lOG_TAG, "intArray-$it") }
    val mapList = intArray.map { it * 2 }
    val filterList = intArray.filter { it % 2 == 0 }
    val flatMapList = intArray.flatMap { listOf(it, it * 5) }

    mapList.forEach { Log.e(lOG_TAG, "mapList -$it") }
    /*
     mapList -8
    mapList -6
    mapList -4
    mapList -2
     */
    filterList.forEach { Log.e(lOG_TAG, "filterList -$it") }
    flatMapList.forEach { Log.e(lOG_TAG, "flatMapList -$it") }
    /*flatMapList -4
    flatMapList -20
    flatMapList -3
    flatMapList -15
    flatMapList -2
    flatMapList -10
    flatMapList -1
    flatMapList -5*/
    intArray.forEach { Log.e(lOG_TAG, "intArray-$it") }
    Log.e(lOG_TAG, "flatMapList -${flatMapList.size}")
    /*flatMapList -8*/
}

fun filterOperation() {
    val intArray = intArrayOf(21, 40, 11, 33, 78)
    val filterList = intArray.filter { it % 3 == 0 }
    filterList.forEach {
        Log.e(lOG_TAG, "$it")
    }
}

val sequence = sequenceOf(1, 2, 3, 4)
val result: Sequence<Int> = sequence.map { it ->
    Log.e(lOG_TAG, "map $it")
    it * 2
}.filter { it ->
    Log.e(lOG_TAG, "filter $it")
    it % 4 == 0
}

/**
 * 惰性集合Sequence
 * 在调用result的时候，才会执行定义时的操作
 * 每一个调用result都会执行定义时的操作，在满满足条件时终止，不继续执行接下来的循环
 */
fun sequenceOperation() {
    result.forEach { Log.e(lOG_TAG, "result $it") }
    Log.e(lOG_TAG, "result.first ${result.first()}")
}

/**
 * 定义一个类，含构造方法
 */
class Student constructor(val name: String, val gender: String, val age: Int) {

    fun show() {
        Log.e(lOG_TAG, "Student:name=$name,age=${this.age},gender=$gender")
    }
}
