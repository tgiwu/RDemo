package com.yz.rdemo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.multidex.MultiDex
import android.util.Log
import com.yz.rdemo.net.ApiService
import com.yz.rdemo.net.HttpClient
import com.yz.rdemo.utils.MySPManager
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.ipc.RongExceptionHandler
import io.rong.imlib.model.Group
import io.rong.imlib.model.UserInfo

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RongIM.init(this)

        Thread.setDefaultUncaughtExceptionHandler(RongExceptionHandler(this))

        MySPManager.init(this)
        HttpClient.getInstance(this)

        RongIM.setUserInfoProvider({ id -> UserInfo(id, id, Uri.parse(Constants.PORTRAIT)) }, true)

        RongIM.setGroupInfoProvider({ id -> Group(id, id, Uri.parse(Constants.PORTRAIT)) }, true)


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}