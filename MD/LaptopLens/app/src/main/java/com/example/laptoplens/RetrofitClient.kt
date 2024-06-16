package com.example.laptoplens

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val baseUrl = "https://api-434e63m7za-et.a.run.app/" // Ganti dengan URL API Anda yang sebenarnya

    val instance: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}