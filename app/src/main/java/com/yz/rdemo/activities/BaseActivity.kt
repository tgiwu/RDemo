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
    private var isFirstAttach = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mController = provideController()
        mDisplay = provideDisplay()
        mDisplay?.let {  mController?.setDisplay(mDisplay!!) }
        isFirstAttach = true
        setContentView(getLayout())
    }

    abstract fun getLayout() : Int
    abstract fun provideController() : C
    abstract fun provideDisplay(): D
    abstract fun onFirstAttach()

    fun getController(): C? = mController
    fun getDisplay(): D? = mDisplay

    override fun onResume() {
        super.onResume()
        Log.i("zhy", "onResume")
        mController?.attach(this)
        mDisplay?.attach(this)
        if (isFirstAttach) {
            onFirstAttach()
            isFirstAttach = false
        }
    }

    override fun onPause() {
        Log.i("zhy", "onPause")
        mController?.detach()
        mDisplay?.detach()
        super.onPause()
    }
}