package com.demos.kotlin.algorithm;

public class Strings {
    /**
     * 给你两个字符串 haystack 和 needle ，请你在haystack 字符串中找出 needle 字符串的第一个匹配项的下标 (下标从0开始) 。如果 needle 不是haystack 的一部分，则返回 -1
     * 1 <= haystack.length, needle.length <= 104
     * haystack 和 needle 仅由小写英文字符组成
     * <p>
     * 朴素解法
     */
    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) return -1;
        int i = 0, needleLen = needle.length(), haystackLen = haystack.length();
        while (haystackLen - i >= needleLen) {
            int res = i;
            for (int j = 0; j < needleLen; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    res = -1;
                    break;
                }
            }
            if (res != -1) return res;
            i++;
        }
        return -1;
    }
}
