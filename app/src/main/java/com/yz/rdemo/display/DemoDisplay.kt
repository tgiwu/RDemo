package com.yz.rdemo.display

import android.annotation.SuppressLint
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import com.google.common.base.Preconditions
import com.yz.rdemo.R
import com.yz.rdemo.fragments.LoginFragment
import com.yz.rdemo.fragments.RegistryFragment
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_main.view.*

@SuppressLint("StaticFieldLeak")
class DemoDisplay(): IMainDisplay {

    private var mActivity: AppCompatActivity? = null

    constructor(activity: AppCompatActivity) : this() {
        attach(activity)
    }

    override fun isAttached(): Boolean = null != mActivity

    override fun attach(activity: AppCompatActivity) {
        mActivity = activity
    }

    override fun detach() {
        mActivity = null
    }

    override fun showRegistry() {
        Preconditions.checkNotNull(mActivity, NullPointerException("activity is null"))
        if (checkMainContent()) {
            if (mActivity!!.supportFragmentManager?.findFragmentByTag("login") == null)
                getTransaction()?.add(R.id.main_content, RegistryFragment.instance, "registry")?.commit()
            else
                getTransaction()?.replace(R.id.main_content, RegistryFragment.instance, "registry")?.commit()
        }
    }

    override fun showLogin() {
        Preconditions.checkNotNull(mActivity, NullPointerException("activity is null"))
        if (checkMainContent()) {
            if (mActivity!!.supportFragmentManager?.findFragmentByTag("registry") == null)
                getTransaction()?.add(R.id.main_content, LoginFragment.instance, "login")?.commit()
            else
                getTransaction()?.replace(R.id.main_content, LoginFragment.instance, "login")?.commit()
        }
    }

    override fun showConversation() {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        RongIM.getInstance().startConversationList(mActivity!!.applicationContext)
    }

    override fun showErrorToast(message: String) {
        mActivity?.let {
            it.runOnUiThread { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun showErrorToast(message: Int) {
        mActivity?.let {
            it.runOnUiThread{ Toast.makeText(it, message, Toast.LENGTH_SHORT).show()}
        }
    }

    private fun checkMainContent():Boolean {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        val view = mActivity?.findViewById<FrameLayout>(R.id.main_content)
        view ?: return false
        return true
    }

    private fun getTransaction() : FragmentTransaction? = mActivity?.supportFragmentManager?.
            beginTransaction()
}