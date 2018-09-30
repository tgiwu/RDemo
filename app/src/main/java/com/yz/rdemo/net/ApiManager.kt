package com.yz.rdemo.net

import com.google.common.base.Preconditions
import com.google.gson.Gson
import com.yz.rdemo.net.model.LoginModel
import com.yz.rdemo.net.model.RegistryModel
import com.yz.rdemo.net.model.SimpleModel
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

    fun sendCode(region: String, phone:String) : Observable<SimpleModel> {
        Preconditions.checkNotNull(service, NullPointerException("service is null !" ))
        val param = HashMap<String, String>()
        param["region"] = region
        param["phone"] = phone
        val json = mGson.toJson(param)
        return service!!.sendCode(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
    }

    fun registry(nickname: String, password:String, verification_token: String): Observable<RegistryModel> {
        Preconditions.checkNotNull(service, NullPointerException("service is null !" ))
        val param = HashMap<String, String>()
        param["nickname"] = nickname
        param["password"] = password
        param["verification_token"] = verification_token
        val json = mGson.toJson(param)
        return service!!.registry(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
    }

    fun login(region: String, phone: String, password:String) :Observable<LoginModel>{
        Preconditions.checkNotNull(service, NullPointerException("service is null!"))
        val param = HashMap<String, String>()
        param["region"] = region
        param["phone"] = phone
        param["password"] = password
        val json = mGson.toJson(param)
        return  service!!.login(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json))
    }
}