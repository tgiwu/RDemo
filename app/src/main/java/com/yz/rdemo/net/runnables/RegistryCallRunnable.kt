package com.yz.rdemo.net.runnables

import android.util.Log
import com.yz.rdemo.Constants
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.net.model.LoginInfo
import com.yz.rdemo.net.model.RegistryModel
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class RegistryCallRunnable(nickname: String, password:String, verification_token: String, phone:String):NetworkCallRunnable<RegistryModel>(){

    private val mNickname = nickname
    private val mPassword = password
    private val mVerificationCode = verification_token
    private val mPhone = phone

    override fun doBackgroundCall(): Observable<RegistryModel> {
        return ApiManager.registry(mNickname, mPassword, mVerificationCode)
    }

    override fun onSuccess(result: RegistryModel) {
        Log.i("zhy", "registry $result")
        if (null != result.result)
            MainController.instance.onRequestSuccess(Constants.REQUEST_REGISTRY_DO, LoginInfo(mPhone, mPassword, result.result.id))
        else
            MainController.instance.onRequestError(Constants.REQUEST_REGISTRY_DO, result.toString())
    }

    override fun onError(e: Throwable?, message:String?, code:Int) {
        MainController.instance.onRequestError(Constants.REQUEST_REGISTRY_DO, message)
    }
}