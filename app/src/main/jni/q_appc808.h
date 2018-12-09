#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <memory.h>
#include <stdint.h>

int g_msg_no;

typedef struct
{
  int       heart;
  int       msg_no;
  int       s_port;
  int       upd_time;
  uint16_t  app_id;
  char      s_ip[32];
  char      app_user_id[24];
  char      imei[16];
}APC808_CFG_TYPE;

APC808_CFG_TYPE   apc808Cfg[1];

#define IMEI_LEN          15
#define MSG_HEAD_len      43

#define RT_SUCCESS_NOMSG  0
#define RT_REG_FAILED     -1001

int q_apc808_init(uint32_t i_app_id,char *i_app_user_id,char *i_imei,int i_updtime,int i_port,char *i_ip);
int q_apc808_get_crc(uint32_t i_len,unsigned char *i_buf);
int q_apc808_login(uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_register(uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_deregister(uint32_t o_buf_len,unsigned char *o_buf);
int q_apc808_get_head(unsigned char *i_msg_type_id,uint32_t i_msg_len,unsigned char *o_head_buf);
int q_apc808_set_uint32(uint32_t i_val,unsigned char *o_buf);
int q_apc808_set_uint16(int i_val,unsigned char *i_buf);
int q_apc808_dec2bcd(uint32_t i_val,uint32_t i_len,unsigned char *o_buf);
int q_apc808_set_term_para(uint16_t i_msg_no,unsigned char *i_buf,unsigned char *o_buf);
int q_apc808_query_para(uint16_t i_msg_no,unsigned char *i_buf,unsigned char *o_buf);
int q_apc808_parse_cmd(uint32_t i_len,unsigned char *i_buf,uint32_t o_len,unsigned char *o_buf);

int q_apc808_get_position(double i_lon,
    double i_lat, uint32_t i_altitude,uint32_t i_speed, uint32_t i_direction,char *i_gps_time,
    uint32_t o_buf_len,unsigned char *o_buf);

int q_apc808_get_bat_pos(uint32_t i_num,
    double i_lon[],double i_lat[],uint32_t i_altitude[],uint32_t i_speed[],
    uint32_t i_direction[],char **i_gps_time,uint32_t o_buf_len,unsigned char *o_buf);

int q_apc808_answer_pos(uint32_t i_msg_no,
    double i_lon,double i_lat,uint32_t i_altitude,uint32_t i_speed,
    uint32_t i_direction,char *i_gps_time,uint32_t o_buf_len,unsigned char *o_buf);

uint16_t q_apc808_get_uint16(unsigned char *i_str);
uint32_t q_apc808_get_uint32(unsigned char *i_str);
