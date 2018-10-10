package com.yz.rdemo.display

import android.support.v7.app.AppCompatActivity

interface IDisplay {

    fun isAttached():Boolean
    fun attach(activity: AppCompatActivity)
    fun detach()

    fun showErrorToast(message:String)
    fun showErrorToast(message: Int)
}