package com.yz.rdemo.display

import android.support.v7.app.AppCompatActivity

interface IDisplay {

    fun attach(activity: AppCompatActivity)
    fun detach()
}