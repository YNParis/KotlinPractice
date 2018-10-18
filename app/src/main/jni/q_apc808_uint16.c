#include "q_appc808.h"

uint16_t q_apc808_get_uint16(unsigned char *i_str)
{
  uint16_t        val=0;
  unsigned char   *p=NULL;

  p = (unsigned char *)&val;

  *p = i_str[1]; p++;
  *p = i_str[0];

  return(val);
}

int q_apc808_set_uint16(int i_val,unsigned char *i_buf)
{
  unsigned char  *p=NULL,*q=NULL;

  p = (unsigned char *)&i_val;
  q = i_buf+1;

  *q = *p;
  p++; q--;
  *q = *p;

  return(2);
}
