#include "q_appc808.h"

int q_apc808_get_crc(uint32_t i_len,unsigned char *i_buf)
{
  int             i=0,len=0;
  unsigned char   key;
  unsigned char   v_buf[2048];
  unsigned char   *p=NULL,*q=NULL;

  memset(v_buf,0,2048);

  p = i_buf + 1;

  v_buf[0] = 0X8E;
  q = v_buf+1; 
  len = 1;

  key = i_buf[0];

  if ( key == 0X8E )
  {
    *q = 0X8D; q++;
    *q = 0X02; q++;
    len+=2;
  }
  else if ( key == 0X8D )
  {
    *q = 0X8D; q++;
    *q = 0X01; q++;
    len+=2;
  }
  else
  {
    *q = key;
    q++; len++;
  }

  for(i=1;i<i_len;i++)
  {
    key = key ^ *p;

    if ( *p == 0X8E )
    {
      *q = 0X8D; q++;
      *q = 0X02;
      len++;
    }
    else if ( *p == 0X8D )
    {
      *q = 0X8D; q++;
      *q = 0X01;
      len++;
    }
    else
    {
      *q = *p;
    }
    q++; p++; len++;
  }

  *q = key; q++;

  *q = 0X8E;

  len += 2;

  memset(i_buf,0,len+1);
  memcpy(i_buf,v_buf,len);

  return(len);
}
