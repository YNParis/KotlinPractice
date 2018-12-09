#include "q_appc808.h"

int q_apc808_get_bat_pos(uint32_t i_num, double i_lon[],double i_lat[],uint32_t i_altitude[],uint32_t i_speed[],
    uint32_t i_direction[],char **i_gps_time,uint32_t o_buf_len,unsigned char *o_buf)
{
  uint32_t          len=0,i=0;
  uint32_t          v_val=0;
  unsigned char     msg_id[2],tmp[8];
  unsigned char     *p=NULL;

  if ( o_buf_len < 1024 )
  {
    return(-1);
  }
  memset(o_buf,0,o_buf_len);
  p = o_buf;

  /* skip msg_head */
  p += MSG_HEAD_len;

  /* pos data number */
  q_apc808_set_uint16(i_num,p);
  p += 2; len += 2;

  /* data type */
  *p = 0; p++; len++;

  for(i=0;i<i_num;i++)
  {
    /* position data lenght */
    q_apc808_set_uint16(28,p);
    p += 2; len +=2 ;

    /* skip alarm flag , 4 char */
    p += 4; len += 4;

    /* status flag , 4 char */
    p += 3;
    *p = 0X02; p++; len += 4;

    /* v_latitude weidu 纬度 */
    v_val = i_lat[i] * 1000000;
    q_apc808_set_uint32(v_val,p);
    p += 4; len += 4;

    /* v_longitude jingdu 经度 */
    v_val = i_lon[i] * 1000000;
    q_apc808_set_uint32(v_val,p);
    p += 4; len += 4;

    /* altitude  */
    q_apc808_set_uint16(i_altitude[i],p);
    p += 2; len += 2;

    /* speed  */
    q_apc808_set_uint16(i_speed[i],p);
    p += 2; len += 2;

    /* direction  */
    q_apc808_set_uint16(i_direction[i],p);
    p += 2; len += 2;

    /* gps_time yy */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+2,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;

    /* gps_time mm */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+4,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;

    /* gps_time dd */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+6,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;

    /* gps_time hh */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+8,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;

    /* gps_time mi */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+10,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;

    /* gps_time ss */
    memset(tmp,0,8);
    memcpy(tmp,i_gps_time[i]+12,2);
    v_val = atoi((char *)tmp);
    q_apc808_dec2bcd(v_val,1,&tmp[4]);
    *p = tmp[4]; p++; len++;
  }

  memset(msg_id,0,2);
  msg_id[0] = 0X07;
  msg_id[1] = 0X04;

  p = o_buf;

  len += q_apc808_get_head(msg_id,len,o_buf);

  len = q_apc808_get_crc(len,o_buf);

  return(len);
}
