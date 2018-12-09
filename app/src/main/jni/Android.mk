#include "com_app808_HelloNdk.h"
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := hellondk
LOCAL_SRC_FILES := hello.c \
                   q_apc808_init.c\
                   	q_apc808_login.c			\
                   	q_apc808_uint16.c			\
                   	q_apc808_uint32.c			\
                   	q_apc808_dec2bcd.c			\
                   	q_apc808_get_crc.c			\
                   	q_apc808_get_head.c			\
                   	q_apc808_parse_cmd.c		\
                   	q_apc808_query_para.c		\
                   	q_apc808_answer_pos.c		\
                   	q_apc808_get_bat_pos.c		\
                   	q_apc808_get_register.c		\
                   	q_apc808_get_position.c		\
                   	q_apc808_set_term_para.c	\
                   	q_apc808_get_deregister.c

APP_PLATFORM := android-19

# for logging
LOCAL_LDLIBS += -llog
#LOCAL_LDLIBS :=-llog
include $(BUILD_SHARED_LIBRARY)

