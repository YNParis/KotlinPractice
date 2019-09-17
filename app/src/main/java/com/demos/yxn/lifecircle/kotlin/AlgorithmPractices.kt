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
    sum += array[j]
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
    return IntArray(0)
  }
  nums1.sort()
  nums2.sort()


  if (nums1.size < nums2.size) {
    getSameMember(nums1, nums2)
  } else {
    getSameMember(nums2, nums1)
  }

  return IntArray(0)
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
    return IntArray(0)
  }
  //TODO 未完成
  for (i in 0 until shortArr.size) {
    if (shortArr[i] == longArr[li]) {
      shortArr[a] = shortArr[i]
      a++
    } else if (shortArr[i] > longArr[li]) {

    }
  }
  return IntArray(0)
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
  for (i in 0 until digits.size) {
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

/**
 *
 */
fun addDigits(num: Int): Int {
  var sum = 0
  do {

  } while (num > 9)
  return sum
}

/**
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * 说明：

num1 和 num2 的长度小于110。
num1 和 num2 只包含数字 0-9。
num1 和 num2 均不以零开头，除非是数字 0 本身。
不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/multiply-strings
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
下面这种方法会溢出
 */
fun multiple(
  num1: String,
  num2: String
): String {
  if (num1.isEmpty() || num2.isEmpty() || num1.length >= 110 || num2.length >= 110)
    return "illegal args"
  var a1: Long = 0
  var a2: Long = 0
  for (i in 0 until num1.length) {
    a1 += Math.round(
        (num1.elementAt(i).toLong() - 48) * (Math.pow(10.0, (num1.length - i).toDouble() - 1))
    )

  }
  for (i in 0 until num2.length) {
    a2 += Math.round(
        (num2.elementAt(i).toLong() - 48) * (Math.pow(10.0, (num2.length - i).toDouble() - 1))
    )
  }
  println(a1)
  println(a2)

  return (a1 * a2).toString()
}

/**
 * num1 和 num2 的长度小于110。
num1 和 num2 只包含数字 0-9。
num1 和 num2 均不以零开头，除非是数字 0 本身。
不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理

 * 以下采用列式计算，处理进位，将对应的结果存到数组中
 */
fun multiple2(
  num1: String,
  num2: String
): String {
  if (num1.length == 1 && num1.first() == '0' || num2.length == 1 && num2.first() == '0') {
    return "0"
  } else if (num1.length == 1 && num1.first() == '1') {
    return num2
  } else if (num2.length == 1 && num2.first() == '1') {
    return num1
  }
  val sum = Array((num1.length + num2.length)) { 0 }
  val a1 = Array((num1.length)) { 0 }
  val a2 = Array((num2.length)) { 0 }
  for (i in 0 until num1.length) {
    a1[i] = num1.elementAt(i).toInt() - 48
  }
  for (i in 0 until num2.length) {
    a2[i] = num2.elementAt(i).toInt() - 48
  }
  a1.reverse()
  a2.reverse()
  for (i in 0 until a1.size) {
    for (j in 0 until a2.size) {
      sum[i + j + 1] = (a1[i] * a2[j] + sum[i + j]) / 10 + sum[i + j + 1]
      sum[i + j] = (a1[i] * a2[j] + sum[i + j]) % 10
    }
  }
  val stringBuffer = StringBuilder()
  sum.reverse()
  val t = if (sum.first() == 0) 1 else 0
  for (i in t until sum.size)
    stringBuffer.append(sum[i])
  return stringBuffer.toString()
}

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。
思路，先排序
 */
fun threeSum(nums: IntArray): MutableList<List<Int>> {

  val listOfList = mutableListOf<List<Int>>()
  nums.sort()
  if (nums.size < 3) return listOfList
  if (nums.first() * nums.last() > 0)
    return listOfList
  if (nums.first() * nums.last() == 0 && nums.first() - nums.last() != 0) return listOfList
  for ((index, e) in nums.withIndex()) {
    if (index + 3 >= nums.size) break
    val target = -e
    var i = index + 1
    var j = nums.size - 1
    while (i < j) {
      val sum = nums.elementAt(i) + nums.elementAt(j)
      when {
        sum == target -> {
          val list = mutableListOf<Int>()
          list.add(e)
          list.add(nums.elementAt(i))
          list.add(nums.elementAt(j))
          if (!listOfList.contains(list))
            listOfList.add(list)
          i++
          j--
        }
        sum > target -> j--
        else -> i++
      }
    }
  }
  return listOfList
}