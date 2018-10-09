package com.yz.rdemo.controllers

import android.support.v7.app.AppCompatActivity
import com.yz.rdemo.display.IDisplay

interface IController {
    fun setDisplay(display : IDisplay)
    fun isAttached():Boolean
    fun attach(activity: AppCompatActivity)
    fun detach()
}