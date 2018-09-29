package com.yz.rdemo.utils

interface BackgroundExecutor {
    fun <R> execute(runnable: NetworkCallRunnable<R>)
    fun <R> execute(runnable: BackgroundCallRunnable<R>)
}