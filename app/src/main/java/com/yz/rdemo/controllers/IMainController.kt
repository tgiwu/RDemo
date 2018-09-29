package com.yz.rdemo.controllers

interface IMainController<U : IMainController.IMainUi>: IController {

    fun doRequestCode(region:String, phone: String)

    fun doRegistry(nickname: String, password: String, verification_token: String)

    fun doLogin(region: String, phone: String, password: String)

    fun onRequestSuccess(requestCode: Int, data: Any?)

    fun onRequestError(requestCode: Int, message: String?)

    interface IMainUi

    interface IRegistryUi : IMainUi {
        fun onRequestSend()
        fun onRegistrySuccess()
        fun onError(requestCode: Int, message: String?)
    }

    interface ILoginUi :IMainUi{
        fun onLoginSuccess()
        fun onError(requestCode: Int, message: String?)
    }
}