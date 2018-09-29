package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yz.rdemo.controllers.IMainController

class LoginFragment: Fragment(), IMainController.ILoginUi, View.OnClickListener {

    companion object {
        val instance:LoginFragment by lazy { LoginFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(requestCode: Int, message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}