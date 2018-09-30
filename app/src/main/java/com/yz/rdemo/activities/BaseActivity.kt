package com.yz.rdemo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yz.rdemo.R
import com.yz.rdemo.controllers.IController
import com.yz.rdemo.display.IDisplay

abstract class BaseActivity<C : IController, D : IDisplay>: AppCompatActivity() {

    private var mController: C? = null
    private var mDisplay: D? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mController = provideController()
        mDisplay = provideDisplay()
        setContentView(R.layout.activity_main)
    }

    abstract fun provideController() : C
    abstract fun provideDisplay(): D

    fun getController(): C? = mController
    fun getDisplay(): D? = mDisplay

    override fun onRestart() {
        super.onRestart()
        Log.i("zhy", " onRestart")
        mController?.attach(this)
        mDisplay?.attach(this)
    }

    override fun onStart() {
        super.onStart()
        Log.i("zhy", "onStart")
        mController?.attach(this)
        mDisplay?.attach(this)
    }

    override fun onResume() {
        super.onResume()
        Log.i("zhy", "onResume")
        mController?.attach(this)
        mDisplay?.attach(this)
    }

    override fun onPause() {
        Log.i("zhy", "onPause")
        mController?.detach()
        mDisplay?.detach()
        super.onPause()
    }
}