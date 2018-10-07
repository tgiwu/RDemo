package com.yz.rdemo.controllers

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yz.rdemo.Constants.REQUEST_LOGIN_DO
import com.yz.rdemo.Constants.REQUEST_REGISTRY_CODE
import com.yz.rdemo.Constants.REQUEST_REGISTRY_DO
import com.yz.rdemo.Constants.REQUEST_REGISTRY_VERIFY_CODE
import com.yz.rdemo.activities.MainActivity
import com.yz.rdemo.net.model.*
import com.yz.rdemo.net.runnables.LoginCallRunnable
import com.yz.rdemo.net.runnables.RegistryCallRunnable
import com.yz.rdemo.net.runnables.SendCodeCallRunnable
import com.yz.rdemo.net.runnables.VerifyCodeCallRunnable
import com.yz.rdemo.utils.MyExecutor
import com.yz.rdemo.utils.MySPManager
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient

class MainController : IMainController<IMainController.IMainUi> {

    companion object {
        val instance: MainController by lazy { MainController() }
    }

    private var mActivity: AppCompatActivity? = null

    override fun isAttached(): Boolean = null != mActivity

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
        Log.i("zhy", "doRegistry $nickname  $password, $verification_token, $phone")
        MyExecutor.execute(RegistryCallRunnable(nickname, password, verification_token, phone))
    }

    override fun doLogin(region: String, phone: String, password: String) {
        Log.i("zhy", "doLogin $region, $phone, $password")
        MyExecutor.execute(LoginCallRunnable(region, phone, password))
    }

    override fun doVerifyCode(region:String, nickname: String, password: String, code: String, phone: String) {
        Log.i("zhy", "doVerifyCode $region $nickname $password $code $phone")
        MyExecutor.execute(VerifyCodeCallRunnable(region,phone, code,nickname, password))
    }

    override fun tryToConnectServer(token: String) {
//        RongIM.connect(token, object :RongIMClient.ConnectCallback() {
        RongIM.connect("tfszbUwsVR0pUKY+C/9MSUIdOyN2FeZF7pxEYpISa65javqzboyVA8iud/DpFNyPc+sj++9TO5tFbTkOzN+BSwQi7sWd57Ka", object :RongIMClient.ConnectCallback() {
            override fun onSuccess(p0: String?) {
                Log.i("zhy", "onSuccess $p0")
                (mActivity as MainActivity).getDisplay()?.showConversation()
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                Log.i("zhy", "onError $p0")
            }

            override fun onTokenIncorrect() {
                Log.i("zhy", "onTokenIncorrect $token")
            }
        })
    }

    override fun onRequestSuccess(requestCode: Int, data: Any?) {
        Log.i("zhy", "is mActivity null ${hashCode()}")
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
                Log.i("zhy", "REQUEST_LOGIN_DO")
                saveUserData(requestCode, data)
                mActivity?.let {
                    tryToConnectServer((data as LoginResultEntity).token)
                }
                mActivity?.supportFragmentManager?.findFragmentByTag("login")?.let {
                    if (!it.isDetached)
                        (it as IMainController.ILoginUi).onLoginSuccess()
                }
            }
            REQUEST_REGISTRY_VERIFY_CODE -> {
                Log.i("zhy", "REQUEST_REGISTRY_VERIFY_CODE")
               val info = data as HashMap<String, String>
                Log.i("zhy", "map $info['nickname''], info['password''], $info['verification_token''], $info['phone']")
                info.let {
                    doRegistry(it["nickname"]!!, it["password"]!!, it["verification_token"]!!, it["phone"]!!)
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
            REQUEST_REGISTRY_DO,
            REQUEST_REGISTRY_VERIFY_CODE -> {
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