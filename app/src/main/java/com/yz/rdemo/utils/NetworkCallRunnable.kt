package com.yz.rdemo.utils

import io.reactivex.Observable

abstract class NetworkCallRunnable<R> {
    open fun onPre() {}
    abstract fun doBackgroundCall() : Observable<R>
    abstract fun onSuccess(result: R)
    abstract fun onError(e: Throwable?)
    open fun onFinish(){}
}