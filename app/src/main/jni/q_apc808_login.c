#include "q_appc808.h"

int q_apc808_login(char *i_app_id,char *i_app_user_id,char *i_imei,uint32_t o_buf_len,unsigned char *o_buf)
{
  uint32_t          len=0;
  unsigned char     msg_id[2];

  if ( o_buf_len < 128 )
  {
    return(-1);
  }
  memset(o_buf,0,o_buf_len);

  g_msg_no = 0;

  memset(msg_id,0,2);
  msg_id[0] = 0X01;
  msg_id[1] = 0X02;

  len = q_apc808_get_head(msg_id,i_imei,i_app_id,i_app_user_id,0,o_buf);

  len += MSG_HEAD_len; 
  len = q_apc808_get_crc(len,o_buf);

  return(len);
}
