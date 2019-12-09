package com.demos.yxn.lifecircle.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.demos.yxn.lifecircle.IConntectionService;
import com.demos.yxn.lifecircle.R;

public class IPCActivity extends AppCompatActivity {

    private IConntectionService iConnectionServiceProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        bindService(new Intent(this, RemoteService.class), new ServiceConnection() {
            @Override public void onServiceConnected(ComponentName name, IBinder service) {
                iConnectionServiceProxy = IConntectionService.Stub.asInterface(service);
            }

            @Override public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void connect(View view) {
        try {
            iConnectionServiceProxy.connect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(View view) {
        try {
            iConnectionServiceProxy.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void isConnected(View view) {
        boolean isConnected = false;
        try {
            isConnected = iConnectionServiceProxy.isConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, String.valueOf(isConnected), Toast.LENGTH_SHORT).show();
    }
}
