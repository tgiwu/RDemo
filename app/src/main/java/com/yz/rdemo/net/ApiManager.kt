package com.yz.rdemo.net

import com.google.common.base.Preconditions
import com.google.gson.Gson
import com.yz.rdemo.net.model.TokenModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit

object ApiManager{

    private var service: ApiService? = null
    private val mGson = Gson()

    fun setApi(apiService: ApiService) {
        service = apiService
    }

    fun getToken(userId: String, name:String, portraitUri:String) : Observable<TokenModel> {
        Preconditions.checkNotNull(service, NullPointerException("service is null !" ))
        return service!!.getToken(userId, name, portraitUri)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .throttleWithTimeout(60, TimeUnit.SECONDS)

    }

    fun sendCode(region: String, phone:String) : Observable<String> {
        Preconditions.checkNotNull(service, NullPointerException("service is null !" ))
        var param = HashMap<String, String>()
        param["region"] = region
        param["phone"] = phone
        val json = mGson.toJson(param)
        return service!!.sendCode(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
    }
}