package com.yz.rdemo.net.runnables

import android.util.Log
import com.yz.rdemo.Constants
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.net.model.SimpleModel
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class SendCodeCallRunnable(region:String, phone:String): NetworkCallRunnable<SimpleModel>() {

    private val mRegion = region
    private val mPhone = phone

    override fun doBackgroundCall(): Observable<SimpleModel> {
        return ApiManager.sendCode(mRegion, mPhone)
    }

    override fun onSuccess(result: SimpleModel) {
        Log.i("zhy", " request code $result")
        if (200 == result.code)
            MainController.instance.onRequestSuccess(Constants.REQUEST_REGISTRY_CODE, null)
        else
            MainController.instance.onRequestError(Constants.REQUEST_REGISTRY_CODE, result.toString())
    }

    override fun onError(e: Throwable?, message:String?, code:Int) {
        MainController.instance.onRequestError(Constants.REQUEST_REGISTRY_CODE, e?.message)
    }
}