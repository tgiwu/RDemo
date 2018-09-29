package com.yz.rdemo

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient

object Utils {
    fun connect(token: String, app: App) {
        if (app.applicationInfo.packageName == findProcessNameById(app, android.os.Process.myPid())) {
            RongIM.connect(token, object :RongIMClient.ConnectCallback(){
                override fun onSuccess(p0: String?) {
                    Log.i("zhy", " connect success $p0")

                }

                override fun onError(p0: RongIMClient.ErrorCode?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTokenIncorrect() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }

    private fun findProcessNameById(app: App, pid: Int): String {
        val activityManager = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            return if (pid == appProcess.pid) {
                appProcess.processName
            } else {
                ""
            }
        }
        return ""
    }
}