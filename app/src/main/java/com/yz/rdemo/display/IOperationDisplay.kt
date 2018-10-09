package com.yz.rdemo.display

interface IOperationDisplay: IDisplay {
    fun showPrivateChat(targetId: String)
    fun showDiscussionChat(ids: List<String>, title: String)
    fun showRomeChat(chatRomeId:String)
    fun showGroupChat(groupId: String,title: String)
    fun showOperationListFragment()
}