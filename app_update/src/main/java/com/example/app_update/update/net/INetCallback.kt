package com.example.app_update.update.net

interface INetCallback {
    fun onSuccess(response: String?)
    fun onFailed(throwable: Throwable?)
}