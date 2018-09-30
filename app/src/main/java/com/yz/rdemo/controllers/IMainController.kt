package com.yz.rdemo.controllers

import android.support.v7.app.AppCompatActivity
import com.yz.rdemo.activities.MainActivity
import com.yz.rdemo.net.model.LoginInfo

interface IMainController<U : IMainController.IMainUi>: IController {

    fun doRequestCode(region:String, phone: String)

    fun doRegistry(nickname: String, password: String, verification_token: String, phone: String)

    fun doLogin(region: String, phone: String, password: String)

    fun onRequestSuccess(requestCode: Int, data: Any?)

    fun onRequestError(requestCode: Int, message: String?)

    fun tryToConnectServer(activity: MainActivity)

    interface IMainUi

    interface IRegistryUi : IMainUi {
        fun onRequestSend()
        fun onRegistrySuccess()
        fun onRequestCodeSend()
        fun onError(requestCode: Int, message: String?)
    }

    interface ILoginUi :IMainUi{
        fun onLoginSuccess(loginInfo: LoginInfo)
        fun onError(requestCode: Int, message: String?)
    }
}