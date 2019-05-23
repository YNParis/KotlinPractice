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

/**
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 */
fun removeDuplicates(nums: IntArray): Int {
  if (nums.isEmpty()) {
    return 0
  }
  var a = 0
  nums[a] = nums[0]
  for (i in 0..(nums.size - 1)) {
    if (nums[i] != nums[a]) {
      a++
      nums[a] = nums[i]
    }
  }
  return a + 1
}

/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 */
fun maxProfit(prices: IntArray): Int {
//TODO 未完成
  return 0
}

/**
 * 找两个数组中的重复元素
 */
fun intersect(
  nums1: IntArray,
  nums2: IntArray
): IntArray {
  if (nums1.isEmpty() or nums2.isEmpty()) {
    return kotlin.IntArray(0)
  }
  nums1.sort()
  nums2.sort()


  if (nums1.size < nums2.size) {
    getSameMember(nums1, nums2)
  } else {
    getSameMember(nums2, nums1)
  }

  return kotlin.IntArray(0)
}

fun getSameMember(
  shortArr: IntArray,
  longArr: IntArray
): IntArray {
  var si = 0
  var li = 0
  var a = 0
  val ll = shortArr.lastIndexOf(longArr.last())
  val sl = longArr.lastIndexOf(shortArr.last())
  if (ll == -1 && sl == -1) {
    return kotlin.IntArray(0)
  }
  //TODO 未完成
  for (i in 0..(shortArr.size - 1)) {
    if (shortArr[i] == longArr[li]) {
      shortArr[a] = shortArr[i]
      a++
    } else if (shortArr[i] > longArr[li]) {

    }
  }
  return kotlin.IntArray(0)
}

/**
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。

最高位数字存放在数组的首位， 数组中每个元素只存储一个数字。

你可以假设除了整数 0 之外，这个整数不会以零开头。

输入: [4,3,2,1]
输出: [4,3,2,2]
解释: 输入数组表示数字 4321。

输入: [9]
输出: [1,0]
解释: 输入数组表示数字 9。
 */
fun plusOne(digits: IntArray): IntArray {
  digits.reverse()
  for (i in 0..(digits.size - 1)) {
    digits[i] = (digits[i] + 1) % 10
    if (digits[i] != 0) {
      break
    } else if (i == digits.lastIndex) {
      return digits.plus(1).reversedArray()
    }
  }
  digits.reverse()
  return digits
}
