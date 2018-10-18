#include "q_appc808.h"

int q_apc808_dec2bcd(uint32_t i_val,uint32_t i_len,unsigned char *o_buf)
{
  int i=0,tmp=0;

  for (i = i_len - 1; i >= 0; i--)
  {
    tmp = i_val % 100;
    o_buf[i] = ((tmp / 10) << 4) + ((tmp % 10) & 0x0F);
    i_val /= 100;
  }
  return 1;
}
