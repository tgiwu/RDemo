package com.yz.rdemo.net.runnables

import android.util.Log
import com.yz.rdemo.Constants
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.net.model.LoginModel
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class LoginCallRunnable(region: String, phone: String, password:String): NetworkCallRunnable<LoginModel>() {

    private val mRegion = region
    private val mPhone = phone
    private  val mPassword = password

    override fun doBackgroundCall(): Observable<LoginModel> {
        return ApiManager.login(mRegion, mPhone, mPassword)
    }

    override fun onSuccess(result: LoginModel) {
        Log.i("zhy", "login $result")
        if (null != result.result)
            MainController.instance.onRequestSuccess(Constants.REQUEST_LOGIN_DO, result.result)
        else {
            MainController.instance.onRequestError(Constants.REQUEST_LOGIN_DO, result.code.toString())
        }
    }

    override fun onError(e: Throwable?, message:String?, code:Int) {
        Log.i("zhy","login error $e")
        MainController.instance.onRequestError(Constants.REQUEST_LOGIN_DO, e?.message)
    }
}