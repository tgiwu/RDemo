package com.yz.rdemo.net.runnables

import android.util.Log
import com.yz.rdemo.Constants.REQUEST_REGISTRY_VERIFY_CODE
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.net.model.VerifyCodeModel
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class VerifyCodeCallRunnable(region:String,  phone:String,  code:String, nickname:String, password:String):NetworkCallRunnable<VerifyCodeModel>() {
    private val mRegion = region
    private val mPhone = phone
    private val mCode = code
    private val mNickname = nickname
    private val mPassword = password

    override fun doBackgroundCall(): Observable<VerifyCodeModel> = ApiManager.verifyCode(mRegion, mPhone, mCode)

    override fun onSuccess(result: VerifyCodeModel) {
        Log.i("zhy", "verify code  $result")
        val model = HashMap<String, String>()
        model["password"] = mPassword
        model["nickname"] = mNickname
        model["verification_token"] = result.result!!.verification_token
        model["phone"] = mPhone
        MainController.instance.onRequestSuccess(REQUEST_REGISTRY_VERIFY_CODE, model)
    }

    override fun onError(e: Throwable?, message: String?, code: Int) {
        MainController.instance.onRequestError(REQUEST_REGISTRY_VERIFY_CODE, message)

    }
}