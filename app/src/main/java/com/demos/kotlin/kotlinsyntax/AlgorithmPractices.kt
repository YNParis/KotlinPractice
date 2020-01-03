package com.demos.kotlin.kotlinsyntax

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
  for (i in 0 until nums.size) {
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
思路，先排序，遍历第一个数，在剩下的数组元素中用双指针，找剩下的两个数
初始执行时间2588ms

最终优化执行时间780ms
1.与上一个相同，则进行下次循环，加上这个能快600ms
2.相等时做下面的操作快1000ms
3.用MutableList比用ArrayList快70ms
 */
fun threeSum(nums: IntArray): MutableList<List<Int>> {
  val listOfList = ArrayList<List<Int>>()
  nums.sort()
  for (index in 0 until nums.size - 2) {
    if (nums[index] > 0) break
    //1.与上一个相同，则进行下次循环，加上这个能快600ms
    if (index > 0 && nums[index] == nums[index - 1]) continue
    var i = index + 1
    var j = nums.size - 1
    while (i < j) {
      val sum = nums[index] + nums[i] + nums[j]
      when {
        sum < 0 -> while (i < j && nums[i] == nums[++i]);//直到下一个不相等的nums[i]，因为相等的话，和这次的结果时一样的
        sum > 0 -> while (i < j && nums[j] == nums[--j]);//直到下一个不相等的nums[j]
        else -> {
          //2.这一步这样写能快1000ms
          listOfList.add(listOf(nums[index], nums[i], nums[j]))
          while (i < j && nums[i] == nums[++i]);
          while (i < j && nums[j] == nums[--j]);
        }
      }
    }
  }
  return listOfList
}

/**
 * 执行时间736ms的范例
 */
fun bestThreeSum(nums: IntArray): MutableList<List<Int>> {
  nums.sort()
  val res = ArrayList<List<Int>>()
  for (k in 0 until nums.size - 2) {
    if (nums[k] > 0) break
    if (k > 0 && nums[k] == nums[k - 1]) continue
    var start = k + 1
    var stop = nums.size - 1
    while (start < stop) {
      val sum = nums[k] + nums[start] + nums[stop]
      //use while can make 20 ms faster than if
      when {
        sum < 0 -> while (start < stop && nums[start] == nums[++start]);
        sum > 0 -> while (start < stop && nums[stop] == nums[--stop]);
        else -> {
          res.add(listOf(nums[k], nums[start], nums[stop]))
          while (start < stop && nums[start] == nums[++start]);
          while (start < stop && nums[stop] == nums[--stop]);
        }
      }
    }
  }
  return res
}

/**1033移动石子直到连续  236ms*/
fun numMovesStones(
  a: Int,
  b: Int,
  c: Int
): IntArray {
  val l: Int
  val r: Int
  val x = minOf(a, b, c)
  val z = maxOf(a, b, c)
  val y = a + b + c - x - z
  l = y - x - 1
  r = z - y - 1
  if (l == 0 && r == 0) {
    return intArrayOf(0, 0)
  } else if (l < 2 || r < 2) {
    return intArrayOf(1, l + r)
  }
  return intArrayOf(2, l + r)
}

/**
 * 给你个整数数组 arr，其中每个元素都 不相同。

请你找到所有具有最小绝对差的元素对，并且按升序的顺序返回。
 */
fun minimumAbsDifference(arr: IntArray): List<List<Int>> {
  arr.sort()
  val list = ArrayList<List<Int>>()
  var min = 0
  for (i in 1 until arr.size) {
    val d = Math.abs(arr[i] - arr[i - 1])
    if (min == 0 || min == d) {
      list.add(listOf(arr[i - 1], arr[i]))
      min = d
    } else if (min < d) {
      continue
    } else if (min > d) {
      list.clear()
      list.add(listOf(arr[i - 1], arr[i]))
      min = d
    }
  }
  return list
}

/**
 * 请你帮忙设计一个程序，用来找出第 n 个丑数。

丑数是可以被 a 或 b 或 c 整除的 正整数。
 */
fun nthUglyNumber(
  n: Int,
  a: Int,
  b: Int,
  c: Int
): Int {
  return 0
}

/**
 * 香槟塔，返回第i行第j列杯中香槟的比例
 * 错误原因：不是这一层满了，再流到下一层，每一个杯子得到香槟的速度是不一样的，越到中间，速度越快
 */
fun champagneTower(
  poured: Int,
  query_row: Int,
  query_glass: Int
): Double {
  if (query_glass > query_row) return 0.0
  val i = query_row + 1
  val j = query_glass + 1
  if (poured == 0) {
    return 0.0
  }
  if (i == 1) {
    return 1.0
  }
  val a = poured - i * (i - 1) / 2.0
  if (a <= 0.0) return 0.0
  else if (a >= i) return 1.0
  return when (j) {
    1, i -> a / (i - 1.0) / 2.0
    else -> a / (i - 1.0)
  }
}

/**
 * 香槟塔，返回第i行第j列杯中香槟的比例
 * 思路：
 */
fun champagneTower2(
  poured: Int,
  query_row: Int,
  query_glass: Int
): Double {

  var r = 0.0
  if (poured == 0) return r
  val array2d = Array(query_row + 1) { Array(query_row + 1) { 0.0 } }
  for (i in 0..query_row) {

  }

  return r
}
