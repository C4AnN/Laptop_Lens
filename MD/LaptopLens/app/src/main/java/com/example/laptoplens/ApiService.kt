package com.example.laptoplens

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    fun signUp(@Body user: User): Call<ApiResponse>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse>
}
