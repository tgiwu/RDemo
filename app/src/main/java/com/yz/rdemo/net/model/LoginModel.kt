package com.yz.rdemo.net.model

data class LoginModel(val code: Int, val result: LoginResultEntity?){
    override fun toString(): String {
        return "LoginModel(code=$code, result=$result)"
    }
}
data class LoginResultEntity(val id: String, val token: String) {
    override fun toString(): String {
        return "LoginResultEntity(id='$id', token='$token')"
    }
}
