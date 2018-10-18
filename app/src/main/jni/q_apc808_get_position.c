#include "q_appc808.h"

int q_apc808_get_position(char *i_app_id,char *i_app_user_id,char *i_imei,
    double i_lon,double i_lat,uint32_t i_altitude,uint32_t i_speed,
    uint32_t i_direction,char *i_gps_time,uint32_t o_buf_len,unsigned char *o_buf)
{
  uint32_t          len=0;
  uint32_t          v_val=0;
  unsigned char     msg_id[2],tmp[8];
  unsigned char     *p=NULL;

  if ( o_buf_len < 256 )
  {
    return(-1);
  }
  memset(o_buf,0,o_buf_len);
  p = o_buf;

  /* skip msg_head */
  p += MSG_HEAD_len;

  /* skip alarm flag , 4 char */
  p += 4; len = 4;

  /* status flag , 4 char */
  p += 3;
  *p = 0X02; p++; len += 4;

  /* v_latitude weidu 纬度 */
  v_val = i_lat * 1000000;
  q_apc808_set_uint32(v_val,p);
  p += 4; len += 4;

  /* v_longitude jingdu 经度 */
  v_val = i_lon * 1000000;
  q_apc808_set_uint32(v_val,p);
  p += 4; len += 4;

  /* altitude  */
  q_apc808_set_uint16(i_altitude,p);
  p += 2; len += 4;

  /* speed  */
  q_apc808_set_uint16(i_speed,p);
  p += 2; len += 4;

  /* direction  */
  q_apc808_set_uint16(i_direction,p);
  p += 2; len += 4;

  /* gps_time yy */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+2,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  /* gps_time mm */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+4,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  /* gps_time dd */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+6,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  /* gps_time hh */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+8,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  /* gps_time mi */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+10,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  /* gps_time ss */
  memset(tmp,0,8);
  memcpy(tmp,i_gps_time+12,2);
  v_val = atoi((char *)tmp);
  q_apc808_dec2bcd(v_val,1,&tmp[4]);
  *p = tmp[4]; p++; len++;

  memset(msg_id,0,2);
  msg_id[0] = 0X02;
  msg_id[1] = 0X00;

  p = o_buf;

  len = q_apc808_get_head(msg_id,i_imei,i_app_id,i_app_user_id,len,o_buf);

  len += MSG_HEAD_len; 
  len = q_apc808_get_crc(len,o_buf);

  return(len);
}
