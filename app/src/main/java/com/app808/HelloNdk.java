package com.app808;

/**
 * Created by YXN on 2018/9/7.
 */

public class HelloNdk {

    static {
        System.loadLibrary("hellondk");
    }

    //初始化数据
    public static native void cInit(String userId, String phone, int updtime, int port, String ip);

    //获取登录信息，length需>=128
    public static native byte[] cLogin();

    public static native int cLoginLen();

    //获取消息，lengthDefined>=256
    public static native int cParseCmd(int lengthReceived, byte[] received);

    //获取GPS信息
    public static native byte[] cGetPositon(double lon, double lat, double altitude, double speed, double direction, String gpsTime);

    public static native int cGetPositonLen(double lon, double lat, double altitude, double speed, double direction, String gpsTime);


}
