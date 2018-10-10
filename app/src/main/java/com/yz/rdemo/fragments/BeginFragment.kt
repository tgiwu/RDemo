package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yz.rdemo.Constants
import com.yz.rdemo.R
import com.yz.rdemo.activities.MainActivity
import com.yz.rdemo.controllers.IMainController
import kotlinx.android.synthetic.main.begin_layout.*

class BeginFragment: Fragment(), IMainController.IBeginUi, View.OnClickListener {

    companion object {
        val instance :BeginFragment by lazy { BeginFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.begin_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        begin_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        (activity as MainActivity).getController()?.tryToConnectServer(Constants.MY_TOKEN)
    }
}