package com.example.laptoplens

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = loadCookies(context)
        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }

    private fun loadCookies(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences("cookies", Context.MODE_PRIVATE)
        val cookieStringSet = sharedPreferences.getStringSet("cookies", null)
        return cookieStringSet?.toList() ?: emptyList()
    }
}