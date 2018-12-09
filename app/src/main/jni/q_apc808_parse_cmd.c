#include "q_appc808.h"

int q_apc808_parse_cmd(uint32_t i_len,unsigned char *i_buf,uint32_t o_len,unsigned char *o_buf)
{
  uint32_t       len=0;
  uint16_t       msg_id=0,msg_no=0;
  uint16_t       ans_no=0,ans_msg_id=0;
  unsigned char  *p=NULL,ans_flag;

  p = i_buf + 1;

  msg_id = q_apc808_get_uint16(p);
  p += 2;

  if ( *p & 0X20 )
  {
    len += 512;
  }
  else if ( *p & 0X10 )
  {
    len += 256;
  }
  len += *(p+1); p += 2;

  if ( i_len < len )
  {
    return(-1);
  }

  /* skip imei */
  p += 15;

  /* skip app_id */
  p += 2;

  /* skip app_user_id */
  p += 20;

  /* msg_no */
  msg_no = q_apc808_get_uint16(p);
  p += 2;
  
  memset(o_buf,0,o_len);

  switch ( msg_id )
  {
    /* common answer */
    case 0X8001:
      ans_no = q_apc808_get_uint16(p);
      p += 2;
      ans_msg_id = q_apc808_get_uint16(p);
      p += 2;
      ans_flag = *p;
      
      return(RT_SUCCESS_NOMSG);

    /* register answer*/
    case 0X8100:
      ans_no = q_apc808_get_uint16(p);
      p += 2;
      if ( *p == 0 )
      {
        return(RT_SUCCESS_NOMSG);
      }
      else if ( *p == 1 )
      {
        return(RT_REG_FAILED);
      }

    /* set term para */
    case 0X8103:

      len = q_apc808_set_term_para(msg_no,p,o_buf);

      return(len);
    /* query term para */
    case 0X8106:
      len = q_apc808_query_para(msg_no,p,o_buf);
      return(len);
  }
  return(RT_SUCCESS_NOMSG);
}
