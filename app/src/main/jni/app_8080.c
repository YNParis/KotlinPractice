#include "q_app808.h"

int q_ap808_hello(double i_lon, double i_lat, int o_buf_len, char *o_buf) {
    int len = 0;
    memset(o_buf, 0, o_buf_len);
    len = sprintf(o_buf, "qiaoshui test lon=[%.5f] lat=[%.5f] \n", i_lon, i_lat);
    return (len);
}