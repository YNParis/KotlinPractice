package com.demos.kotlin.bean

import rx.schedulers.TimeInterval as TimeInterval

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

/**
 * 看日期是否在某个时间段内
 */
fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}

class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start
            override fun hasNext(): Boolean = hasNext()

            override fun next(): MyDate {
                if (!hasNext()) {
                    throw NoSuchElementException("没有下一个了")
                }
                val result = current
//                current = current.followingDate()
                return current
            }
        }
    }
}

