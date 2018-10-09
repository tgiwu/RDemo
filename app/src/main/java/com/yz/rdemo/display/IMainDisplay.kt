package com.yz.rdemo.display

interface IMainDisplay:IDisplay {
    fun showRegistry()
    fun showLogin()
    fun showOperationList()
    fun showConversationList()
    fun showErrorToast(message:String)
    fun showErrorToast(message: Int)
}