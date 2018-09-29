package com.yz.rdemo.controllers

import android.support.v7.app.AppCompatActivity

interface IController {
    fun attach(activity: AppCompatActivity)
    fun detach()
}