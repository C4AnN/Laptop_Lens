package com.example.laptoplens

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.GET

interface ApiService {
    @Multipart
    @POST("predict")
    fun uploadCSV(@Part file: MultipartBody.Part): Call<ApiResponse>

    @POST("signup")
    fun signUp(@Body user: User): Call<ApiResponse>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse>

    @GET("last-prediction")
    fun getPredictions(): Call<PredictionResponse>
}
