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
    public static int binarySearch1(int[] nums, int target) {
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

    /**
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * <p>
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
     * <p>
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
     * <p>
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * <p>
     */
    public static int search(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] == target) return 0;
            return
                    -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < nums[right]) {
                /*转置点在左半部*/
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                /*转置点在右半部*/
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 模板2
     * 初始条件：left=0，right=length
     * 终止：left==right
     * 向左查找：right=mid
     * 向右查找：left=mid+1
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch2(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length;
        while (left < right) {
            // Prevent (left + right) overflow
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        // Post-processing:
        // End Condition: left == right
        if (left != nums.length && nums[left] == target) return left;
        return -1;
    }

    public static int firstBadVersion(int n) {
        int left = 0, right = n, res = -1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
                res = mid;
            } else {
                left = mid + 1;
            }
        }
        if (isBadVersion(left)) {
            return left;
        }
        return res;
    }

    public static boolean isBadVersion(int version) {
        return version >= 4;
    }

    /**
     * 峰值元素是指其值严格大于左右相邻值的元素。
     * <p>
     * 给你一个整数数组nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * <p>
     * 你可以假设nums[-1] = nums[n] = -∞ 。
     * <p>
     * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
     * <p>
     * <p>
     * 理解：
     * 1.时间复杂度为 O(log n)，采用二分查找；
     * 2.nums[-1] = nums[n] = -∞，这就代表着 只要数组中存在一个元素比相邻元素大，那么沿着它一定可以找到一个峰值。
     *
     * @param nums
     * @return
     */
    public static int findPeakElement(int[] nums) {
        if (nums.length == 1) return 0;
        if (nums.length == 2) {
            return nums[0] > nums[1] ? 0 : 1;
        }
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     * <p>
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
     * <p>
     * 你必须设计一个时间复杂度为O(log n) 的算法解决此问题。
     * <p>
     *
     * @param nums
     * @return
     */
    public static int findMin(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) return nums[0];
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                /*mid和right比较，若mid>right，说明旋转点在右半部，向右查找*/
                left = mid + 1;
            } else {
                /*mid和right比较，若mid<right，说明旋转点在左半部，向左查找*/
                right = mid;
            }
        }
        if (left != nums.length) return nums[left];
        return -1;
    }

}
