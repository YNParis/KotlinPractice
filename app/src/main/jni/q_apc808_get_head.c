#include "q_appc808.h"

int q_apc808_get_head(unsigned char *i_msg_type_id,uint32_t i_msg_len,unsigned char *o_head_buf)
{
  unsigned char    *p=NULL;

  p = o_head_buf;

  memcpy(p,i_msg_type_id,2);
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
  memcpy(p,apc808Cfg->imei,IMEI_LEN);
  p += 15;

  /* cnpc app id */
  q_apc808_set_uint16(apc808Cfg->app_id,p);
  p += 2;

  /* app user id */
  memcpy(p,apc808Cfg->app_user_id,20);
  p += 20;

  /* msg_no */
  *p = apc808Cfg->msg_no>>8&0Xff; p++;
  *p = apc808Cfg->msg_no&0Xff;    p++;

  apc808Cfg->msg_no++;

  if ( apc808Cfg->msg_no > 65535 )
  {
    apc808Cfg->msg_no = 0;
  }

  return(43);

}
