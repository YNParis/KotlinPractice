#include "q_appc808.h"

int q_apc808_get_register(char *i_app_id,char *i_app_user_id,char *i_imei,uint32_t o_buf_len,unsigned char *o_buf)
{
  uint32_t          len=0;
  unsigned char     msg_id[2];
  unsigned char     *p=NULL;

  if ( o_buf_len < 128 )
  {
    return(-1);
  }
  memset(o_buf,0,o_buf_len);
  p = o_buf;

  /* skip msg_head */
  p += MSG_HEAD_len;

  memcpy(p,i_app_id,2);
  p += 2;

  len = strlen(i_app_user_id);
  memcpy(p,i_app_user_id,len);
  p += len;

  memcpy(p,i_imei,IMEI_LEN);
  p += IMEI_LEN;

  memset(msg_id,0,2);
  msg_id[0] = 0X01;
  msg_id[1] = 0X00;

  p = o_buf;

  len = q_apc808_get_head(msg_id,i_imei,i_app_id,i_app_user_id,37,o_buf);

  len += MSG_HEAD_len; 
  len = q_apc808_get_crc(len,o_buf);

  return(len);
}
