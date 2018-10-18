#include "q_appc808.h"

int q_apc808_get_head(unsigned char *msg_type_id,char *i_imei,char *i_app_id,char *i_app_user_id,uint32_t i_msg_len,unsigned char *o_head_buf)
{
  uint32_t         len=0;
  unsigned char    *p=NULL;

  p = o_head_buf;

  memcpy(p,msg_type_id,2);
  p += 2;

  /* msg attr , no split package , no rsa */
  if ( i_msg_len & 0X0200 )
  {
    *p = *p|0X02;
  }
  if ( i_msg_len & 0X0100 )
  {
    *p = *p|0X01;
  }
  p++;
  *p = i_msg_len&0XFF;
  p++;

  /* mobile IMEI */
  memcpy(p,i_imei,IMEI_LEN);
  p += 15;

  /* cnpc app id */
  memcpy(p,i_app_id,2);
  p += 2;

  /* app user id */
  len = strlen(i_app_user_id);
  memcpy(p,i_app_user_id,len);
  p += len;

  /* msg_no */
  *p = g_msg_no>>8&0Xff; p++;
  *p = g_msg_no&0Xff;    p++;
  g_msg_no++;

  if ( g_msg_no > 65535 )
  {
    g_msg_no = 0;
  }

  return(43);

}
