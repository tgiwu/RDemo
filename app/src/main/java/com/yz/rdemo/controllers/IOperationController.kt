package com.yz.rdemo.controllers

import com.yz.rdemo.display.IOperationDisplay
import com.yz.rdemo.net.model.ListItem
import io.rong.imlib.model.Group
import io.rong.imlib.model.UserInfo

interface IOperationController<U: IOperationController.IOperationUI, D: IOperationDisplay>:IController {

    fun onRequestPrivate(targetId: String)
    fun onRequestRome(chatRomeId:String)
    fun onRequestDiscussion(ids: List<String>?, title: String)
    fun onRequestGroup(groupId: String,title: String)

    fun onListFriends()
    fun onListRomes()
    fun onListFriendsMulti()
    fun onListGroup()

    interface IOperationUI

    interface IListUI : IOperationUI {
        fun onListFriends(lists: List<ListItem>)
        fun onListGroup(lists: List<ListItem>)
        fun onListRome(lists: List<ListItem>)
        fun onListFriendForDiscussion(lists: List<ListItem>)
    }

}