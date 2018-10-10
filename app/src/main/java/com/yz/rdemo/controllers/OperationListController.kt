package com.yz.rdemo.controllers

import android.content.ContentResolver
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yz.rdemo.Constants
import com.yz.rdemo.Utils
import com.yz.rdemo.activities.BaseActivity
import com.yz.rdemo.display.IDisplay
import com.yz.rdemo.display.IOperationDisplay
import com.yz.rdemo.net.model.ListItem
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.UserData

class OperationListController<U : IOperationController.IOperationUI, D: IOperationDisplay>: IOperationController<IOperationController.IOperationUI, IOperationDisplay>{

    companion object {
        val instance :OperationListController<IOperationController.IOperationUI, IOperationDisplay> by lazy { OperationListController<IOperationController.IOperationUI, IOperationDisplay>() }
    }

    private var mActivity: BaseActivity<IOperationController<U, D>, D>? = null
    private var mDisplay: IOperationDisplay? = null

    override fun isAttached(): Boolean = null != mActivity

    override fun setDisplay(display: IDisplay) {
        mDisplay = display as IOperationDisplay
    }

    override fun attach(activity: AppCompatActivity) {
        mActivity = activity as BaseActivity<IOperationController<U, D>, D>
        mDisplay = activity.getDisplay()
    }

    override fun detach() {
        mActivity = null
        mDisplay = null
    }

    fun getUserInfo() {
        RongIM.getInstance().syncUserData(UserData(mActivity), object : RongIMClient.OperationCallback(){
            override fun onSuccess() {
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
            }
        })
    }

    override fun onListFriends() {
        val ui = mActivity?.supportFragmentManager?.findFragmentByTag("operation") as? IOperationController.IListUI
        ui?.onListFriends(listOf(ListItem("13466739595", "5", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_PRIVATE), 0),
                ListItem("13466739596", "6", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_PRIVATE), 0)))
    }

    override fun onListRomes() {
        val ui = mActivity?.supportFragmentManager?.findFragmentByTag("operation") as? IOperationController.IListUI
        ui?.onListRome(listOf(ListItem(Constants.ROME_ID, "rome", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_DISCUSSION), 0)))
    }

    override fun onListFriendsMulti() {
        val ui = mActivity?.supportFragmentManager?.findFragmentByTag("operation") as? IOperationController.IListUI
        ui?.onListFriendForDiscussion(listOf(ListItem("13466739595", "5", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_PRIVATE), 1),
                ListItem("13466739596", "6", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_PRIVATE), 1)))
    }

    override fun onListGroup() {
        val ui = mActivity?.supportFragmentManager?.findFragmentByTag("operation") as? IOperationController.IListUI
        ui?.onListGroup(listOf(ListItem(Constants.GROUP_ID, "group", Utils.getDefaultUri(mActivity!!, Constants.PORTRAIT_TYPE_GROUP), 0)))
    }

    override fun onRequestPrivate(targetId: String) {
        mDisplay?.showPrivateChat(targetId)

    }

    override fun onRequestRome(chatRomeId: String) {
        mDisplay?.showRomeChat(chatRomeId)
    }

    override fun onRequestDiscussion(ids: List<String>, title: String) {
        if (ids.isEmpty()) {
            mDisplay?.showErrorToast("Select one person to discussion at least")
            return
        } else {
            mDisplay?.showDiscussionChat(ids, title)
        }
    }

    override fun onRequestGroup(groupId: String, title: String) {
        mDisplay?.showGroupChat(groupId, title)
    }
}