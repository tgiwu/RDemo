package com.yz.rdemo.utils

import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object MyExecutor : BackgroundExecutor {

    private val mHandler = Handler(Looper.getMainLooper())

    private var mExecutorService :ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)

    override fun <R> execute(runnable: NetworkCallRunnable<R>) {
        mExecutorService.execute(NetworkCallRunner(runnable))
    }

    override fun <R> execute(runnable: BackgroundCallRunnable<R>) {
        mExecutorService.execute(BackgroundCallRunner(runnable))
    }

    class NetworkCallRunner<R>(runnable: NetworkCallRunnable<R>): Runnable {

        val mBackgroundRunnable: NetworkCallRunnable<R> = runnable

        override fun run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            mHandler.post { mBackgroundRunnable.onPre() }
            mBackgroundRunnable.doBackgroundCall().subscribe(object : Observer<R> {
                override fun onNext(t: R) {
                    mBackgroundRunnable.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("zhy", "on error ${e.message}")
                    if (e is HttpException) {
                        Log.e("zhy", "error ${e.response().errorBody()?.string()} code ${e.response().code()}")
                        mBackgroundRunnable.onError(e, e.response().errorBody()?.string(), e.response().code())
                    } else {
                        mBackgroundRunnable.onError(e,null, -1)
                    }
                }

                override fun onComplete() {
                    mBackgroundRunnable.onFinish()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
        }
    }

    class BackgroundCallRunner<R>(runnable: BackgroundCallRunnable<R>) : Runnable {

        val mBackgroundCallRunnable = runnable

        override fun run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            mHandler.post{mBackgroundCallRunnable.preExecute()}

            val result = mBackgroundCallRunnable.runAsync()
            mHandler.post{ResultCallback(result)}
        }

        inner class ResultCallback(result: R) : Runnable {
            private var mResult = result
            override fun run() {
                mBackgroundCallRunnable.postExecute(mResult)
            }

        }

    }
}