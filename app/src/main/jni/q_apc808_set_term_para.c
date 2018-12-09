#include "q_appc808.h"

int q_apc808_set_term_para(uint16_t i_msg_no,unsigned char *i_buf,unsigned char *o_buf)
{
  uint32_t        i=0,msg_id=0,len=0;
  unsigned char   v_msg_id[2];
  unsigned char   *p=NULL,*q=i_buf;

  p = i_buf+1;

  for(i=0;i<*q;i++)
  {
    msg_id = q_apc808_get_uint32(p);
    p += 4;
    len = *p; p++;
    
    /* heart */
    if ( msg_id == 0X0001 )
    {
      apc808Cfg->heart = q_apc808_get_uint32(p);
      p += len;
    }
    else if ( msg_id == 0X0013 )
    {
      memcpy(apc808Cfg->s_ip,p,len);
      p += len;
    }
    else if ( msg_id == 0x0018 )
    {
      apc808Cfg->s_port = q_apc808_get_uint32(p);
      p += len;
    }
    else if ( msg_id == 0X0029 )
    {
      apc808Cfg->upd_time = q_apc808_get_uint32(p);
      p += len;
    }
  }

  q = o_buf + MSG_HEAD_len;
  len = i_msg_no;
  q_apc808_set_uint16(len,q);
  q += 2;

  len = 0X8103;
  q_apc808_set_uint16(len,q);
  q += 2;

  *q = 0;

  memset(v_msg_id,0,2);
  v_msg_id[0] = 0X02;
  v_msg_id[1] = 0X00;
  len = 5;
  
  len += q_apc808_get_head(v_msg_id,len,o_buf);

  len = q_apc808_get_crc(len,o_buf);

  return(len);
}
