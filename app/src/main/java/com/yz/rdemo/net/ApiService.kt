package com.yz.rdemo.net

import com.yz.rdemo.net.model.LoginModel
import com.yz.rdemo.net.model.RegistryModel
import com.yz.rdemo.net.model.SimpleModel
import com.yz.rdemo.net.model.TokenModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @POST("user/getToken.json")
    @FormUrlEncoded
    fun getToken(@Field("userId") userId:String, @Field("name") name: String, @Field("portraitUri") portraitUri: String) : Observable<TokenModel>

    @POST("user/register")
    fun registry(@Body request: RequestBody) : Observable<RegistryModel>

    @POST("user/send_code")
    fun sendCode(@Body request: RequestBody): Observable<SimpleModel>

    @POST("user/login")
    fun login(@Body request: RequestBody):Observable<LoginModel>
}