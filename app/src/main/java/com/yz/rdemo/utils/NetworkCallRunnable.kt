package com.yz.rdemo.utils

import io.reactivex.Observable

abstract class NetworkCallRunnable<R> {
    open fun onPre() {}
    abstract fun doBackgroundCall() : Observable<R>
    abstract fun onSuccess(result: R)
    abstract fun onError(e: Throwable?, message: String?, code: Int)
    open fun onFinish(){}
}