package com.yz.rdemo.utils

abstract class BackgroundCallRunnable<R> {
    open fun preExecute(){}
    abstract fun runAsync() : R
    open fun postExecute(result: R){}
}