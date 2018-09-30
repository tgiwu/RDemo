package com.yz.rdemo.net.model

data class VerifyCodeModel(val code:Int, val result: VerifyCodeResultEntity?) {
    override fun toString(): String {
        return "VerifyCodeModel(code=$code, result=$result)"
    }
}
data class VerifyCodeResultEntity(val verification_token:String) {
    override fun toString(): String {
        return "VerifyCodeResultEntity(verification_token='$verification_token')"
    }
}