package com.yz.rdemo.net

import android.annotation.SuppressLint
import android.content.Context
import com.yz.rdemo.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object HttpClient {

    private var retrofit: Retrofit? = null
    private var mContext: Context? = null
    private var okHttpClient: OkHttpClient? = null
    private var builder = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private fun initRetrofit(): Retrofit {
        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .connectTimeout(60, TimeUnit.SECONDS).build()
        retrofit ?: apply {
            retrofit = builder.build()
        }
        ApiManager.setApi(retrofit!!.create(ApiService::class.java))
        return retrofit!!
    }

    fun getInstance(context: Context): Retrofit {
        return if (null == retrofit) {
            mContext = context
            initRetrofit()
        } else {
            retrofit!!
        }
    }

//    fun addCookies() {
//        okHttpClient?: initRetrofit()
//        okHttpClient!!.newBuilder().cookieJar(NovateCookieManger(mContext!!)).build();
//        retrofit = builder.client(okHttpClient).build()
//    }

}