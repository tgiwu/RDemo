package com.yz.rdemo

import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
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

    fun getDefaultUri(context: Context, type: Int) : Uri {
        val res = context.resources
        val strBuilder = StringBuilder()
        strBuilder.append(ContentResolver.SCHEME_ANDROID_RESOURCE)
        strBuilder.append("://")
        strBuilder.append(res.getResourcePackageName(io.rong.imkit.R.drawable.rc_default_portrait))
        strBuilder.append("/")
        strBuilder.append(res.getResourceTypeName(io.rong.imkit.R.drawable.rc_default_portrait))
        strBuilder.append("/")
        when (type) {
            0,3 -> {
                strBuilder.append(res.getResourceEntryName(io.rong.imkit.R.drawable.rc_default_portrait))
            }
            1 -> {
                strBuilder.append(res.getResourceEntryName(io.rong.imkit.R.drawable.rc_default_group_portrait))
            }
            2 -> {
                strBuilder.append(res.getResourceEntryName(io.rong.imkit.R.drawable.rc_default_discussion_portrait))
            }
        }
        return Uri.parse(strBuilder.toString())
    }

    fun toStringList(set :HashSet<String>) :List<String>{
        val list = MutableList<String>(set.size) { ""}
        var index = 0
        val iterator = set.iterator()
        while (iterator.hasNext()) {
            list[index] = iterator.next()
            index ++
        }
        for (string in list) {
            Log.i("zhy", "id = $string")
        }
        return list
    }

}