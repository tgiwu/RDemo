package com.yz.rdemo.net.model

data class LoginInfo(var phone: String, var password: String, var id: String) {
    override fun toString(): String {
        return "LoginInfo(phone='$phone', password='$password')"
    }
}