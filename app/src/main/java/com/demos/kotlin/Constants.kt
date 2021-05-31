package com.demos.kotlin

/**
 * top-level declaration，顶层声明。
 * 定义的方法及变量不属于某个类，直接属于包，在整个应用包的任何一个类中都可以直接调用而不需要声明类名。
 */
const val lOG_TAG = "KOTLIN_PRACTICE_LOG"

class Constants {

    companion object {
        const val NEW_VERSION_URL = "http://59.110.162.30/app_updater_version.json"
        const val lOG_TAG = "KOTLIN_PRACTICE_LOG"
    }

    object AConstants {
        const val lOG_TAG = "a.tag"
    }

    object BConstants {
        const val lOG_TAG = "b.tag"
    }
}

object Const2 {
    const val lOG_TAG = "KOTLIN_PRACTICE_LOG"
}