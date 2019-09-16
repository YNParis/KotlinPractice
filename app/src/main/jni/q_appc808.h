#include "stdint.h"
#include "stdlib.h"
#include "string.h"
#include "memory.h"
#include "stdio.h"

int g_msg_no;

#define IMEI_LEN          15
#define MSG_HEAD_len      43


int q_apc808_get_crc(uint32_t i_len,unsigned char *i_buf);
int q_apc808_login(char *i_app_id,char *i_app_user_id,char *i_imei,uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_register(char *i_app_id,char *i_app_user_id,char *i_imei,uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_deregister(char *i_app_id,char *i_app_user_id,char *i_imei,uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_head(unsigned char *msg_type_id,char *i_imei,char *i_app_id,char *i_app_user_id,uint32_t i_msg_len,unsigned char *o_head_buf);
int q_apc808_set_uint32(uint32_t i_val,unsigned char *o_buf);
int q_apc808_set_uint16(int i_val,unsigned char *i_buf);
int q_apc808_dec2bcd(uint32_t i_val,uint32_t i_len,unsigned char *o_buf);
int q_apc808_get_position(char *i_app_id,char *i_app_user_id,char *i_imei, double i_lon,double i_lat, uint32_t i_altitude,uint32_t i_speed, uint32_t i_direction,char *i_gps_time,uint32_t o_buf_len,unsigned char *o_buf);

uint16_t q_apc808_get_uint16(unsigned char *i_str);
uint32_t q_apc808_get_uint32(unsigned char *i_str);
