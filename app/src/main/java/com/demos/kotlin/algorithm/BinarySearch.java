package com.demos.kotlin.algorithm;

/**
 * 二分查找
 */
public class BinarySearch {
    /*3个模板*/

    /**
     * 模板1:
     * 初始条件：left = 0, right = length-1
     * 终止：left > right
     * 向左查找：right = mid-1
     * 向右查找：left = mid+1
     */
    int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            // Prevent (left + right) overflow，不使用left + right防止内存溢出
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // End Condition: left > right
        return -1;
    }

    /**
     * 给你一个非负整数 x ，计算并返回x的 算术平方根 。
     * <p>
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * <p>
     * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
     * <p>
     *
     * @param x
     * @return x的平方根向下取整
     */
    public static int mySqrt(int x) {
        if (x == 0) return 0;
        int left = 0, right = x;/*初始条件*/
        /*中止条件：left>right*/
        /*向左查找：right=mid-1*/
        /*向右查找：left=mid+1*/
        /*比较条件：mid*mid==x；(mid-1)(mid-1)<x;*/
        int res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long s = (long) mid * mid;
            if (s == x) {
                res = mid;
                break;
            } else if (s < x) {
                left = mid + 1;
                res = mid;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }
}
