#include "com_app808_HelloNdk.h"
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := hellondk
LOCAL_SRC_FILES := hello.c \
                   app_8080.c \
                   q_apc808_dec2bcd.c \
                   q_apc808_get_crc.c \
                   q_apc808_get_deregister.c \
                   q_apc808_get_head.c \
                   q_apc808_get_position.c \
                   q_apc808_get_register.c \
                   q_apc808_login.c \
                   q_apc808_uint16.c \
                   q_apc808_uint32.c \

APP_PLATFORM := android-19

# for logging
LOCAL_LDLIBS += -llog
include $(BUILD_SHARED_LIBRARY)

