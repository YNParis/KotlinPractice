#include <jni.h>
#include <q_app808.h>
#include <malloc.h>
#include "q_appc808.h"

#ifndef _Included_com_app808_HelloNdk
#define _Included_com_app808_HelloNdk
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_demos_yxn_lifecircle_ndktest_HelloNdk
 * Method:    sayHello
 * Signature: (DDI)I
 */

JNIEXPORT jint JNICALL Java_com_app808_HelloNdk_getLen
        (JNIEnv *env, jclass clas, jdouble lon, jdouble lat) {
    char buf[256];
//    char *pstr = (char *) malloc(256 * sizeof(char *));
    int len = 0;
    unsigned char buf_2[256];
    len = q_ap808_hello(100.01, 45.01, 256, buf);
    //下面这个方法执行后会报错，Android Fatal signal 11 (SIGSEGV), code 1, fault addr 0x56ac341e in tid 16860
    //可能是线程的关系，要写在主线程中
//    len = q_apc808_login(12, 13789898989, 122133144155166, 256, buf_2);
    return len;

}


#ifdef __cplusplus
}
#endif
#endif


