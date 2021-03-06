package com.yz.rdemo.net

import android.content.Context
import android.os.Build
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class NovateCookieManger(context: Context): CookieJar {

    val mContext: Context = context

    var cookieStore : PersistentCookieStore = PersistentCookieStore(context)

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.isNotEmpty()) {
            for (item in cookies) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    cookieStore.add(url, item)
                }
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url]
    }
}