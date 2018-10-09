package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yz.rdemo.controllers.IMainController
import com.yz.rdemo.R
import com.yz.rdemo.activities.BaseActivity
import com.yz.rdemo.display.IMainDisplay
import kotlinx.android.synthetic.main.registry_fragment_layout.*

class RegistryFragment : Fragment(), IMainController.IRegistryUi, View.OnClickListener {

    companion object {
        val instance: RegistryFragment by lazy { RegistryFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.registry_fragment_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registryRequestCodeBtn.setOnClickListener(this)
        goToLogin.setOnClickListener(this)
        registryBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.registryRequestCodeBtn -> {
                if (registryPhone.text.isNotBlank())
                    (activity as BaseActivity<IMainController<IMainController.IMainUi, IMainDisplay>, IMainDisplay>).getController()?.doRequestCode("86", registryPhone.text.toString())
            }
            R.id.goToLogin -> {
                (activity as BaseActivity<IMainController<IMainController.IMainUi, IMainDisplay>, IMainDisplay>).getDisplay()?.showLogin()
            }
            R.id.registryBtn -> {
                if (checkInput())
                    (activity as BaseActivity<IMainController<IMainController.IMainUi, IMainDisplay>, IMainDisplay>).getController()?.doVerifyCode("86", registryNickname.text.toString(),
                            registryPassword.text.toString(), registryCode.text.toString(), registryPhone.text.toString())
            }
            else -> {
            }
        }
    }

    private fun checkInput(): Boolean {
        if (registryNickname.text.isBlank()) {
            Toast.makeText(activity, R.string.registry_nickname_empty, Toast.LENGTH_SHORT).show()
            return false
        }

        if (registryCode.text.isBlank()) {
            Toast.makeText(activity, R.string.registry_code_empty, Toast.LENGTH_SHORT).show()
            return false
        }

        if (registryPassword.text.isBlank()) {
            Toast.makeText(activity, R.string.login_password_empty, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onRequestCodeSend() {
        Log.i("zhy", "code has sent")
//        Toast.makeText(activity, R.string.registry_code_has_send, Toast.LENGTH_LONG).show()
    }

    override fun onRequestSend() {
    }

    override fun onRegistrySuccess() {
    }

    override fun onError(requestCode: Int, message: String?) {
        message?.let {
            (activity as BaseActivity<IMainController<IMainController.IMainUi, IMainDisplay>, IMainDisplay>).getDisplay()?.showErrorToast(it)
        }
    }
}