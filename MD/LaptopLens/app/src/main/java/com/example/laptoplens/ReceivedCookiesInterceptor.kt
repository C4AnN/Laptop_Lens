package com.example.laptoplens

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val cookies = originalResponse.headers("Set-Cookie")
        if (cookies.isNotEmpty()) {
            storeCookies(cookies, context)
        }
        return originalResponse
    }

    private fun storeCookies(cookies: List<String>, context: Context) {
        val sharedPreferences = context.getSharedPreferences("cookies", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("cookies", HashSet(cookies))
        editor.apply()
    }
}