package com.yz.rdemo.controllers

import android.support.v7.app.AppCompatActivity
import com.yz.rdemo.net.runnables.SendCodeCallRunnable
import com.yz.rdemo.utils.MyExecutor

class MainController : IMainController<IMainController.IMainUi> {

    companion object {
        val instance: MainController by lazy { MainController() }
    }

    var mActivity: AppCompatActivity? = null

    override fun attach(activity: AppCompatActivity) {
        mActivity = activity
    }

    override fun detach() {
        mActivity = null
    }

    override fun doRequestCode(region: String, phone: String) {
        MyExecutor.execute(SendCodeCallRunnable(region, phone))
    }

    override fun doRegistry(nickname: String, password: String, verification_token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doLogin(region: String, phone: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestSuccess(requestCode: Int, data: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestError(requestCode: Int, message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}