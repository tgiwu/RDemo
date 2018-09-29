package com.yz.rdemo.net

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
    fun registry(@Body request: RequestBody)

    @POST("user/send_code")
    fun sendCode(@Body request: RequestBody): Observable<String>
}