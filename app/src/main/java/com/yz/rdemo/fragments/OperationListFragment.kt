package com.yz.rdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yz.rdemo.R
import com.yz.rdemo.adapters.ListItemAdapter
import com.yz.rdemo.controllers.IOperationController
import com.yz.rdemo.net.model.ListItem
import kotlinx.android.synthetic.main.operation_list_layout.*

class OperationListFragment: Fragment(),IOperationController.IListUI {

    var mAdapter: ListItemAdapter? = null
    var type:Int = -1

    companion object {
        val instance: OperationListFragment by lazy { OperationListFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.operation_list_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        operation_item_list.layoutManager = LinearLayoutManager(activity)
        mAdapter?.apply {
            mAdapter = ListItemAdapter(activity!!)
        }
        operation_item_list.adapter = mAdapter
    }

    override fun onListFriends(lists: List<ListItem>) {
        mAdapter?.changeList(lists)
        type = 0

        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
    }

    override fun onListGroup(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        type = 1
    }

    override fun onListFriendForDiscussion(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        type = 2
    }

    override fun onListRome(lists: List<ListItem>) {
        if (operation_item_list.visibility == View.GONE) operation_item_list.visibility = View.VISIBLE
        if (operation_item_list_txt.visibility == View.VISIBLE) operation_item_list_txt.visibility = View.GONE
        mAdapter?.changeList(lists)
        type = 3
    }

    override fun showErrorToast(error: String) {
    }

}