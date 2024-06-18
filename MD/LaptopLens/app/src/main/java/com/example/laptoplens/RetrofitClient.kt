package com.example.laptoplens

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api-434e63m7za-et.a.run.app/"

    // Function to retrieve the authentication token from SharedPreferences
    private fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    // Function to save the token to SharedPreferences
    private fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
        Log.d("RetrofitClient", "Token saved to SharedPreferences: $token")
    }

    // OkHttpClient builder with logging and token interceptor
    private fun createHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val response = chain.proceed(original)

                // Log all response headers for debugging
                for ((name, value) in response.headers) {
                    Log.d("RetrofitClient", "Header: $name = $value")
                }

                // Check if response has the Authorization header
                response.header("authorization")?.let { token ->
                    if (token.startsWith("Bearer ")) {
                        val actualToken = token.substring(7)
                        saveToken(context, actualToken)
                    }
                }

                response
            })
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                val token = getToken(context)
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer $token")
                    Log.d("RetrofitClient", "Token added to request: $token")
                } else {
                    Log.d("RetrofitClient", "No token found in SharedPreferences")
                }
                val request = requestBuilder.build()
                chain.proceed(request)
            })
            .build()
    }

    // Function to create and configure Retrofit instance
    fun getApiService(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createHttpClient(context))  // Using the OkHttpClient with token interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
