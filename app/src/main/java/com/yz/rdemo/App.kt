package com.yz.rdemo

import android.app.Application
import android.content.Context
import com.yz.rdemo.net.ApiService
import com.yz.rdemo.net.HttpClient
import io.rong.imkit.RongIM
import io.rong.imlib.ipc.RongExceptionHandler

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RongIM.init(this)

        Thread.setDefaultUncaughtExceptionHandler(RongExceptionHandler(this))

        HttpClient.getInstance(this)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}