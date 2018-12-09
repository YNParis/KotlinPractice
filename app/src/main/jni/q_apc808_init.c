#include "q_appc808.h"

int q_apc808_init(uint32_t i_app_id,char *i_app_user_id,char *i_imei,int i_updtime,int i_port,char *i_ip)
{
  int    len=0;

  memset(apc808Cfg,0,sizeof(APC808_CFG_TYPE));

  apc808Cfg->app_id = i_app_id;

  len = strlen(i_app_user_id);

  if ( len > 20 )
  {
    memcpy(apc808Cfg->app_user_id,i_app_user_id,20);
  }
  else
  {
    strcpy(apc808Cfg->app_user_id,i_app_user_id);
  }

  memcpy(apc808Cfg->imei,i_imei,15);

  apc808Cfg->msg_no = 0;
  apc808Cfg->s_port = i_port;
  apc808Cfg->upd_time = i_updtime;
  strcpy(apc808Cfg->s_ip,i_ip);

  return(1);
}
