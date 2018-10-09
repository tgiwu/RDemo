package com.yz.rdemo.controllers

import com.yz.rdemo.display.IMainDisplay

interface IMainController<U : IMainController.IMainUi, D: IMainDisplay>: IController {

    fun doRequestCode(region:String, phone: String)

    fun doRegistry(nickname: String, password: String, verification_token: String, phone: String)

    fun doLogin(region: String, phone: String, password: String)

    fun doVerifyCode(region:String, nickname: String, password: String, code: String, phone: String)

    fun onRequestSuccess(requestCode: Int, data: Any?)

    fun onRequestError(requestCode: Int, message: String?)

    fun tryToConnectServer(token: String)

    interface IMainUi

    interface IRegistryUi : IMainUi {
        fun onRequestSend()
        fun onRegistrySuccess()
        fun onRequestCodeSend()
        fun onError(requestCode: Int, message: String?)
    }

    interface ILoginUi :IMainUi{
        fun onLoginSuccess()
        fun onError(requestCode: Int, message: String?)
    }
}