package com.yz.rdemo.display

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.google.common.base.Preconditions
import com.yz.rdemo.R
import com.yz.rdemo.fragments.RegistryFragment
import kotlinx.android.synthetic.main.activity_main.view.*

@SuppressLint("StaticFieldLeak")
class DemoDisplay(): IMainDisplay {

    private var mActivity: AppCompatActivity? = null

    constructor(activity: AppCompatActivity) : this() {
        attach(activity)
    }

    override fun attach(activity: AppCompatActivity) {
        mActivity = activity
    }

    override fun detach() {
        mActivity = null
    }

    override fun showRegistry() {
        if (checkMainContent()) {
            mActivity?.supportFragmentManager?.
                    beginTransaction()?.
                    add(R.id.main_content, RegistryFragment.instance)
                    ?.commit()
        }
    }

    override fun showLogin() {
    }

    private fun checkMainContent():Boolean {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        val view = mActivity?.findViewById<FrameLayout>(R.id.main_content)
        view ?: return false
        return true
    }
}