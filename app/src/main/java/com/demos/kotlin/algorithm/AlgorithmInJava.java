package com.demos.kotlin.algorithm;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AlgorithmInJava {

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     * <p>
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * <p>
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "III"
     * 输出: 3
     * 示例 2:
     * <p>
     * 输入: "IV"
     *
     * @param s 输入的罗马字符串
     * @return
     */
    public int romanToInt(String s) {

        if (s.contains("IV")) {
            //4


            s = s.replace("IV", "IIII");
        }
        if (s.contains("IX")) {
            //9
            s = s.replace("IX", "IIIIV");

        }
        if (s.contains("XL")) {
            //40
            s = s.replace("XL", "XXXX");

        }
        if (s.contains("XC")) {
            //90
            s = s.replace("XC", "XXXXL");

        }
        if (s.contains("CD")) {
//400
            s = s.replace("CD", "CCCC");
        }
        if (s.contains("CM")) {
            //900
            s = s.replace("CM", "CCCCD");

        }
        char[] chars = new char[s.length()];
        int r = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        s.getChars(0, s.length(), chars, 0);
        for (char c : chars
        ) {
            if (map.containsKey(c)) {
                r += map.get(c);
            }

        }
        return r;
    }

    public int romanToInt2(String s) {
        char[] chars = new char[s.length()];
        int r = 0;
        for (int i = 0; i < chars.length; i++) {
            char thischar = chars[i];
            char nextchar = 'a';
            if (i + 1 != chars.length) {
                nextchar = chars[i + 1];
            }
            if (thischar == 'I') {
                if (nextchar == 'X') {
                    r += 9;
                    i++;
                } else if (nextchar == 'V') {
                    r += 4;
                    i++;
                } else {
                    r += 1;
                }
            } else if (thischar == 'X') {
                if (nextchar == 'C') {
                    r += 90;
                    i++;
                } else if (nextchar == 'L') {
                    r += 40;
                    i++;
                } else {
                    r += 10;
                }

            } else if (thischar == 'C') {
                if (nextchar == 'M') {
                    r += 900;
                    i++;
                } else if (nextchar == 'D') {
                    r += 400;
                    i++;
                } else {
                    r += 100;
                }

            }

        }
        return r;
    }

    /**
     * 获取每月等额本息的每月还款额
     *
     * @param principal    本金
     * @param year         贷款年限
     * @param yearInterest 贷款年利率
     */
    public static void getMonthPayEqual(double principal, int year, double yearInterest) {
        double monthInterest = yearInterest / 12.0;
        int month = year * 12;

        Log.e("repay", "每月等额本息还款方式");
        // 每月本息金额  = (本金×月利率×((1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
        double payMonthEqual = (principal * monthInterest * Math.pow(1 + monthInterest, month)) / (Math.pow(1 + monthInterest, month) - 1);
        Log.e("repay", "每月还款：" + payMonthEqual);
        // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
        for (int i = 1; i <= month; i++) {
            double monthPrincipal = principal * monthInterest * (Math.pow(1 + monthInterest, i - 1)) / (Math.pow(1 + monthInterest, month) - 1);
            Log.e("repay", "第" + i + "个月还款本金：" + monthPrincipal + "，利息：" + (payMonthEqual - monthPrincipal));
        }

        // 每月利息  = 剩余本金 x 贷款月利率
        Log.e("repay", "总利息：" + (payMonthEqual * month - principal));
        double totalInterest = (payMonthEqual * month - principal) / principal;
        Log.e("repay", "总利率：" + totalInterest);
    }

    /**
     * 等额本金还款
     *
     * @param principal    本金
     * @param year         贷款年限
     * @param yearInterest 贷款年利率
     */
    public static void getMonthPayEqualPrincipal(double principal, int year, double yearInterest) {
        double monthInterest = yearInterest / 12.0;
        int month = year * 12;

        Log.e("repay", "每月等额本金还款方式");
        double principalMonth = principal / month;
        Log.e("repay", "每月还本金：" + principalMonth);
        // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
        double leftPrincipal = principal;
        double interest = 0;
        for (int i = 1; i <= month; i++) {
            double monthInt = leftPrincipal * monthInterest;
            interest += monthInt;
            leftPrincipal -= principalMonth;
            Log.e("repay", "第" + i + "个月还款本息：" + (principalMonth + monthInt) + "，利息：" + monthInt);
        }

        // 每月利息  = 剩余本金 x 贷款月利率
        Log.e("repay", "总利息：" + interest);
        double totalInterest = interest / principal;
        Log.e("repay", "总利率：" + totalInterest);
    }

    /**
     * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）
     * 思路，用递归？
     * 官方解题：利用逻辑运算符的短路性质,&&,左边为true,执行右边,左边为false,不执行.
     */
    public int sumNums(int n) {
        boolean b = (n > 0) && ((n += sumNums(n - 1)) > 0);
        return n;
    }

    /**
     * 两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * <p>
     * 你可以按任意顺序返回答案。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            try {
                if (map.get(target - nums[i]) >= 0) {
                    return new int[]{map.get(target - nums[i]), i};
                }
            } catch (Exception e) {
                map.put(nums[i], i);
            }
        }
        return null;
    }

    /**
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
     * <p>
     * 示例 1:
     * <p>
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 示例 2:
     * <p>
     * 输入: strs = [""]
     * 输出: [[""]]
     * 示例 3:
     * <p>
     * 输入: strs = ["a"]
     * 输出: [["a"]]
     * <p>
     * 提示：
     * <p>
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     * <p>
     * <p>
     * TODO 没思路
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 1) {
            return Collections.singletonList(Collections.singletonList(strs[0]));
        }
        /*遍历：
         * 拿到一个数组，先分析其长度、含有的字母，字母个数。*/
        for (int i = 0, len = strs.length; i < len; i++) {
            char[] chars = strs[i].toCharArray();

        }
        return null;
    }

    private static HashMap getChar(String str) {
        char[] chars = str.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }
}
