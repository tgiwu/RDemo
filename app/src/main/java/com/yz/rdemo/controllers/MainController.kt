package com.yz.rdemo.controllers

import android.support.v7.app.AppCompatActivity
import com.yz.rdemo.Constants.REQUEST_LOGIN_DO
import com.yz.rdemo.Constants.REQUEST_REGISTRY_CODE
import com.yz.rdemo.Constants.REQUEST_REGISTRY_DO
import com.yz.rdemo.net.model.LoginInfo
import com.yz.rdemo.net.model.LoginResultEntity
import com.yz.rdemo.net.runnables.LoginCallRunnable
import com.yz.rdemo.net.runnables.RegistryCallRunnable
import com.yz.rdemo.net.runnables.SendCodeCallRunnable
import com.yz.rdemo.utils.MyExecutor
import com.yz.rdemo.utils.MySPManager
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient

class MainController : IMainController<IMainController.IMainUi> {

    companion object {
        val instance: MainController by lazy { MainController() }
    }

    var mActivity: AppCompatActivity? = null

    override fun attach(activity: AppCompatActivity) {
        mActivity = activity
    }

    override fun detach() {
        mActivity = null
    }

    override fun doRequestCode(region: String, phone: String) {
        MyExecutor.execute(SendCodeCallRunnable(region, phone))
    }

    override fun doRegistry(nickname: String, password: String, verification_token: String, phone: String) {
        MyExecutor.execute(RegistryCallRunnable(nickname, password, verification_token, phone))
    }

    override fun doLogin(region: String, phone: String, password: String) {
        MyExecutor.execute(LoginCallRunnable(region, phone, password))
    }

    override fun tryToConnectServer(activity: com.yz.rdemo.activities.MainActivity) {
        val token = MySPManager.getDefaultSPForQuery().getString("rong_token", "")
        RongIM.connect(token, object : RongIMClient.ConnectCallback(){
            override fun onSuccess(p0: String?) {
                activity.showConversationList()
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
            }

            override fun onTokenIncorrect() {
            }

        })

    }

    override fun onRequestSuccess(requestCode: Int, data: Any?) {
        mActivity?: return
        when(requestCode) {
            REQUEST_REGISTRY_CODE ->
                mActivity?.supportFragmentManager?.findFragmentByTag("registry")?.let {
                    if (!it.isDetached) {
                        (it as IMainController.IRegistryUi).onRequestSend()
                    }
                }
            REQUEST_REGISTRY_DO -> {
                saveUserData(requestCode, data)
                val loginInfo = data as LoginInfo
                doLogin("86", loginInfo.phone, loginInfo.password)
                mActivity?.supportFragmentManager?.findFragmentByTag("registry")?.let {
                    if (!it.isDetached)
                        (it as IMainController.IRegistryUi).onRegistrySuccess()
                }
            }
            REQUEST_LOGIN_DO -> {
                saveUserData(requestCode, data)
                mActivity?.supportFragmentManager?.findFragmentByTag("login")?.let {
                    if (!it.isDetached)
                        (it as IMainController.ILoginUi).onLoginSuccess(data as LoginInfo)
                }
            }
        }
    }



    override fun onRequestError(requestCode: Int, message: String?) {
        mActivity?: return
        when(requestCode) {
            REQUEST_REGISTRY_CODE ->
                mActivity?.supportFragmentManager?.findFragmentByTag("registry")?.let {
                    if (!it.isDetached) {
                        (it as IMainController.IRegistryUi).onError(requestCode, message)
                    }
                }
            REQUEST_REGISTRY_DO -> {
                mActivity?.supportFragmentManager?.findFragmentByTag("registry")?.let {
                    if (!it.isDetached)
                        (it as IMainController.IRegistryUi).onError(requestCode, message)
                }
            }
            REQUEST_LOGIN_DO -> {
                mActivity?.supportFragmentManager?.findFragmentByTag("login")?.let {
                    if (!it.isDetached)
                        (it as IMainController.ILoginUi).onError(requestCode, message)
                }
            }
        }
    }

    private fun saveUserData(requestCode: Int, data: Any?) {
        when(requestCode) {
            REQUEST_LOGIN_DO -> {
                MySPManager.getDefaultEditor().putString("rong_token", (data as LoginResultEntity).token).apply()
            }
        }
    }

}