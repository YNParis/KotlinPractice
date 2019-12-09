package com.demos.yxn.lifecircle.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;
import com.demos.yxn.lifecircle.IConntectionService;

/**
 * 核心功能 管理和提供子进程的连接和消息服务
 */
public class RemoteService extends Service {
    public RemoteService() {
    }

    private boolean isConnected;
    private Handler handler = new Handler(Looper.getMainLooper());
    private IConntectionService service = new IConntectionService.Stub() {
        @Override public void connect() throws RemoteException {
            try {
                Thread.sleep(5000);
                isConnected = true;
                handler.post(new Runnable() {
                    @Override public void run() {
                        Toast.makeText(RemoteService.this, "connect", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override public void disconnect() throws RemoteException {
            isConnected = false;
            //跑在子进程的一个线程中，不会在主进程中打印，需要用handler
            //Toast.makeText(RemoteService.this, "disconnect", Toast.LENGTH_SHORT).show();
            handler.post(new Runnable() {
                @Override public void run() {
                    Toast.makeText(RemoteService.this, "disconnect", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override public boolean isConnected() throws RemoteException {
            return isConnected;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return service.asBinder();
    }
}
