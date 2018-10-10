package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yz.rdemo.R
import com.yz.rdemo.adapters.ListItemAdapter
import com.yz.rdemo.controllers.IOperationController
import com.yz.rdemo.net.model.ListItem
import kotlinx.android.synthetic.main.operation_list_layout.*
import android.support.v7.widget.DividerItemDecoration
import com.yz.rdemo.Constants
import com.yz.rdemo.activities.OperationListActivity


class OperationListFragment: Fragment(),IOperationController.IListUI, ListItemAdapter.IOnItemClickListener, View.OnClickListener {

    private var mAdapter: ListItemAdapter? = null
    var mType: Int = Constants.CHAT_TYPE_UNDEFAULT

    companion object {
        val instance: OperationListFragment by lazy { OperationListFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.operation_list_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        operation_item_list.layoutManager = layoutManager
        operation_item_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        mAdapter?:apply {
            mAdapter = ListItemAdapter(activity!!)
            mAdapter?.setOnItemClick(this)
        }
        operation_item_list.adapter = mAdapter

        (activity as OperationListActivity).addFabClickListener(this)
    }

    override fun onItemClick(item: ListItem) {
        when(mType) {
            Constants.CHAT_TYPE_PRIVATE ->
                (activity as OperationListActivity).getController()?.onRequestPrivate(item.id)
            Constants.CHAT_TYPE_ROME_CHAT ->
                (activity as OperationListActivity).getController()?.onRequestRome(item.id)
            Constants.CHAT_TYPE_GROUP ->
                (activity as OperationListActivity).getController()?.onRequestGroup(item.id, item.name)
        }
    }

    override fun onClick(v: View?) {
        mAdapter!!.getSelected()?.let {
            (activity as OperationListActivity).getController()?.onRequestDiscussion(it, "title")
        }
    }

    override fun onListFriends(lists: List<ListItem>) {
        Log.i("zhy", "onListFriends size ${lists.size}")
        mAdapter?.changeList(lists)
        mType = Constants.CHAT_TYPE_PRIVATE

        if (operation_item_list.visibility == View.GONE)
            operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE)
            operation_item_list_txt.visibility = View.GONE
    }

    override fun onListGroup(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        mType = Constants.CHAT_TYPE_GROUP
    }

    override fun onListFriendForDiscussion(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        mType = Constants.CHAT_TYPE_DISCUSSION
    }

    override fun onListRome(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        mType = Constants.CHAT_TYPE_ROME_CHAT
    }

    override fun showErrorToast(error: String) {
    }

}