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

class RegistryFragment :Fragment(), IMainController.IRegistryUi, View.OnClickListener{


    companion object {
        val instance : RegistryFragment by lazy { RegistryFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.registry_fragment_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registryRequestCodeBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.registryRequestCodeBtn -> {
                (activity as BaseActivity<IMainController<IMainController.IMainUi>, IMainDisplay>).getController()?.doRequestCode("86", registryPhone.text.toString())
            }
            else ->{}
        }
    }

    override fun onRequestSend() {
        Log.i("zhy", "code has sent")
        Toast.makeText(activity, R.string.registry_code_has_send, Toast.LENGTH_LONG).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRegistrySuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(requestCode: Int, message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}