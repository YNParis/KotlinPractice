package com.demos.yxn.lifecircle;

//连接服务

interface IConntectionService {

//加oneway关键字，表示主进程不会阻塞，不关心子进程执行过程
oneway void connect();
void disconnect();
boolean isConnected();

}
