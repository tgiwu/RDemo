package com.yz.rdemo.net.runnables

import com.yz.rdemo.Constants
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class SendCodeCallRunnable(region:String, phone:String): NetworkCallRunnable<String>() {

    private val mRegion = region
    private val mPhone = phone

    override fun doBackgroundCall(): Observable<String> {
        return ApiManager.sendCode(mRegion, mPhone)
    }

    override fun onSuccess(result: String) {
        MainController.instance.onRequestSuccess(Constants.REQUEST_REGISTRY_CODE, null)
    }

    override fun onError(e: Throwable?) {
        MainController.instance.onRequestError(Constants.REQUEST_REGISTRY_CODE, e?.message)
    }
}