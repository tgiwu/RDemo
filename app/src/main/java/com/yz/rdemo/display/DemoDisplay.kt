package com.yz.rdemo.display

import android.annotation.SuppressLint
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import com.google.common.base.Preconditions
import com.yz.rdemo.R
import com.yz.rdemo.activities.OperationListActivity
import com.yz.rdemo.fragments.BeginFragment
import com.yz.rdemo.fragments.LoginFragment
import com.yz.rdemo.fragments.OperationListFragment
import com.yz.rdemo.fragments.RegistryFragment
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.push.RongPushClient

@SuppressLint("StaticFieldLeak")
class DemoDisplay(): IMainDisplay, IOperationDisplay {

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

    override fun showBegin() {
        Preconditions.checkNotNull(mActivity, NullPointerException("activity is null"))
        if (checkMainContent()) {
                getTransaction()?.add(R.id.main_content, BeginFragment.instance, "begin")?.commit()
        }
    }

    override fun showConversationList() {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        val map = HashMap<String, Boolean> ()
        map[RongPushClient.ConversationType.PRIVATE.name] = true
        map[RongPushClient.ConversationType.CHATROOM.name] = true
        map[RongPushClient.ConversationType.GROUP.name] = true
        map[RongPushClient.ConversationType.DISCUSSION.name] = true
        RongIM.getInstance().startConversationList(mActivity!!.applicationContext, map)
        mActivity?.finish()
    }

    override fun showOperationList() {
        mActivity?.startActivity(Intent(mActivity!!.applicationContext, OperationListActivity::class.java))
        mActivity?.finish()
    }

    override fun showOperationListFragment() {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        if (checkMainContent()) {
            if (mActivity!!.supportFragmentManager?.findFragmentByTag("operation") == null)
                getTransaction()?.add(R.id.main_content, OperationListFragment.instance, "operation")?.commit()
            else
                getTransaction()?.replace(R.id.main_content, OperationListFragment.instance, "operation")?.commit()
        }
    }

    override fun showErrorToast(message: String) {
        val view = mActivity?.window?.decorView?.findViewById<FrameLayout>(R.id.main_content)
        mActivity?.let {
            it.runOnUiThread {
                view?.let { v ->
                    Snackbar.make(v, message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun showErrorToast(message: Int) {
        val view = mActivity?.window?.decorView?.findViewById<FrameLayout>(R.id.main_content)
        mActivity?.let {
            it.runOnUiThread {
                view?.let { v ->
                    Snackbar.make(v, message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun showPrivateChat(targetId: String) {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        RongIM.getInstance().startConversation(mActivity, Conversation.ConversationType.PRIVATE, targetId, "idid")
    }

    override fun showDiscussionChat(ids: List<String>, title: String) {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        RongIM.getInstance().createDiscussionChat(mActivity, ids, "discussion", object : RongIMClient.CreateDiscussionCallback() {
            override fun onSuccess(p0: String?) {
                Log.i("zhy", "create success $p0")
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                Log.i("zhy", "create error $p0")
            }
        })
    }

    override fun showRomeChat(chatRomeId:String) {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        RongIM.getInstance().startChatRoomChat(mActivity!!, chatRomeId, true)
    }

    override fun showGroupChat(groupId: String, title: String) {
        Preconditions.checkNotNull(mActivity, Throwable("activity is null"))
        RongIM.getInstance().startGroupChat(mActivity, groupId, title)
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