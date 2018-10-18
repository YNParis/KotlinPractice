#include "q_appc808.h"

int q_apc808_set_uint32(uint32_t i_val,unsigned char *o_buf)
{
  unsigned char  *p=NULL,*q=NULL;

  p = (unsigned char *)&i_val;
  q = o_buf+3;

  *q = *p;
  p++; q--;

  *q = *p;
  p++; q--;

  *q = *p;
  p++; q--;

  *q = *p;
  return(4);
}

uint32_t q_apc808_get_uint32(unsigned char *i_str)
{
  uint32_t        val=0;
  unsigned char   *p=NULL;

  p = (unsigned char *)&val;

  *p = i_str[3]; p++;
  *p = i_str[2]; p++;
  *p = i_str[1]; p++;
  *p = i_str[0];

  return(val);
}

