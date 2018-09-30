package com.yz.rdemo.net.model

data class RegistryModel(val code:Int, val result: RegistryResultEntity?) {
    override fun toString(): String {
        return "RegistryModel(code=$code, result=$result)"
    }
}

data class RegistryResultEntity(val id: String) {
    override fun toString(): String {
        return "RegistryResultEntity(id='$id')"
    }
}
