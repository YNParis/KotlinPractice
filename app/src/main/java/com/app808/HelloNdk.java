package com.app808;

/**
 * Created by YXN on 2018/9/7.
 */

public class HelloNdk {

    static {
        System.loadLibrary("hellondk");
    }

    //调用接口得到
    public static native int getLen(double lon, double lat);

    //获取C返回的字符串，C中用char*字符数组表示
    public static native String getString(double lon, double lat);

}
