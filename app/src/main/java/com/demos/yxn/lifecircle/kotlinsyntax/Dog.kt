package com.demos.yxn.lifecircle.kotlinsyntax

/**
 * Created by YXN on 2018/10/18.
 */
class Dog(childName: String) : Animal() {

    init {
        println("Dog is init.")
    }

    constructor(childName: String, age: Int) : this(childName) {
        println("constructor is execute.")
        println("Dog's name is $childName and age is $age.")
    }

    override fun eat() {
        println("Dog can eat.")

    }

    override fun jump() {
        println("Dog can jump.")
    }
}