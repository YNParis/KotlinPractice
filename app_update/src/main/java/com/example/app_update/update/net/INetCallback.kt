package com.example.app_update.update.net;

public interface INetCallback {
    void onSuccess(String response);

    void onFailed(Throwable throwable);
}
