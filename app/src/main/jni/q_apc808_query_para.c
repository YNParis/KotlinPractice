#include "q_appc808.h"

int q_apc808_query_para(uint16_t i_msg_no,unsigned char *i_buf,unsigned char *o_buf)
{
  uint32_t        i=0,msg_id=0,len=0,rt_len=0;
  unsigned char   v_msg_id[2];
  unsigned char   *p=NULL,*q=i_buf,*rt=NULL;

  p = i_buf+1;

  rt = o_buf += MSG_HEAD_len; 

  len = i_msg_no;
  q_apc808_set_uint16(len,rt);
  rt += 2;

  *rt = *q; rt++;
  rt_len = 3;

  for(i=0;i<*q;i++)
  {
    msg_id = q_apc808_get_uint32(p);
    
    /* heart */
    if ( msg_id == 0X0001 )
    {
      memcpy(rt,p,4); rt += 4;
      *rt = 4; rt++;
      q_apc808_set_uint32(apc808Cfg->heart,rt);
      p += 4; rt += 4; rt_len += 9;
    }
    else if ( msg_id == 0X0013 )
    {
      len = strlen(apc808Cfg->s_ip);
      memcpy(rt,p,4); rt += 4;
      *rt = len; rt++; rt_len += 5;
      memcpy(rt,apc808Cfg->s_ip,len);
      rt += len; rt_len += len;
      p += 4;
    }
    else if ( msg_id == 0x0018 )
    {
      memcpy(rt,p,4); rt += 4;
      *rt = 4; rt++;
      q_apc808_set_uint32(apc808Cfg->s_port,rt);
      p += 4; rt += 4; rt_len += 9;
    }
    else if ( msg_id == 0X0029 )
    {
      memcpy(rt,p,4); rt += 4;
      *rt = 4; rt++;
      q_apc808_set_uint32(apc808Cfg->upd_time,rt);
      p += 4; rt += 4; rt_len += 9;
    }
  }

  memset(v_msg_id,0,2);
  v_msg_id[0] = 0X02;
  v_msg_id[1] = 0X00;
  
  rt_len += q_apc808_get_head(v_msg_id,rt_len,o_buf);

  rt_len = q_apc808_get_crc(rt_len,o_buf);

  return(len);
}
