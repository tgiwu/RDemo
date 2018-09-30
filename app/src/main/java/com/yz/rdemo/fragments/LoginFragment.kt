package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yz.rdemo.R
import com.yz.rdemo.activities.BaseActivity
import com.yz.rdemo.activities.MainActivity
import com.yz.rdemo.controllers.IMainController
import com.yz.rdemo.display.IMainDisplay
import com.yz.rdemo.net.model.LoginInfo
import kotlinx.android.synthetic.main.login_fragment_layout.*

class LoginFragment: Fragment(), IMainController.ILoginUi, View.OnClickListener {

    companion object {
        val instance:LoginFragment by lazy { LoginFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn.setOnClickListener(this)
        goToRegistry.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.loginBtn -> {
                if (checkInput())
                    (activity as BaseActivity<IMainController<IMainController.IMainUi>, IMainDisplay>).getController()?.doLogin("86", loginPhone.text.toString(), loginPass.text.toString())
            }
            R.id.goToRegistry -> {
                (activity as BaseActivity<IMainController<IMainController.IMainUi>, IMainDisplay>).getDisplay()?.showRegistry()
            }
            else->{}
        }
    }

    private fun checkInput() : Boolean {
        if (loginPhone.text.isEmpty()){
            Toast.makeText(activity, R.string.login_phone_empty, Toast.LENGTH_LONG).show()
            return false
        }
        if (loginPass.text.isEmpty()) {
            Toast.makeText(activity, R.string.login_password_empty, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onLoginSuccess() {
    }

    override fun onError(requestCode: Int, message: String?) {
        Log.i("zhy", "get error code $message")
    }
}