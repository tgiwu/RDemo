package com.yz.rdemo.net.runnables

import com.yz.rdemo.net.ApiManager
import com.yz.rdemo.net.model.TokenModel
import com.yz.rdemo.utils.NetworkCallRunnable
import io.reactivex.Observable

class TokenCallRunnable(userId: String, name:String, portraitUri: String): NetworkCallRunnable<TokenModel>() {

    private val mUserId = userId
    private val mName = name
    private val mPortraitUri = portraitUri

    override fun doBackgroundCall(): Observable<TokenModel> {
        return ApiManager.getToken(mUserId, mName, mPortraitUri)
    }

    override fun onSuccess(result: TokenModel) {
    }

    override fun onError(e: Throwable?, message: String?, code:Int) {
    }
}