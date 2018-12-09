#include <q_app808.h>
#include <malloc.h>
#include "q_appc808.h"
#include<android/log.h>
#include <string.h>
#include <jni.h>

#ifndef _Included_com_app808_HelloNdk
#define _Included_com_app808_HelloNdk
#define TAG    "myhello-jni-test"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#ifdef __cplusplus
extern "C" {
#endif

//JavaVM *vm = NULL;
//jmethodID methodID;

char *str2char(JNIEnv *env, jclass clas, jstring str) {
    char *ch = NULL;
    clas = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "UTF-8");
    jmethodID mid = (*env)->GetMethodID(env, clas, "getBytes", "(Ljava/lang/String;)[B");

    //将ip转成char*
    jbyteArray barrip = (jbyteArray) (*env)->CallObjectMethod(env, str, mid, strencode);
    jsize alenip = (*env)->GetArrayLength(env, barrip);
    jbyte *baip = (*env)->GetByteArrayElements(env, barrip, JNI_FALSE);
    if (alenip > 0) {
        ch = (char *) malloc(alenip + 1);
        memcpy(ch, baip, alenip);
        ch[alenip] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barrip, baip, 0);
    return ch;
}

jbyteArray getByteArray(JNIEnv *env, char ch[], int size, int len) {
    jbyteArray jba = (*env)->NewByteArray(env, size * 2);
    (*env)->SetByteArrayRegion(env, jba, 0, len, ch);
    return jba;
}


JNIEXPORT void JNICALL Java_com_app808_HelloNdk_cInit
        (JNIEnv *env, jclass clas, jstring userId, jstring phone, jint updtime, jint port,
         jstring ip) {
//初始化数据
    char *phoneChar = str2char(env, clas, phone);
    char *ipchar = str2char(env, clas, ip);
    char *userIdChar = str2char(env, clas, userId);
    q_apc808_init(1, userIdChar, phoneChar, updtime, port, ipchar);
    LOGE("init userIdChar=%s,phoneChar=%s,ipchar=%s", userIdChar, phoneChar, ipchar);
}

/*
 * Class:     com_app808_HelloNdk
 * Method:    cLogin
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jbyteArray JNICALL Java_com_app808_HelloNdk_cLogin
        (JNIEnv *env, jclass clas) {
//获取登录信息，length需>=128
    char chr[512];
    int l = q_apc808_login(512, chr);
    jbyteArray jba = (*env)->NewByteArray(env, 1024);
    (*env)->SetByteArrayRegion(env, jba, 0, l, chr);
    return jba;
}

JNIEXPORT jint JNICALL Java_com_app808_HelloNdk_cLoginLen
        (JNIEnv *env, jclass clas) {
//获取登录信息，length需>=128
    char chr[512];
    return q_apc808_login(512, chr);
}

/*
 * Class:     com_app808_HelloNdk
 * Method:    cParseCmd
 * Signature: (ILjava/lang/String;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_app808_HelloNdk_cParseCmd
        (JNIEnv *env, jclass clas, jint lengthReceived, jbyteArray stringReceived) {
    //获取消息，lengthDefined>=256
//    char *charReceived = str2char(env, clas, stringReceived);
//    char charReceived[lengthReceived] = (*env)->
    char charDefined[512];
    char *charReceived;

    jsize alenip = (*env)->GetArrayLength(env, stringReceived);
    jbyte *baip = (*env)->GetByteArrayElements(env, stringReceived, JNI_FALSE);
    if (alenip > 0) {
        charReceived = (char *) malloc(alenip + 1);
        memcpy(charReceived, baip, alenip);
        charReceived[alenip] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, stringReceived, baip, 0);

    int l = q_apc808_parse_cmd(lengthReceived, charReceived, 512, charDefined);
    return l;
}


/*
 * Class:     com_app808_HelloNdk
 * Method:    cGetPositon
 * Signature: (DDDDDLjava/lang/String;ILjava/lang/String;)I
 */
JNIEXPORT jbyteArray JNICALL Java_com_app808_HelloNdk_cGetPositon
        (JNIEnv *env, jclass clas, jdouble lon, jdouble lat, jdouble altitude, jdouble speed,
         jdouble direction, jstring gpsTime) {
    //获取GPS信息
    char *charTime = str2char(env, clas, gpsTime);
    char charDefined[256];

    int l = q_apc808_get_position(lon, lat, altitude, speed, direction, charTime, 256,
                                  charDefined);
    jbyteArray jba = (*env)->NewByteArray(env, 512);
    (*env)->SetByteArrayRegion(env, jba, 0, l, charDefined);
    return getByteArray(env, charDefined, 512, l);
}

JNIEXPORT jint JNICALL Java_com_app808_HelloNdk_cGetPositonLen
        (JNIEnv *env, jclass clas, jdouble lon, jdouble lat, jdouble altitude, jdouble speed,
         jdouble direction, jstring gpsTime) {
    //获取GPS信息
    char *charTime = str2char(env, clas, gpsTime);
    char charDefined[256];
    return q_apc808_get_position(lon, lat, altitude, speed, direction, charTime, 256,
                                 charDefined);
}

#ifdef __cplusplus
}
#endif
#endif


