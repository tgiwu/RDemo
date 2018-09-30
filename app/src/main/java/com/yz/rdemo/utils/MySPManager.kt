package com.yz.rdemo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.common.base.Preconditions

@SuppressLint("StaticFieldLeak")
object MySPManager {

    private var mContext: Context? = null
    fun init(context: Context) {
        mContext = context
    }

    fun getDefaultSPForQuery():SharedPreferences {
        Preconditions.checkNotNull(mContext, NullPointerException("SPManager have not be init"))
        return PreferenceManager.getDefaultSharedPreferences(mContext)
    }

    fun getDefaultEditor():SharedPreferences.Editor = getDefaultSPForQuery().edit()

    fun getSPByName(name:String): SharedPreferences {
        Preconditions.checkNotNull(mContext, NullPointerException("SPManager have not be init"))
        return mContext!!.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
    fun getEditorByName(name: String):SharedPreferences.Editor = getSPByName(name).edit()
}