package com.yz.rdemo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yz.rdemo.net.ApiService
import com.yz.rdemo.net.HttpClient
import com.yz.rdemo.utils.MySPManager
import io.rong.imkit.RongIM
import io.rong.imlib.ipc.RongExceptionHandler

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RongIM.init(this)

        Thread.setDefaultUncaughtExceptionHandler(RongExceptionHandler(this))

        MySPManager.init(this)
        HttpClient.getInstance(this)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}