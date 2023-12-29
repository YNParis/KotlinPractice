package com.demos.kotlin.algorithm;

import java.security.PublicKey;
import java.util.LinkedHashMap;

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

    /**
     * 给定一个正整数n，输出外观数列的第n 项r外观数列，是一个整数序列，从数字1开始，序列中的每一项都是对前一项的描述你可以将其视作是由递归公式定义的数字字符串序
     * 列:
     * countAndSay(1)=“1“countAndSay(n)是对 countAndSay(n-1)的描述然后转换成另一个数字字符串。
     */
    public static String countAndSay1(int n) {
        if (n == 1) return "1";
        String last = "1";
        int i = 1;
        while (i < n) {
            String thisTime = "";
            for (char c : last.toCharArray()) {
                if (thisTime.length() != 0 && thisTime.charAt(thisTime.length() - 1) == c) {
                    thisTime = thisTime.substring(0, thisTime.length() - 2) + (thisTime.charAt(thisTime.length() - 2) - '0' + 1) + c;
                } else {
                    thisTime = thisTime + '1' + c;
                }
            }
            last = thisTime;
            i++;
        }
        return last;
    }

    public static String countAndSay(int n) {
        if (n == 1) return "1";
        String last = "1";
        return last;
    }
}
